package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CaterKiller extends EntityMob {

    public CaterKiller(World worldIn) {
        super(worldIn);
        
        // Define o tamanho com base na config PlayNicely
        if (OreSpawnMain.PlayNicely == 0) {
            this.setSize(2.9F, 4.6F);
        } else {
            this.setSize(1.45F, 2.3F);
        }
        
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 200;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D)); 
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(OreSpawnMain.CaterKiller_stats.attack);
    }

    public int mygetMaxHealth() {
        return OreSpawnMain.CaterKiller_stats.health;
    }

    @Override
    public int getTotalArmorValue() {
        return OreSpawnMain.CaterKiller_stats.defense;
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean ret = super.attackEntityFrom(source, amount);
        Entity e = source.getTrueSource();
        if (e instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase) e);
        }
        return ret;
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        // Ataque com knockback extremo original do OreSpawn
        if (super.attackEntityAsMob(target)) {
            if (target instanceof EntityLivingBase) {
                double ks = 1.2D;
                double inair = 0.1D;
                float f3 = (float) Math.atan2(target.posZ - this.posZ, target.posX - this.posX);
                
                if (target.isDead || target instanceof EntityPlayer) {
                    inair *= 2.0D; // Atira o jogador pelo ar
                }
                target.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void updateAITasks() {
        if (this.isDead) return;
        super.updateAITasks();
        
        // Destruição de Árvores (Lógica que substitui o gigantesco "scan_it")
        if (OreSpawnMain.PlayNicely == 0 && this.world.rand.nextInt(20) == 0) {
            int range = 2; // O CaterKiller é muito largo
            BlockPos center = new BlockPos(this);
            for (int dx = -range; dx <= range; dx++) {
                for (int dy = 0; dy <= range * 2; dy++) {
                    for (int dz = -range; dz <= range; dz++) {
                        BlockPos pos = center.add(dx, dy, dz);
                        Block bid = this.world.getBlockState(pos).getBlock();
                        
                        // Come folhas e troncos no caminho
                        if (bid == Blocks.LEAVES || bid == Blocks.LEAVES2 || bid == Blocks.LOG || bid == Blocks.LOG2) {
                            if (this.world.getGameRules().getBoolean("mobGriefing")) {
                                this.world.destroyBlock(pos, true);
                            }
                        }
                    }
                }
            }
        }
    }

    // --- Sistema de Drops Épicos ---

    private ItemStack dropItemRand(Item item, int amount) {
        ItemStack stack = new ItemStack(item, amount, 0);
        double dx = this.posX + (this.world.rand.nextDouble() * 5.0D) - (this.world.rand.nextDouble() * 5.0D);
        double dy = this.posY + 1.0D;
        double dz = this.posZ + (this.world.rand.nextDouble() * 5.0D) - (this.world.rand.nextDouble() * 5.0D);
        EntityItem entityItem = new EntityItem(this.world, dx, dy, dz, stack);
        this.world.spawnEntity(entityItem);
        return stack;
    }

    private void applyArmorEnchantments(ItemStack is) {
        // Função auxiliar para evitar copiar/colar o código de encantamento em todas as peças de armadura
        if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROTECTION, 1 + this.world.rand.nextInt(5));
        if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BLAST_PROTECTION, 1 + this.world.rand.nextInt(5));
        if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_PROTECTION, 1 + this.world.rand.nextInt(5));
        if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 1 + this.world.rand.nextInt(5));
        if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        this.dropItemRand(OreSpawnMain.CaterKillerJaw, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        
        for (int i = 0; i < 10; ++i) this.dropItemRand(Items.LEATHER, 1);
        for (int i = 0; i < 6; ++i) this.dropItemRand(Items.BEEF, 1);
        
        // Drops raros (Ferramentas, armas e armaduras de Rubi)
        int count = 1 + this.world.rand.nextInt(5);
        for (int i = 0; i < count; ++i) {
            int chance = this.world.rand.nextInt(20);
            ItemStack is = null;
            
            switch (chance) {
                case 0:
                    this.dropItemRand(OreSpawnMain.MyUltimateSword, 1);
                    break;
                case 1:
                    this.dropItemRand(OreSpawnMain.MyRuby, 1);
                    break;
                case 2:
                    this.dropItemRand(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 1);
                    break;
                case 3: // Ruby Sword
                    is = this.dropItemRand(OreSpawnMain.MyRubySword, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.SHARPNESS, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.KNOCKBACK, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.LOOTING, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FIRE_ASPECT, 1 + this.world.rand.nextInt(5));
                    break;
                case 4: // Ruby Shovel
                    is = this.dropItemRand(OreSpawnMain.MyRubyShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                case 5: // Ruby Pickaxe
                    is = this.dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FORTUNE, 1 + this.world.rand.nextInt(5));
                    break;
                case 6: // Ruby Axe
                    is = this.dropItemRand(OreSpawnMain.MyRubyAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                case 7: // Ruby Hoe
                    is = this.dropItemRand(OreSpawnMain.MyRubyHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.EFFICIENCY, 1 + this.world.rand.nextInt(5));
                    break;
                case 8: // Ruby Helmet
                    is = this.dropItemRand(OreSpawnMain.RubyHelmet, 1);
                    this.applyArmorEnchantments(is);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.RESPIRATION, 1 + this.world.rand.nextInt(2));
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.AQUA_AFFINITY, 1 + this.world.rand.nextInt(5));
                    break;
                case 9: // Ruby Chestplate
                    is = this.dropItemRand(OreSpawnMain.RubyBody, 1);
                    this.applyArmorEnchantments(is);
                    break;
                case 10: // Ruby Leggings
                    is = this.dropItemRand(OreSpawnMain.RubyLegs, 1);
                    this.applyArmorEnchantments(is);
                    break;
                case 11: // Ruby Boots
                    is = this.dropItemRand(OreSpawnMain.RubyBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) is.addEnchantment(Enchantments.FEATHER_FALLING, 5 + this.world.rand.nextInt(5));
                    if (this.world.rand.nextInt(2) == 1) is.addEnchantment(Enchantments.UNBREAKING, 2 + this.world.rand.nextInt(4));
                    break;
                case 12: // Ultimate Bow
                    this.dropItemRand(OreSpawnMain.MyUltimateBow, 1);
                    break;
            }
        }
        
        // Efeito Final: Spawna as 25 borboletas ao morrer!
        if (!this.world.isRemote) {
            for (int j = 0; j < 25; ++j) {
                Entity butterfly = EntityList.createEntityByIDFromName(new ResourceLocation("orespawn:butterfly"), this.world);
                if (butterfly != null) {
                    butterfly.setLocationAndAngles(this.posX, this.posY + 1.0D, this.posZ, this.world.rand.nextFloat() * 360.0F, 0.0F);
                    this.world.spawnEntity(butterfly);
                }
            }
        }
    }

    // --- Sistema de Sons ---

    @Override
    protected SoundEvent getAmbientSound() {
        return this.world.rand.nextInt(3) == 0 ? SoundEvent.REGISTRY.getObject(new ResourceLocation("orespawn", "caterkiller_living")) : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 1.5F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }
}