package danger.orespawn;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EasterBunny extends EntityAnimal {

    public EasterBunny(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.75f);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        // Na 1.12.2, as tarefas são inicializadas aqui em vez de no construtor
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(2, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0f, 1.0D, 1.4D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0f, 1.0D, 1.4D));
        this.tasks.addTask(4, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
        this.tasks.addTask(6, new MyEntityAIWanderALot(this, 16, 1.0D));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        // Registra dano de ataque para animais (comum no OreSpawn)
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        // Mantém a velocidade base (lógica original do mod)
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50.0D || !this.world.isDaytime()) {
            return false;
        }
        
        // Verifica se já existe outro EasterBunny por perto
        AxisAlignedBB box = this.getEntityBoundingBox().grow(32.0D, 8.0D, 32.0D);
        Entity target = this.world.findNearestEntityWithinAABB(EasterBunny.class, box, this);
        
        return target == null && super.getCanSpawnHere();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE; // Som clássico do OreSpawn
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
        return 0.4F;
    }

    @Override
    protected Item getDropItem() {
        return Items.CHICKEN;
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        int count = this.rand.nextInt(3) + 2;
        for (int i = 0; i < count; ++i) {
            this.dropItem(Items.CHICKEN, 1);
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITasks();
        
        if (!this.world.isRemote && this.rand.nextInt(600) == 1) {
            this.layAnEgg(1 + this.rand.nextInt(3));
        }
    }

    private void layAnEgg(int amount) {
        int i = this.rand.nextInt(115);
        Item eggItem = getEggFromIndex(i);

        if (eggItem != null) {
            ItemStack stack = new ItemStack(eggItem, amount);
            double rx = this.posX + (this.rand.nextDouble() * 4.0D - 2.0D);
            double rz = this.posZ + (this.rand.nextDouble() * 4.0D - 2.0D);
            
            EntityItem entityItem = new EntityItem(this.world, rx, this.posY + 1.0D, rz, stack);
            this.world.spawnEntity(entityItem);
        }
    }

    private Item getEggFromIndex(int index) {
        switch (index) {
            case 5: return OreSpawnMain.GirlfriendEgg;
            case 6: return OreSpawnMain.RedCowEgg;
            case 7: return OreSpawnMain.GoldCowEgg;
            case 8: return OreSpawnMain.EnchantedCowEgg;
            case 9: return OreSpawnMain.MOTHRAEgg;
            case 10: return OreSpawnMain.AloEgg;
            case 11: return OreSpawnMain.CryoEgg;
            case 12: return OreSpawnMain.CamaEgg;
            case 13: return OreSpawnMain.VeloEgg;
            case 14: return OreSpawnMain.HydroEgg;
            case 15: return OreSpawnMain.BasilEgg;
            case 16: return OreSpawnMain.DragonflyEgg;
            case 17: return OreSpawnMain.EmperorScorpionEgg;
            case 18: return OreSpawnMain.ScorpionEgg;
            case 19: return OreSpawnMain.CaveFisherEgg;
            case 20: return OreSpawnMain.SpyroEgg;
            case 21: return OreSpawnMain.BaryonyxEgg;
            case 22: return OreSpawnMain.GammaMetroidEgg;
            case 23: return OreSpawnMain.CockateilEgg;
            case 24: return OreSpawnMain.KyuubiEgg;
            case 25: return OreSpawnMain.AlienEgg;
            case 26: return OreSpawnMain.AttackSquidEgg;
            case 27: return OreSpawnMain.WaterDragonEgg;
            case 28: return OreSpawnMain.CephadromeEgg;
            case 29: return OreSpawnMain.DragonEgg;
            case 30: return OreSpawnMain.KrakenEgg;
            case 31: return OreSpawnMain.LizardEgg;
            case 32: return OreSpawnMain.BeeEgg;
            case 33: return OreSpawnMain.TrooperBugEgg;
            case 34: return OreSpawnMain.SpitBugEgg;
            case 35: return OreSpawnMain.StinkBugEgg;
            case 36: return OreSpawnMain.OstrichEgg;
            case 37: return OreSpawnMain.GazelleEgg;
            case 38: return OreSpawnMain.ChipmunkEgg;
            case 39: return OreSpawnMain.CreepingHorrorEgg;
            case 40: return OreSpawnMain.TerribleTerrorEgg;
            case 41: return OreSpawnMain.CliffRacerEgg;
            case 42: return OreSpawnMain.TriffidEgg;
            case 43: return OreSpawnMain.PitchBlackEgg;
            case 44: return OreSpawnMain.LurkingTerrorEgg;
            case 45: return OreSpawnMain.GodzillaEgg;
            case 46: return OreSpawnMain.SmallWormEgg;
            case 47: return OreSpawnMain.MediumWormEgg;
            case 48: return OreSpawnMain.LargeWormEgg;
            case 49: return OreSpawnMain.CassowaryEgg;
            case 50: return OreSpawnMain.CloudSharkEgg;
            case 51: return OreSpawnMain.GoldFishEgg;
            case 52: return OreSpawnMain.LeafMonsterEgg;
            case 53: return OreSpawnMain.TshirtEgg;
            case 54: return OreSpawnMain.EnderKnightEgg;
            case 55: return OreSpawnMain.EnderReaperEgg;
            case 56: return OreSpawnMain.BeaverEgg;
            case 57: return OreSpawnMain.RotatorEgg;
            case 58: return OreSpawnMain.VortexEgg;
            case 59: return OreSpawnMain.PeacockEgg;
            case 60: return OreSpawnMain.FairyEgg;
            case 61: return OreSpawnMain.DungeonBeastEgg;
            case 62: return OreSpawnMain.RatEgg;
            case 63: return OreSpawnMain.FlounderEgg;
            case 64: return OreSpawnMain.WhaleEgg;
            case 65: return OreSpawnMain.IrukandjiEgg;
            case 66: return OreSpawnMain.SkateEgg;
            case 67: return OreSpawnMain.UrchinEgg;
            case 68: return OreSpawnMain.Robot1Egg;
            case 69: return OreSpawnMain.Robot2Egg;
            case 70: return OreSpawnMain.Robot3Egg;
            case 71: return OreSpawnMain.Robot4Egg;
            case 72: return OreSpawnMain.GhostEgg;
            case 73: return OreSpawnMain.GhostSkellyEgg;
            case 74: return OreSpawnMain.BrownAntEgg;
            case 75: return OreSpawnMain.RedAntEgg;
            case 76: return OreSpawnMain.RainbowAntEgg;
            case 77: return OreSpawnMain.UnstableAntEgg;
            case 78: return OreSpawnMain.TermiteEgg;
            case 79: return OreSpawnMain.ButterflyEgg;
            case 80: return OreSpawnMain.MothEgg;
            case 81: return OreSpawnMain.MosquitoEgg;
            case 82: return OreSpawnMain.FireflyEgg;
            case 83: return OreSpawnMain.TRexEgg;
            case 84: return OreSpawnMain.HerculesEgg;
            case 85: return OreSpawnMain.MantisEgg;
            case 86: return OreSpawnMain.StinkyEgg;
            case 87: return OreSpawnMain.Robot5Egg;
            case 88: return OreSpawnMain.CoinEgg;
            case 89: return OreSpawnMain.BoyfriendEgg;
            case 90: return OreSpawnMain.TheKingEgg;
            case 91: return OreSpawnMain.ThePrinceEgg;
            case 92: return OreSpawnMain.EasterBunnyEgg;
            case 93: return OreSpawnMain.MolenoidEgg;
            case 94: return OreSpawnMain.SeaMonsterEgg;
            case 95: return OreSpawnMain.SeaViperEgg;
            case 96: return OreSpawnMain.CaterKillerEgg;
            case 97: return OreSpawnMain.LeonEgg;
            case 98: return OreSpawnMain.HammerheadEgg;
            case 99: return OreSpawnMain.RubberDuckyEgg;
            case 100: return OreSpawnMain.CrystalCowEgg;
            case 101: return OreSpawnMain.CriminalEgg;
            case 102: return OreSpawnMain.TheQueenEgg;
            case 103: return OreSpawnMain.BrutalflyEgg;
            case 104: return OreSpawnMain.NastysaurusEgg;
            case 105: return OreSpawnMain.PointysaurusEgg;
            case 106: return OreSpawnMain.CricketEgg;
            case 107: return OreSpawnMain.ThePrincessEgg;
            case 108: return OreSpawnMain.FrogEgg;
            case 109: return OreSpawnMain.JefferyEgg;
            case 110: return OreSpawnMain.AntRobotEgg;
            case 111: return OreSpawnMain.SpiderRobotEgg;
            case 112: return OreSpawnMain.SpiderDriverEgg;
            case 113: return OreSpawnMain.CrabEgg;
            default: return null;
        }
    }

    @Override
    public boolean canDespawn() {
        return !this.isChild() && !this.isNoDespawnRequired();
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EasterBunny(this.world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == OreSpawnMain.MyCrystalApple;
    }
}