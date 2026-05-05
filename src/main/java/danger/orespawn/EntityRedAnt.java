package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityRedAnt extends EntityAnt {

    public EntityRedAnt(World worldIn) {
        super(worldIn);
        this.setSize(0.2F, 0.2F); // Um pouco maior que a formiga comum
        this.experienceValue = 1;
    }

    @Override
    protected void initEntityAI() {
        // Aproveita o AI de natação e fuga da classe pai
        super.initEntityAI();

        // Adiciona IA agressiva
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D));

        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        // Red ants são pequenas e erram bastante o ataque, mas na 1.12.2
        // o ataque deve respeitar o cooldown padrão do jogo se for muito alto.
        // A lógica do OreSpawn de ignorar ataques baseado em um número aleatório foi mantida:
        if (this.rand.nextInt(15) != 0) {
            return false;
        }
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // Lógica "hack" do OreSpawn original para atacar players colados a ela
        // A IA padrão já faz isso, mas mantive para ser fiel ao comportamento clássico de "mordiscar o pé"
        if (!this.world.isRemote && this.ticksExisted % 20 == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && OreSpawnMain.PlayNicely == 0) {
            EntityPlayer player = this.world.getClosestPlayerToEntity(this, 1.5D);
            if (player != null && !player.capabilities.isCreativeMode) {
                this.attackEntityAsMob(player);
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.isEmpty()) {
                // Alvo da Red Ant: DimensionID2 (Miner's Dream)
                int targetDimension = (player.dimension != OreSpawnMain.DimensionID2) ? OreSpawnMain.DimensionID2 : 0;
                
                WorldServer worldServer = player.getServer().getWorld(targetDimension);
                player.changeDimension(targetDimension, new OreSpawnTeleporter(worldServer));
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
}