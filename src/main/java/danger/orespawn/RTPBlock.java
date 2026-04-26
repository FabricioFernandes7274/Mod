/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RTPBlock
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public RTPBlock(int i) {
        super(Material.ROCK);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void onEntityWalking(World world, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof net.minecraft.entity.player.EntityPlayer) {
            net.minecraft.entity.player.EntityPlayer p = (net.minecraft.entity.player.EntityPlayer)par5Entity;
            net.minecraft.entity.player.EntityPlayerMP mp = null;
            if (par5Entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
                mp = (net.minecraft.entity.player.EntityPlayerMP)par5Entity;
            }
            int x = par2;
            int y = par3;
            int z = par4;
            boolean found = false;
            block0: for (int tries = 0; tries < 1000 && !found; ++tries) {
                x = world.rand.nextInt(2) == 0 ? par2 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8) : par2 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                z = world.rand.nextInt(2) == 0 ? par4 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8) : par4 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                for (y = par3 - 4; y <= par3 + 4; ++y) {
                    if (!world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z)).getBlock().getMaterial().isSolid() || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock() != Blocks.AIR || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z)).getBlock() != Blocks.AIR) continue;
                    found = true;
                    continue block0;
                }
            }
            if (found) {
                if (mp != null) {
                    mp.connection.setPlayerLocation((double)((float)x + 0.5f), (double)y, (double)((float)z + 0.5f), p.rotationYaw, 0.0f);
                } else {
                    p.setLocationAndAngles((double)((float)x + 0.5f), (double)y, (double)((float)z + 0.5f), p.rotationYaw, 0.0f);
                }
                for (int var3 = 0; var3 < 6; ++var3) {
                    world.spawnParticle("smoke", (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                    world.spawnParticle("explode", (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                    world.spawnParticle("reddust", (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                }
                p.world.playSoundAtEntity((Entity)p, "random.explode", 1.0f, 1.5f);
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

