package danger.orespawn;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class Coin extends EntityLiving {

    public Coin(World worldIn) {
        super(worldIn);
        this.setSize(1.5F, 1.5F);
        this.experienceValue = 10;
    }

    @Override
    protected void initEntityAI() {
        // Na 1.12.2 as tasks devem ser adicionadas preferencialmente aqui
        this.tasks.addTask(0, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        // EntityLiving não tem dano de ataque por defeito, logo temos de registar o atributo antes:
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
    }

    @Override
    protected boolean canDespawn() {
        return !this.isNoDespawnRequired();
    }

    public int mygetMaxHealth() {
        return 1; // Só precisa de 1 hit para ser "partida"
    }

    @Override
    public int getTotalArmorValue() {
        return 0;
    }

    // --- Spawn e Deteção de outras Moedas ---
    
    @Override
    public boolean getCanSpawnHere() {
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 50.0D) {
            return false;
        }
        
        // Verifica se já existem outras moedas num raio de 20 blocos para não gerar aos montes
        List<Coin> list = this.world.getEntitiesWithinAABB(Coin.class, this.getEntityBoundingBox().grow(20.0D, 8.0D, 20.0D));
        return list.isEmpty() && super.getCanSpawnHere();
    }

    // --- Sistema de Drops e Recompensas ---

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int i = this.world.rand.nextInt(10);
        Item dropItem = OreSpawnMain.MyEmeraldSword;
        
        if (i == 0) dropItem = Items.DIAMOND;
        else if (i == 1) dropItem = OreSpawnMain.UraniumNugget;
        else if (i == 2) dropItem = OreSpawnMain.TitaniumNugget;
        else if (i == 3) dropItem = Items.EMERALD;
        else if (i == 4) dropItem = OreSpawnMain.MyEmeraldAxe;
        else if (i == 5) dropItem = OreSpawnMain.MyEmeraldShovel;
        else if (i == 6) dropItem = OreSpawnMain.MyEmeraldPickaxe;
        else if (i == 7) dropItem = OreSpawnMain.MyEmeraldHoe;
        else if (i == 8) dropItem = OreSpawnMain.CoinEgg;

        // O método nativo substitui o antigo 'dropItemRand' de forma segura e perfeita
        this.entityDropItem(new ItemStack(dropItem, 1), 1.0F);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    // --- Interações e Áudio ---

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() { 
        return SoundEvents.ENTITY_GENERIC_EXPLODE; 
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
        return 1.0F;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0F;
    }
}