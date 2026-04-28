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
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRuby
extends Block {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite blockIcon;
    public BlockRuby(int par1) {
        super(Material.ROCK);
        this.setHardness(4.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(0.4f);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public void onEntityCollidedWithBlock(World worldIn, int par2, int par3, int par4, Entity par5Entity) {
        if (this == OreSpawnMain.MyBlockMobzillaScaleBlock && par5Entity instanceof net.minecraft.entity.EntityLivingBase) {
            ((net.minecraft.entity.EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH.getId(), 200, 0));
        }
    }

    public void onEntityWalking(World worldIn, int par2, int par3, int par4, Entity par5Entity) {
        if (this == OreSpawnMain.MyBlockMobzillaScaleBlock && par5Entity instanceof net.minecraft.entity.EntityLivingBase) {
            ((net.minecraft.entity.EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH.getId(), 200, 0));
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        //this.blockIcon = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

