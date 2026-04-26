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
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreSalt
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public OreSalt(int par1) {
        super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(2.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityAnt) {
            par5Entity.attackEntityFrom(DamageSource.CACTUS, 5.0f);
        }
    }

    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityAnt) {
            par5Entity.attackEntityFrom(DamageSource.CACTUS, 5.0f);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5));
    }
}

