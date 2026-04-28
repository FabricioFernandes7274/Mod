/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IslandToo extends net.minecraft.entity.EntityLiving {
    private int width = 5;
    private int depth = 3;
    private int length = 10;
    private int timer = 42;
    private int just_spawned = 1;
    private int ticker = 0;
    private int once = 1;
    private double myX;
    private double myY;
    private double myZ;
    private int dirchange = 0;
    private int blocktype = 0;

    public IslandToo(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
        this.ticker = worldIn.rand.nextInt(50);
        this.dirchange = this.getEntityWorld().rand.nextInt(5000);
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        if (this.getEntityWorld().isRemote) {
            return;
        }
        if (this.once != 0) {
            this.myX = this.posX;
            this.myY = this.posY;
            this.myZ = this.posZ;
            this.once = 0;
        }
        if (this.just_spawned != 0) {
            this.dir = this.getEntityWorld().rand.nextInt(4);
            if (this.getEntityWorld().rand.nextInt(40) != 1) {
                this.length = this.width = 1 + this.getEntityWorld().rand.nextInt(5 * OreSpawnMain.IslandSizeFactor);
                this.depth = 1 + this.getEntityWorld().rand.nextInt(4);
                this.speed = this.getEntityWorld().rand.nextFloat() / 40.0f * (float)OreSpawnMain.IslandSpeedFactor;
                if (this.length * this.width * this.depth <= 64) {
                    this.speed *= 2.0f;
                }
                if (this.length * this.width * this.depth <= 32) {
                    this.speed *= 2.0f;
                }
            } else {
                this.length = this.width = 5 + this.getEntityWorld().rand.nextInt(8 * OreSpawnMain.IslandSizeFactor);
                this.depth = 3 + this.getEntityWorld().rand.nextInt(6);
                this.speed = this.getEntityWorld().rand.nextFloat() / 150.0f * (float)OreSpawnMain.IslandSpeedFactor;
            }
            this.create_island();
            this.ticker = this.getEntityWorld().rand.nextInt(50);
            this.dirchange = this.getEntityWorld().rand.nextInt(10000);
        }
        ++this.ticker;
        if (this.ticker >= this.timer) {
            this.update_island();
            this.ticker = 0;
        }
        --this.dirchange;
        if (this.dirchange <= 0) {
            this.dirchange = this.getEntityWorld().rand.nextInt(5000);
            this.dir = this.getEntityWorld().rand.nextInt(4);
        }
        this.just_spawned = 0;
    }

    public void onLivingUpdate() {
        if (this.getEntityWorld().isRemote) {
            super.onLivingUpdate();
        }
    }

    protected void updateAITick() {
    }

    protected void updateAITasks() {
    }

    protected void fall(float par1) {
    }

    protected boolean canDespawn() {
        return false;
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.just_spawned = par1NBTTagCompound.getInteger("JustSpawned");
        this.width = par1NBTTagCompound.getInteger("Iwidth");
        this.depth = par1NBTTagCompound.getInteger("Idepth");
        this.length = par1NBTTagCompound.getInteger("Ilength");
        this.speed = par1NBTTagCompound.getFloat("Ispeed");
        this.dir = par1NBTTagCompound.getInteger("Idir");
        this.blocktype = par1NBTTagCompound.getInteger("Iblocktype");
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("JustSpawned", this.just_spawned);
        par1NBTTagCompound.setInteger("Iwidth", this.width);
        par1NBTTagCompound.setInteger("Idepth", this.depth);
        par1NBTTagCompound.setInteger("Ilength", this.length);
        par1NBTTagCompound.setFloat("Ispeed", this.speed);
        par1NBTTagCompound.setInteger("Idir", this.dir);
        par1NBTTagCompound.setInteger("Iblocktype", this.blocktype);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        boolean xoff = false;
        boolean zoff = false;
        int ix = (int)this.posX;
        int iz = (int)this.posZ;
        if (ix < 0) {
            xoff = true;
            this.posX = ix;
            this.posX -= 0.5;
        } else {
            this.posX = ix;
            this.posX += 0.5;
        }
        if (iz < 0) {
            zoff = true;
            this.posZ = iz;
            this.posZ -= 0.5;
        } else {
            this.posZ = iz;
            this.posZ += 0.5;
        }
        super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }

    private void create_island() {
        int xoff = 0;
        int zoff = 0;
        if (this.posX < 0.0) {
            xoff = 1;
        }
        if (this.posZ < 0.0) {
            zoff = 1;
        }
        for (int k = 0; k <= this.depth; ++k) {
            int il = this.length / (this.depth - k + 1);
            if (il < 1) {
                il = 1;
            }
            for (int i = -il; i <= il; ++i) {
                for (int j = -il; j <= il; ++j) {
                    int ix = (int)this.posX + j - xoff;
                    int iz = (int)this.posZ + i - zoff;
                    if (k == this.depth) {
                        Block bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + k, iz)).getBlock();
                        if (bid == Blocks.AIR) {
                            if (this.getEntityWorld().rand.nextInt(5000) == 1) {
                                this.getEntityWorld().setBlock(ix, (int)this.posY + k, iz, Blocks.WATER);
                                continue;
                            }
                            this.FastSetBlock(ix, (int)this.posY + k, iz, (Block)Blocks.GRASS);
                            if (this.getEntityWorld().rand.nextInt(30) == 1) {
                                if (this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz)).getBlock() != Blocks.AIR) continue;
                                if (this.getEntityWorld().rand.nextInt(2) == 1) {
                                    this.getEntityWorld().setBlock(ix, (int)this.posY + k + 1, iz, OreSpawnMain.MyFlowerPinkBlock);
                                    continue;
                                }
                                this.getEntityWorld().setBlock(ix, (int)this.posY + k + 1, iz, OreSpawnMain.MyFlowerBlueBlock);
                                continue;
                            }
                            if (this.getEntityWorld().rand.nextInt(100) != 1 || this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + k + 1, iz)).getBlock() != Blocks.AIR) continue;
                            OreSpawnMain.OreSpawnTrees.SmallTree(this.getEntityWorld(), ix, (int)this.posY + k + 1, iz);
                            continue;
                        }
                        if (bid != Blocks.BEDROCK) continue;
                        this.setDead();
                        return;
                    }
                    this.mySetBlock(ix, (int)this.posY + k, iz);
                }
            }
        }
        this.getEntityWorld().setBlock((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff, Blocks.AIR);
    }

    private void mySetBlock(int ix, int iy, int iz) {
        Block bid = Blocks.STONE;
        if (this.blocktype == 0) {
            this.blocktype = 1 + this.getEntityWorld().rand.nextInt(8);
        }
        if (this.blocktype == 1 && this.getEntityWorld().rand.nextInt(5) == 1) {
            bid = Blocks.COAL_ORE;
        }
        if (this.blocktype == 2 && this.getEntityWorld().rand.nextInt(10) == 1) {
            bid = Blocks.IRON_ORE;
        }
        if (this.blocktype == 3 && this.getEntityWorld().rand.nextInt(20) == 1) {
            bid = Blocks.EMERALD_ORE;
        }
        if (this.blocktype == 4 && this.getEntityWorld().rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreTitaniumBlock;
        }
        if (this.blocktype == 5 && this.getEntityWorld().rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreUraniumBlock;
        }
        if (this.blocktype == 6 && this.getEntityWorld().rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreRubyBlock;
        }
        if (this.blocktype == 7 && this.getEntityWorld().rand.nextInt(30) == 1) {
            bid = OreSpawnMain.MyOreAmethystBlock;
        }
        if (this.blocktype == 8 && this.getEntityWorld().rand.nextInt(20) == 1) {
            bid = Blocks.GOLD_ORE;
        }
        if (bid == Blocks.STONE) {
            if (this.getEntityWorld().rand.nextInt(3000) == 1) {
                bid = OreSpawnMain.MyEnderPearlBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 2) {
                bid = OreSpawnMain.MyEyeOfEnderBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 3) {
                bid = OreSpawnMain.MyBlockAmethystBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 4) {
                bid = OreSpawnMain.MyBlockRubyBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 5) {
                bid = OreSpawnMain.MyBlockUraniumBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 6) {
                bid = OreSpawnMain.MyBlockTitaniumBlock;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 7) {
                bid = Blocks.GOLD_BLOCK;
            }
            if (this.getEntityWorld().rand.nextInt(3000) == 8) {
                bid = Blocks.DIAMOND_BLOCK;
            }
        }
        this.FastSetBlock(ix, iy, iz, bid);
    }

    private void update_island() {
        int xoff = 0;
        int zoff = 0;
        if (this.dir == 0) {
            this.myZ -= (double)this.speed;
        } else if (this.dir == 1) {
            this.myZ += (double)this.speed;
        } else {
            this.myX = this.dir == 2 ? (this.myX += (double)this.speed) : (this.myX -= (double)this.speed);
        }
        int ke = 0;
        int ks = 0;
        int je = 0;
        int js = 0;
        int mx = (int)this.myX;
        int mz = (int)this.myZ;
        int px = (int)this.posX;
        int pz = (int)this.posZ;
        if (mx != px || mz != pz) {
            Block bid;
            int iz;
            int ix;
            int k;
            int j;
            int il;
            int i;
            if (this.dir == 0) {
                js = 1;
                je = 1;
                ks = -1;
                ke = 1;
            } else if (this.dir == 1) {
                js = -1;
                je = -1;
                ks = -1;
                ke = 1;
            } else if (this.dir == 2) {
                js = -1;
                je = 1;
                ks = -1;
                ke = -1;
            } else {
                js = -1;
                je = 1;
                ks = 1;
                ke = 1;
            }
            if (this.posX < 0.0) {
                xoff = 1;
            }
            if (this.posZ < 0.0) {
                zoff = 1;
            }
            for (i = 0; i <= this.depth; ++i) {
                il = this.length / (this.depth - i + 1);
                if (il < 1) {
                    il = 1;
                }
                for (j = js * il; j <= je * il; ++j) {
                    for (k = ks * il; k <= ke * il; ++k) {
                        ix = (int)this.posX + k - xoff;
                        iz = (int)this.posZ + j - zoff;
                        if (i == this.depth) {
                            bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i + 1, iz)).getBlock();
                            if (bid == OreSpawnMain.MyFlowerPinkBlock || bid == OreSpawnMain.MyFlowerBlueBlock || bid == OreSpawnMain.MyFlowerBlackBlock || bid == OreSpawnMain.MyFlowerScaryBlock) {
                                this.FastSetBlock(ix, (int)this.posY + i + 1, iz, Blocks.AIR);
                            }
                            if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                                this.getEntityWorld().setBlock(ix, (int)this.posY + i, iz, Blocks.AIR);
                            }
                            if (bid == OreSpawnMain.MySkyTreeLog) {
                                this.getEntityWorld().setBlock(ix, (int)this.posY + i + 1, iz, Blocks.AIR);
                                bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i + 2, iz)).getBlock();
                                if (bid == OreSpawnMain.MySkyTreeLog) {
                                    this.getEntityWorld().setBlock(ix, (int)this.posY + i + 2, iz, Blocks.AIR);
                                    bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i + 3, iz)).getBlock();
                                    if (bid == OreSpawnMain.MySkyTreeLog) {
                                        this.getEntityWorld().setBlock(ix, (int)this.posY + i + 3, iz, Blocks.AIR);
                                    }
                                }
                            }
                            if ((bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i, iz)).getBlock()) == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                                this.getEntityWorld().setBlock(ix, (int)this.posY + i, iz, Blocks.AIR);
                            }
                        }
                        this.FastSetBlock(ix, (int)this.posY + i, iz, Blocks.AIR);
                    }
                }
            }
            this.mySetBlock((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff);
            this.posX = mx;
            this.posX = this.myX < 0.0 ? (this.posX -= 0.5) : (this.posX += 0.5);
            this.posZ = mz;
            this.posZ = this.myZ < 0.0 ? (this.posZ -= 0.5) : (this.posZ += 0.5);
            if (this.dir == 0) {
                js = -1;
                je = -1;
                ks = -1;
                ke = 1;
            } else if (this.dir == 1) {
                js = 1;
                je = 1;
                ks = -1;
                ke = 1;
            } else if (this.dir == 2) {
                js = -1;
                je = 1;
                ks = 1;
                ke = 1;
            } else {
                js = -1;
                je = 1;
                ks = -1;
                ke = -1;
            }
            zoff = 0;
            xoff = 0;
            if (this.posX < 0.0) {
                xoff = 1;
            }
            if (this.posZ < 0.0) {
                zoff = 1;
            }
            this.getEntityWorld().setBlock((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff, Blocks.AIR);
            for (i = 0; i <= this.depth; ++i) {
                il = this.length / (this.depth - i + 1);
                if (il < 1) {
                    il = 1;
                }
                for (j = js * il; j <= je * il; ++j) {
                    for (k = ks * il; k <= ke * il; ++k) {
                        ix = (int)this.posX + k - xoff;
                        iz = (int)this.posZ + j - zoff;
                        if (i == this.depth) {
                            bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i, iz)).getBlock();
                            if (bid == Blocks.AIR) {
                                if (this.getEntityWorld().rand.nextInt(5000) == 1) {
                                    this.getEntityWorld().setBlock(ix, (int)this.posY + i, iz, Blocks.WATER);
                                    continue;
                                }
                                this.FastSetBlock(ix, (int)this.posY + i, iz, (Block)Blocks.GRASS);
                                if (this.getEntityWorld().rand.nextInt(30) == 1) {
                                    if (this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i + 1, iz)).getBlock() != Blocks.AIR) continue;
                                    if (this.getEntityWorld().rand.nextInt(2) == 1) {
                                        this.getEntityWorld().setBlock(ix, (int)this.posY + i + 1, iz, OreSpawnMain.MyFlowerPinkBlock);
                                        continue;
                                    }
                                    this.getEntityWorld().setBlock(ix, (int)this.posY + i + 1, iz, OreSpawnMain.MyFlowerBlueBlock);
                                    continue;
                                }
                                if (this.getEntityWorld().rand.nextInt(100) != 1 || this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i + 1, iz)).getBlock() != Blocks.AIR) continue;
                                OreSpawnMain.OreSpawnTrees.SmallTree(this.getEntityWorld(), ix, (int)this.posY + i + 1, iz);
                                continue;
                            }
                            if (bid != Blocks.BEDROCK) continue;
                            this.setDead();
                            return;
                        }
                        bid = this.getEntityWorld().getBlockState(new BlockPos(ix, (int)this.posY + i, iz)).getBlock();
                        if (bid == Blocks.END_STONE) {
                            if (this.getEntityWorld().isRemote) continue;
                            this.getEntityWorld().createExplosion((Entity)this, (double)ix, this.posY + (double)i, (double)iz, 5.0f, true);
                            continue;
                        }
                        this.mySetBlock(ix, (int)this.posY + i, iz);
                    }
                }
            }
            this.getEntityWorld().setBlock((int)this.posX - xoff, (int)this.posY, (int)this.posZ - zoff, Blocks.AIR);
        }
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)OreSpawnMain.MyIslandBlock);
    }

    public void FastSetBlock(int ix, int iy, int iz, Block id) {
        OreSpawnMain.setBlockFast(this.getEntityWorld(), ix, iy, iz, id, 0, 3);
    }
}


}