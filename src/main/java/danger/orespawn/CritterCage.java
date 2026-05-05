package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CritterCage extends Item {
    
    public int cage_id = 0;

    public CritterCage(int id) {
        this.cage_id = id;
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        CritterCage cc = (CritterCage) OreSpawnMain.CageEmpty;
        
        // Se for a Jaula Vazia, atira a EntityCage (A Pokébola)
        if (this.cage_id == cc.cage_id) {
            if (!playerIn.isCreative()) {
                itemstack.shrink(1);
            }
            
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            
            if (!worldIn.isRemote) {
                worldIn.spawnEntity(new EntityCage(worldIn, playerIn, this.cage_id));
            }
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        CritterCage cc = (CritterCage) OreSpawnMain.CageEmpty;
        
        if (this.cage_id == cc.cage_id) {
            return EnumActionResult.PASS;
        }

        double spawnX = pos.getX() + 0.5D;
        double spawnY = pos.getY() + 1.25D;
        double spawnZ = pos.getZ() + 0.5D;

        for (int i = 0; i < 6; ++i) {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, spawnX, spawnY, spawnZ, 0.0D, 0.0D, 0.0D);
        }
        
        worldIn.playSound(null, spawnX, spawnY, spawnZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1.0F, 1.5F);
        
        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        }

        ResourceLocation resLoc = this.getEntityResourceLocation(this.cage_id);

        if (resLoc != null) {
            Entity ent = spawnCreature(worldIn, resLoc, spawnX, spawnY - 0.15D, spawnZ);
            
            if (ent != null) {
                ent.dropItem(OreSpawnMain.CageEmpty, 1);
                
                if (ent instanceof EntityLiving && stack.hasDisplayName()) {
                    ((EntityLiving) ent).setCustomNameTag(stack.getDisplayName());
                }
            }
        } else {
            return EnumActionResult.FAIL;
        }
        
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        
        return EnumActionResult.SUCCESS;
    }

    // Método centralizado para gerir Ores e Mobs Vanilla sem Crash de IDs
    private ResourceLocation getEntityResourceLocation(int id) {
        switch (id) {
            // --- Vanilla Mobs (Substitui os IDs inteiros antigos) ---
            case 161: return new ResourceLocation("minecraft", "spider");
            case 162: return new ResourceLocation("minecraft", "bat");
            case 163: return new ResourceLocation("minecraft", "cow");
            case 164: return new ResourceLocation("minecraft", "pig");
            case 165: return new ResourceLocation("minecraft", "squid");
            case 166: return new ResourceLocation("minecraft", "chicken");
            case 167: return new ResourceLocation("minecraft", "creeper");
            case 168: return new ResourceLocation("minecraft", "skeleton");
            case 169: return new ResourceLocation("minecraft", "zombie");
            case 170: return new ResourceLocation("minecraft", "slime");
            case 171: return new ResourceLocation("minecraft", "ghast");
            case 172: return new ResourceLocation("minecraft", "zombie_pigman");
            case 173: return new ResourceLocation("minecraft", "enderman");
            case 174: return new ResourceLocation("minecraft", "cave_spider");
            case 175: return new ResourceLocation("minecraft", "silverfish");
            case 176: return new ResourceLocation("minecraft", "magma_cube");
            case 177: return new ResourceLocation("minecraft", "witch");
            case 178: return new ResourceLocation("minecraft", "sheep");
            case 179: return new ResourceLocation("minecraft", "wolf");
            case 180: return new ResourceLocation("minecraft", "mooshroom");
            case 181: return new ResourceLocation("minecraft", "ocelot");
            case 182: return new ResourceLocation("minecraft", "blaze");
            case 184: return new ResourceLocation("minecraft", "ender_dragon");
            case 185: return new ResourceLocation("minecraft", "snowman");
            case 186: return new ResourceLocation("minecraft", "villager_golem"); // Iron Golem
            case 187: return new ResourceLocation("minecraft", "wither");
            case 188: return new ResourceLocation("minecraft", "wither_skeleton"); // Modernizado (já não é esqueleto com ID 1)
            case 253: return new ResourceLocation("minecraft", "horse");
            case 217: return new ResourceLocation("minecraft", "villager");

            // --- OreSpawn Mobs ---
            case 183: return new ResourceLocation("orespawn", "Girlfriend");
            case 215: return new ResourceLocation("orespawn", "Boyfriend");
            case 189: return new ResourceLocation("orespawn", "Apple Cow");
            case 190: return new ResourceLocation("orespawn", "Golden Apple Cow");
            case 191: return new ResourceLocation("orespawn", "Enchanted Golden Apple Cow");
            case 208: return new ResourceLocation("orespawn", "Mothra");
            case 209: return new ResourceLocation("orespawn", "Alosaurus");
            case 210: return new ResourceLocation("orespawn", "Cryolophosaurus");
            case 211: return new ResourceLocation("orespawn", "Camarasaurus");
            case 212: return new ResourceLocation("orespawn", "Velocity Raptor");
            case 213: return new ResourceLocation("orespawn", "Hydrolisc");
            case 214: return new ResourceLocation("orespawn", "Basilisk");
            case 220: return new ResourceLocation("orespawn", "Dragonfly");
            case 222: return new ResourceLocation("orespawn", "Emperor Scorpion");
            case 224: return new ResourceLocation("orespawn", "Scorpion");
            case 226: return new ResourceLocation("orespawn", "CaveFisher");
            case 228: return new ResourceLocation("orespawn", "Baby Dragon");
            case 230: return new ResourceLocation("orespawn", "Baryonyx");
            case 232: return new ResourceLocation("orespawn", "WTF?");
            case 234: return new ResourceLocation("orespawn", "Bird");
            case 236: return new ResourceLocation("orespawn", "Kyuubi");
            case 238: return new ResourceLocation("orespawn", "Alien");
            case 240: return new ResourceLocation("orespawn", "Attack Squid");
            case 242: return new ResourceLocation("orespawn", "Water Dragon");
            case 244: return new ResourceLocation("orespawn", "The Kraken");
            case 246: return new ResourceLocation("orespawn", "Lizard");
            case 248: return new ResourceLocation("orespawn", "Cephadrome");
            case 250: return new ResourceLocation("orespawn", "Dragon");
            case 252: return new ResourceLocation("orespawn", "Bee");
            case 255: return new ResourceLocation("orespawn", "Firefly");
            case 256: return new ResourceLocation("orespawn", "Chipmunk");
            case 257: return new ResourceLocation("orespawn", "Gazelle");
            case 258: return new ResourceLocation("orespawn", "Ostrich");
            case 259: return new ResourceLocation("orespawn", "Jumpy Bug");
            case 260: return new ResourceLocation("orespawn", "Spit Bug");
            case 261: return new ResourceLocation("orespawn", "Stink Bug");
            case 268: return new ResourceLocation("orespawn", "Creeping Horror");
            case 269: return new ResourceLocation("orespawn", "Terrible Terror");
            case 270: return new ResourceLocation("orespawn", "Cliff Racer");
            case 271: return new ResourceLocation("orespawn", "Triffid");
            case 272: return new ResourceLocation("orespawn", "Nightmare");
            case 273: return new ResourceLocation("orespawn", "Lurking Terror");
            case 281: return new ResourceLocation("orespawn", "Small Worm");
            case 283: return new ResourceLocation("orespawn", "Large Worm");
            case 282: return new ResourceLocation("orespawn", "Medium Worm");
            case 284: return new ResourceLocation("orespawn", "Cassowary");
            case 285: return new ResourceLocation("orespawn", "Cloud Shark");
            case 286: return new ResourceLocation("orespawn", "Gold Fish");
            case 287: return new ResourceLocation("orespawn", "Leaf Monster");
            case 296: return new ResourceLocation("orespawn", "Ender Knight");
            case 297: return new ResourceLocation("orespawn", "Ender Reaper");
            case 300: return new ResourceLocation("orespawn", "Beaver");
            case 323: return new ResourceLocation("orespawn", "Crystal Urchin");
            case 319: return new ResourceLocation("orespawn", "Flounder");
            case 322: return new ResourceLocation("orespawn", "Skate");
            case 313: return new ResourceLocation("orespawn", "Rotator");
            case 315: return new ResourceLocation("orespawn", "Peacock");
            case 316: return new ResourceLocation("orespawn", "Fairy");
            case 317: return new ResourceLocation("orespawn", "Dungeon Beast");
            case 314: return new ResourceLocation("orespawn", "Vortex");
            case 318: return new ResourceLocation("orespawn", "Rat");
            case 320: return new ResourceLocation("orespawn", "Whale");
            case 321: return new ResourceLocation("orespawn", "Irukandji");
            case 345: return new ResourceLocation("orespawn", "T. Rex");
            case 346: return new ResourceLocation("orespawn", "Hercules Beetle");
            case 347: return new ResourceLocation("orespawn", "Mantis");
            case 348: return new ResourceLocation("orespawn", "Stinky");
            case 150: return new ResourceLocation("orespawn", "Easter Bunny");
            case 151: return new ResourceLocation("orespawn", "CaterKiller");
            case 152: return new ResourceLocation("orespawn", "Molenoid");
            case 153: return new ResourceLocation("orespawn", "Sea Monster");
            case 154: return new ResourceLocation("orespawn", "Sea Viper");
            case 357: return new ResourceLocation("orespawn", "Leonopteryx");
            case 359: return new ResourceLocation("orespawn", "Hammerhead");
            case 361: return new ResourceLocation("orespawn", "Rubber Ducky");
            case 216: return new ResourceLocation("orespawn", "Crystal Apple Cow");
            case 218: return new ResourceLocation("orespawn", "Criminal");
            case 373: return new ResourceLocation("orespawn", "Brutalfly");
            case 374: return new ResourceLocation("orespawn", "Nastysaurus");
            case 375: return new ResourceLocation("orespawn", "Pointysaurus");
            case 376: return new ResourceLocation("orespawn", "Cricket");
            case 377: return new ResourceLocation("orespawn", "Frog");
            case 382: return new ResourceLocation("orespawn", "Spider Driver");
            case 384: return new ResourceLocation("orespawn", "Crab");
            default: return null;
        }
    }

    public static Entity spawnCreature(World worldIn, ResourceLocation resLoc, double x, double y, double z) {
        Entity entity = EntityList.createEntityByIDFromName(resLoc, worldIn);
        
        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, worldIn.rand.nextFloat() * 360.0F, 0.0F);
            
            // Só cavalos (100 -> minecraft:horse) e villagers (120 -> minecraft:villager) é que precisavam disto
            if ((resLoc.equals(new ResourceLocation("minecraft", "horse")) || resLoc.equals(new ResourceLocation("minecraft", "villager"))) && entity instanceof EntityLiving) {
                EntityLiving living = (EntityLiving) entity;
                // onSpawnWithEgg foi modernizado para onInitialSpawn
                living.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entity)), null);
            }
            
            worldIn.spawnEntity(entity);
            
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }
        
        return entity;
    }
}