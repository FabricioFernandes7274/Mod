package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Iterator;

public class Dragon extends EntityTameable {
    // DataParameters substitui o antigo DataWatcher para sincronizar Client/Server
    private static final DataParameter<Integer> ATTACKING = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ACTIVITY = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DRAGON_TYPE = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DRAGON_FIRE = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);

    private int boatPosRotationIncrements;
    private double boatX, boatY, boatZ, boatYaw, boatPitch, boatYawHead;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wing_sound = 0;
    private BlockPos currentFlightTarget = null;
    private boolean target_in_sight = false;
    private int owner_flying = 0;
    private int flyaway = 0;
    private int stuck_count = 0;
    private int lastX = 0, lastZ = 0;
    private int unstick_timer = 0;
    private int fireballticker = 0;
    private float moveSpeed = 0.32f;
    private float deltasmooth = 0.0f;
    private int dragontype = 0;
    private int closest = 99999;
    private int tx = 0, ty = 0, tz = 0;

    public Dragon(World worldIn) {
        super(worldIn);
        this.setSize(1.5f, 1.25f);
        this.experienceValue = 100;
        this.isImmuneToFire = true;
        this.setSitting(false);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new MyEntityAIFollowOwner(this, 1.1f, 12.0f, 2.0f));
        this.tasks.addTask(2, new EntityAITempt(this, 1.25D, Items.BEEF, false));
        this.tasks.addTask(3, new MyEntityAIWander(this, 0.75f));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 9.0f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMoveIndoors(this));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.VISIBLE_MOB_SELECTOR));
        }
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKING, 0);
        this.dataManager.register(ACTIVITY, 0);
        this.dataManager.register(DRAGON_TYPE, 0);
        this.dataManager.register(DRAGON_FIRE, 1);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(35.0D);
    }

    // Getters e Setters originais apontando para o novo DataManager
    public int getAttacking() { return this.dataManager.get(ATTACKING); }
    public void setAttacking(int par1) { this.dataManager.set(ATTACKING, par1); }
    public int getActivity() { return this.dataManager.get(ACTIVITY); }
    public void setActivity(int par1) { this.dataManager.set(ACTIVITY, par1); }
    public int getDragonFire() { return this.dataManager.get(DRAGON_FIRE); }
    public void setDragonFire(int par1) { this.dataManager.set(DRAGON_FIRE, par1); }
    public int getDragonType() { return this.dataManager.get(DRAGON_TYPE); }
    public void setDragonType(int par1) { this.dataManager.set(DRAGON_TYPE, par1); }
 // --- Lógica de Combate e Alvos ---
    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) return false;
        if (par1EntityLiving == null || par1EntityLiving == this || !par1EntityLiving.isEntityAlive()) return false;
        if (!this.getEntitySenses().canSee(par1EntityLiving)) return false;
        
        // Blacklist original do OreSpawn
        if (par1EntityLiving instanceof LurkingTerror || par1EntityLiving instanceof EnderReaper || 
            par1EntityLiving instanceof TerribleTerror || par1EntityLiving instanceof LeafMonster || 
            par1EntityLiving instanceof CreepingHorror || par1EntityLiving instanceof Triffid) {
            return false;
        }
        
        if (par1EntityLiving instanceof EntityMob || par1EntityLiving instanceof Mothra || par1EntityLiving instanceof Kraken) {
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (OreSpawnMain.PlayNicely != 0) return null;
        List<EntityLivingBase> var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0, 20.0, 20.0));
        for (EntityLivingBase var4 : var5) {
            if (this.isSuitableTarget(var4, false)) return var4;
        }
        return null;
    }

    public void fly_with_rider() {
        EntityLivingBase e = null;
        if (this.isDead || this.isSitting() || this.world.isRemote) return;

        if (this.world.rand.nextInt(7) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.world.rand.nextInt(250) == 0) this.setAttackTarget(null);
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) e = this.findSomethingToAttack();
            if (e != null) {
                this.setAttacking(1);
                if (this.getDistanceSq(e) < (double)((7.0f + e.width / 2.0f) * (7.0f + e.width / 2.0f))) {
                    this.attackEntityAsMob(e);
                }
                return;
            }
            this.setAttacking(0);
        }
    }

    private void fly_without_rider() {
        Block bid;
        int xdir = 1, zdir = 1, keep_trying = 50;
        boolean do_new = false, has_owner = false, toofar = false;
        double ox = 0, oy = 0, oz = 0;
        EntityLivingBase e = null;
        double speed_factor = 0.5, var1 = 0, var3 = 0, var5 = 0;
        double yoff = 1.25, xzoff = 2.25;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (this.currentFlightTarget == null) {
            do_new = true;
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.isSitting() || this.isBeingRidden()) return;

        if (this.unstick_timer > 0) --this.unstick_timer;
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            if (++this.stuck_count > 50) {
                this.stuck_count = 0; this.unstick_timer = 100;
                this.target_in_sight = false; this.setAttacking(0); do_new = true;
            }
        } else {
            this.stuck_count = 0; this.lastX = (int)this.posX; this.lastZ = (int)this.posZ;
        }

        this.motionY = this.posY < (double)this.currentFlightTarget.getY() + 2.0 ? (this.motionY *= 0.7) : (this.posY > (double)this.currentFlightTarget.getY() - 2.0 ? (this.motionY *= 0.5) : (this.motionY *= 0.61));
        if (this.world.rand.nextInt(300) == 1) do_new = true;

        if (this.isTamed() && this.getOwner() != null) {
            e = (EntityLivingBase)this.getOwner();
            has_owner = true; ox = e.posX; oy = e.posY; oz = e.posZ;
            if (this.getDistanceSq(e) > 144.0) {
                toofar = true; this.target_in_sight = false; this.setAttacking(0); this.flyaway = 0; do_new = true;
            }
        }

        // --- Lógica de Ataque em Voo ---
        if (!toofar && this.unstick_timer == 0 && this.flyaway == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(9) == 1) {
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.isTamed() && this.getHealth() / this.getMaxHealth() < 0.25f) {
                    this.setActivity(1); this.setAttacking(0); this.target_in_sight = false;
                    this.currentFlightTarget = new BlockPos((int)(this.posX + (this.posX - e.posX)), (int)(this.posY + 1.0), (int)(this.posZ + (this.posZ - e.posZ)));
                } else {
                    this.setActivity(1); this.setAttacking(1); this.target_in_sight = true;
                    this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                    if (this.getDistanceSq(e) < (double)((5.0f + e.width / 2.0f) * (5.0f + e.width / 2.0f))) {
                        this.attackEntityAsMob(e);
                        this.flyaway = 5 + this.world.rand.nextInt(10); do_new = true;
                    } else if (this.getDistanceSq(e) < 256.0 && !this.isInWater() && this.getDragonFire() >= 1) {
                        spawnDragonProjectile(e, xzoff, yoff); // Helper para disparar bolas de fogo/gelo
                    }
                }
            } else {
                this.target_in_sight = false; this.flyaway = 0; this.setAttacking(0);
            }
        }

        // Lógica de Obstrução e movimentação final
        if (this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.1f) do_new = true;
        if (do_new && (!this.target_in_sight || this.flyaway != 0)) {
            findNewFlightTarget(has_owner, ox, oy, oz);
        }

        moveDragon(speed_factor, velocity);
    }
    @Override
    public void onLivingUpdate() {
        if (this.getActivity() == 0) {
            super.onLivingUpdate();
        } else if (this.isDead) {
            super.onLivingUpdate();
            return;
        }

        if (this.world.isRemote) {
            if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = MathHelper.wrapDegrees(this.boatYaw - (double)this.rotationYaw);
                if (this.isBeingRidden()) {
                    d10 = MathHelper.wrapDegrees(this.getControllingPassenger().rotationYaw - (double)this.rotationYaw);
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.rotationYaw;
                --this.boatPosRotationIncrements;
            }
        } else {
            if (this.getActivity() != 0) {
                if (this.fireballticker > 0) --this.fireballticker;

                if (this.isBeingRidden() && this.getControllingPassenger() instanceof EntityPlayer) {
                    EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
                    processRiderControl(pp); // Processa comandos do jogador
                    this.fly_with_rider();
                } else {
                    this.fly_without_rider();
                }
            }
            // this.always_do(); // Adicione sua lógica extra aqui se necessário
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack var2 = player.getHeldItem(hand);
        if (var2.isEmpty() && player.getDistanceSq(this) < 16.0 && this.isTamed()) {
            if (!this.world.isRemote) {
                player.startRiding(this);
                this.setActivity(1);
                this.setSitting(false);
            }
            return true;
        }

        if (!this.isTamed()) {
            if (!var2.isEmpty() && var2.getItem() == Items.BEEF) {
                if (!this.world.isRemote) {
                    if (this.rand.nextInt(5) == 1) {
                        this.setTamedBy(player);
                        this.playTameEffect(true);
                        this.world.setEntityState(this, (byte)7);
                        this.heal(this.getMaxHealth());
                    } else {
                        this.playTameEffect(false);
                        this.world.setEntityState(this, (byte)6);
                    }
                }
                if (!player.capabilities.isCreativeMode) var2.shrink(1);
                return true;
            }
        } else {
            if (!this.isOwner(player)) return false;

            // Cura com Carne
            if (var2.getItem() == Items.BEEF) {
                this.heal(this.getMaxHealth() - this.getHealth());
                if (!player.capabilities.isCreativeMode) var2.shrink(1);
                return true;
            }
            // Desdomar com Deadbush
            if (var2.getItem() == Item.getItemFromBlock(Blocks.DEADBUSH)) {
                this.setTamed(false);
                this.setOwnerId(null);
                if (!player.capabilities.isCreativeMode) var2.shrink(1);
                return true;
            }
            // Tipos de Fogo (Gelo/Fogo/Supercharge)
            if (var2.getItem() == Item.getItemFromBlock(Blocks.ICE)) { this.setDragonFire(0); player.sendMessage(new TextComponentString("Dragon fireballs extinguished.")); return true; }
            if (var2.getItem() == Items.FLINT_AND_STEEL) { this.setDragonFire(1); player.sendMessage(new TextComponentString("Dragon fireballs lit!")); return true; }
            if (var2.getItem() == Items.GUNPOWDER) { this.setDragonFire(2); player.sendMessage(new TextComponentString("Dragon fireballs supercharged!")); return true; }
            
            // Evolução para Baby Dragon (Spyro)
            if (var2.getItem() == Items.DIAMOND && !this.world.isRemote) {
                Entity ent = spawnCreature(this.world, "Baby Dragon", this.posX, this.posY, this.posZ);
                if (ent instanceof EntityTameable) {
                    ((EntityTameable)ent).setTamed(true);
                    ((EntityTameable)ent).setOwnerId(this.getOwnerId());
                }
                this.setDead();
                return true;
            }

            // Alternar Sentar/Ficar
            this.setSitting(!this.isSitting());
            this.setActivity(0);
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("DragonAttacking", this.getAttacking());
        compound.setInteger("DragonActivity", this.getActivity());
        compound.setInteger("DragonFire", this.getDragonFire());
        compound.setInteger("DragonType", this.getDragonType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAttacking(compound.getInteger("DragonAttacking"));
        this.setActivity(compound.getInteger("DragonActivity"));
        this.setDragonFire(compound.getInteger("DragonFire"));
        this.setDragonType(compound.getInteger("DragonType"));
    }

    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public static Entity spawnCreature(World world, String name, double x, double y, double z) {
        Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation(name), world);
        if (var8 != null) {
            var8.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0f, 0.0f);
            world.spawnEntity(var8);
        }
        return var8;
    }
}