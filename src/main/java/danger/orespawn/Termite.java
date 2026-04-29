/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.net.minecraft.entity.ai.EntityAIAttackMelee
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAINearestAttackableTarget
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.net.minecraft.util.text.ITextComponent
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 */
package danger.orespawn;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;

public class Termite
extends EntityAnt {
    int attack_delay = 20;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Termite(World worldIn) {
        super(worldIn);
        this.setSize(0.2f, 0.2f);
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2f);
        this.experienceValue = 1;
        ((net.minecraft.pathfinding.PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, (double)1.4f));
        this.tasks.addTask(1, (EntityAIBase)new net.minecraft.entity.ai.EntityAIAttackMelee((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 8, 1.0));
        if (OreSpawnMain.PlayNicely == 0) {
            this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, net.minecraft.entity.player.EntityPlayer.class, 6, true));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
    }

    @Override
    public int mygetMaxHealth() {
        return 5;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (OreSpawnMain.OreSpawnRand.nextInt(15) != 0) {
            return false;
        }
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((net.minecraft.entity.EntityLivingBase)this), 1.0f);
        return var4;
    }

    @Override
    public boolean interact(net.minecraft.entity.player.EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer == null) {
            return false;
        }
        if (!(par1EntityPlayer instanceof net.minecraft.entity.player.EntityPlayerMP)) {
            return false;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (var2 != null) {
            par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Empty your hand!"));
            return false;
        }
        if (par1EntityPlayer.dimension != OreSpawnMain.DimensionID5) {
            int i;
            for (i = 0; i < par1EntityPlayer.inventory.mainInventory.length; ++i) {
                if (par1EntityPlayer.inventory.mainInventory[i] == null) continue;
                par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Empty your inventory!"));
                return false;
            }
            for (i = 0; i < par1EntityPlayer.inventory.armorInventory.length; ++i) {
                if (par1EntityPlayer.inventory.armorInventory[i] == null) continue;
                par1EntityPlayer.addChatComponentMessage((net.minecraft.util.text.ITextComponent)new net.minecraft.util.text.TextComponentString("Take off your armor!"));
                return false;
            }
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, OreSpawnMain.DimensionID5, (Teleporter)null /* new OreSpawnTeleporter foi removido */(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(OreSpawnMain.DimensionID5), OreSpawnMain.DimensionID5, this.getEntityWorld()));
        } else {
            net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((net.minecraft.entity.player.EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)null /* new OreSpawnTeleporter foi removido */(net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0), 0, this.getEntityWorld()));
        }
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.isDead) {
            return;
        }
        if (this.attack_delay > 0) {
            --this.attack_delay;
        }
        if (this.attack_delay > 0) {
            return;
        }
        this.attack_delay = 20;
        if (this.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
            return;
        }
        net.minecraft.entity.player.EntityPlayer e = this.getEntityWorld().getClosestVulnerablePlayerToEntity((Entity)this, 1.5);
        if (e != null) {
            this.attackEntityAsMob((Entity)e);
        }
    }

    public boolean isWood(Block bid) {
        if (bid == net.minecraft.init.Blocks.FENCE || bid == net.minecraft.init.Blocks.FENCE_GATE || bid == Blocks.PLANKS || bid == Blocks.WOODEN_SLAB) {
            return true;
        }
        if (bid == Blocks.DOUBLE_WOODEN_SLAB || bid == Blocks.BED || bid == Blocks.CRAFTING_TABLE) {
            return true;
        }
        if (bid == Blocks.STANDING_SIGN || bid == Blocks.BOOKSHELF || bid == Blocks.WOODEN_DOOR || bid == Blocks.WOODEN_PRESSURE_PLATE) {
            return true;
        }
        if (bid == Blocks.BIRCH_STAIRS || bid == Blocks.OAK_STAIRS || bid == Blocks.JUNGLE_STAIRS || bid == Blocks.SPRUCE_STAIRS) {
            return true;
        }
        return bid == OreSpawnMain.CrystalPlanksBlock;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int d;
        Block bid;
        int j;
        int i;
        int found = 0;
        for (i = -dy; i <= dy; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if (this.isWood(bid) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dz; j <= dz; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + dy, z + j)).getBlock();
                if (this.isWood(bid) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = -dx; i <= dx; ++i) {
            for (j = -dy; j <= dy; ++j) {
                bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + dz)).getBlock();
                if (this.isWood(bid) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if (!this.isWood(bid = this.getEntityWorld().getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        return found != 0;
    }

    @Override
    public void updateAITick() {
        if (this.isDead) {
            return;
        }
        if (this.getEntityWorld().rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.getEntityWorld().rand.nextInt(200) == 1 && OreSpawnMain.PlayNicely == 0) {
            int i;
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (i = 1; i < 8; ++i) {
                int j = i;
                if (j > 4) {
                    j = 4;
                }
                if (this.scan_it((int)this.posX, (int)this.posY + 1, (int)this.posZ, i, j, i)) break;
                if (i < 5) continue;
                ++i;
            }
            i = 0;
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)this.ty, (double)this.tz, 1.0);
                if (this.closest < 6) {
                    if (this.getEntityWorld().rand.nextInt(3) != 0) {
                        if (this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                            this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(this.tx, this.ty, this.tz)), Blocks.DIRT.getDefaultState().getStateFromMeta(2);
                        }
                        if (this.findBuddies() < 10) {
                            Termite.spawnCreature(this.getEntityWorld()), "Termite", this.posX + (double)0.1f, this.posY + (double)0.1f, this.posZ + (double)0.1f);
                        }
                    } else {
                        if (this.getEntityWorld().getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                            this.getEntityWorld().setBlockState(new net.minecraft.util.math.BlockPos(new net.minecraft.util.math.BlockPos(this.tx, this.ty, this.tz)), Blocks.AIR.getDefaultState().getStateFromMeta(2);
                        }
                        if (this.findBuddies() < 10) {
                            Termite.spawnCreature(this.getEntityWorld()), "Termite", (float)this.tx + 0.1f, (float)this.ty + 0.1f, (float)this.tz + 0.1f);
                        }
                    }
                    this.heal(1.0f);
                }
            }
        }
        super.updateAITick();
    }

    private int findBuddies() {
        List var5 = this.getEntityWorld().getEntitiesWithinAABB(Termite.class, this.getEntityBoundingBox().expand(3.0, 3.0, 3.0));
        return var5.size();
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName((String)par1, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
        }
        return var8;
    }
}

