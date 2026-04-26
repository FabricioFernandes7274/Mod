/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
package danger.orespawn;
import net.minecraft.util.math.AxisAlignedBB;

import danger.orespawn.OreSpawnMain;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class OreSpawnTeleporter
extends Teleporter {
    private WorldServer world;
    private World oldWorld;
    private Random random;
    private int newdim;

    public OreSpawnTeleporter(WorldServer par1WorldServer, int dim, World par2World) {
        super(par1WorldServer);
        this.world = par1WorldServer;
        this.oldWorld = par2World;
        this.random = new Random(par1WorldServer.getSeed());
        this.newdim = dim;
    }

    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        this.justPutMe(par1Entity);
    }

    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        this.justPutMe(par1Entity);
        return true;
    }

    public boolean makePortal(Entity par1Entity) {
        return true;
    }

    private boolean isGroundBlock(Block bid) {
        if (bid == Blocks.AIR) {
            return false;
        }
        if (bid == Blocks.DIRT) {
            return true;
        }
        if (bid == Blocks.GRASS) {
            return true;
        }
        if (bid == Blocks.STONE) {
            return true;
        }
        if (bid == Blocks.END_STONE) {
            return true;
        }
        if (bid == Blocks.NETHERRACK) {
            return true;
        }
        if (bid == Blocks.COBBLESTONE) {
            return true;
        }
        if (bid == Blocks.SAND) {
            return true;
        }
        if (bid == Blocks.SANDSTONE) {
            return true;
        }
        return bid == Blocks.FARMLAND;
    }

    public boolean justPutMe(Entity par1Entity) {
        int posX = (int)par1Entity.posX;
        int posZ = (int)par1Entity.posZ;
        int posY = 120;
        boolean found = false;
        int inarow = 0;
        boolean airfound = false;
        for (int i = 0; i < 1000 && !found; ++i) {
            for (posY = 180; posY > 1; --posY) {
                Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY + 1, posZ)).getBlock();
                if (bid == Blocks.AIR || bid == null) {
                    inarow = 0;
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY, posZ)).getBlock();
                    if (bid != Blocks.AIR && bid != null) continue;
                    airfound = true;
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY - 1, posZ)).getBlock();
                    if (bid == Blocks.AIR || bid == null) continue;
                    if (this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY - 1, posZ)).getBlock().getMaterial().isSolid()) {
                        found = true;
                        break;
                    }
                    if (bid != Blocks.TALLGRASS || !this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY - 2, posZ)).getBlock().getMaterial().isSolid()) break;
                    found = true;
                    --posY;
                    break;
                }
                if (this.isGroundBlock(bid)) {
                    ++inarow;
                }
                if (airfound && inarow >= 3) break;
            }
            if (found) continue;
            posX = (int)par1Entity.posX + this.world.rand.nextInt(3 + i / 5) - this.world.rand.nextInt(3 + i / 5);
            if (i > 100) {
                posX = posX + OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5) - OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5);
            }
            if (i > 500) {
                posX = posX + this.random.nextInt(2 + i / 5) - this.random.nextInt(2 + i / 5);
            }
            posZ = (int)par1Entity.posZ + this.world.rand.nextInt(3 + i / 5) - this.world.rand.nextInt(3 + i / 5);
            if (i > 100) {
                posZ = posZ + OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5) - OreSpawnMain.OreSpawnRand.nextInt(2 + i / 5);
            }
            if (i > 500) {
                posZ = posZ + this.random.nextInt(2 + i / 5) - this.random.nextInt(2 + i / 5);
            }
            airfound = false;
            inarow = 0;
        }
        if (!found) {
            posX = (int)par1Entity.posX;
            posZ = (int)par1Entity.posZ;
            for (posY = 180; posY > 1 && (Blocks.AIR != this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY + 1, posZ)).getBlock() || Blocks.AIR != this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY, posZ)).getBlock() || Blocks.AIR == this.world.getBlockState(new net.minecraft.util.math.BlockPos(posX, posY - 1, posZ)).getBlock()); --posY) {
            }
        }
        double oldX = par1Entity.posX;
        double oldY = par1Entity.posY;
        double oldZ = par1Entity.posZ;
        double newX = posX;
        double newZ = posZ;
        double newY = posY;
        newX = newX < 0.0 ? (newX -= 0.5) : (newX += 0.5);
        newZ = newZ < 0.0 ? (newZ -= 0.5) : (newZ += 0.5);
        par1Entity.setLocationAndAngles(newX, newY, newZ, par1Entity.rotationYaw, 0.0f);
        par1Entity.motionZ = 0.0;
        par1Entity.motionY = 0.0;
        par1Entity.motionX = 0.0;
        MinecraftServer minecraftserver = net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance();
        WorldServer worldserver = minecraftserver.getWorld(this.oldWorld.provider.getDimension());
        WorldServer worldserver1 = minecraftserver.getWorld(this.newdim);
        if (par1Entity instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer ep = (net.minecraft.entity.player.EntityPlayer)par1Entity;
            AxisAlignedBB bb = new AxisAlignedBB((double)(oldX - 24.0), (double)(oldY - 12.0), (double)(oldZ - 24.0), (double)(oldX + 24.0), (double)(oldY + 12.0), (double)(oldZ + 24.0));
            List var5 = this.oldWorld.getEntitiesWithinAABB(EntityTameable.class, bb);
            for (Entity var3 : var5) {
                EntityTameable et = (EntityTameable)var3;
                if (et.isSitting()) continue;
                String p1 = ep.getUniqueID().toString();
                String p2 = et.getUniqueID();
                if ((p1 == null || p2 == null || !p1.equals(p2)) && !et.getGameProfile((net.minecraft.entity.EntityLivingBase)ep)) continue;
                this.sendToThisDimension(var3, newX, newY, newZ, (int)ep.rotationYaw);
            }
        }
        return true;
    }

    public void sendToThisDimension(Entity e, double newX, double newY, double newZ, int ro) {
        if (this.oldWorld.isRemote) {
            return;
        }
        e.world.removeEntity(e);
        e.isDead() = false;
        e.setLocationAndAngles(newX, newY, newZ, (float)ro, 0.0f);
        e.motionZ = 0.0;
        e.motionY = 0.0;
        e.motionX = 0.0;
        e.setWorld((World)this.world);
        Entity var6 = EntityList.createEntityByName((String)EntityList.getEntityString((Entity)e), (World)this.world);
        if (var6 != null) {
            var6.copyDataFrom(e, true);
            var6.setLocationAndAngles(newX, newY, newZ, (float)ro, 0.0f);
            var6.motionZ = 0.0;
            var6.motionY = 0.0;
            var6.motionX = 0.0;
            var6.setWorld((World)this.world);
            this.world.spawnEntity(var6);
        }
        e.isDead() = true;
    }

    public void removeStalePortalLocations(long par1) {
    }
}

