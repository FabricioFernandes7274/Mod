/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDuctTape
extends Item {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private Block field_150935_a;

    public ItemDuctTape(int par1, Block par2Block) {
        this.field_150935_a = par2Block;
    }

    public boolean onItemUse(ItemStack p_77648_1_, net.minecraft.entity.player.EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        int i1;
        Block block = p_77648_3_.getBlockState(new BlockPos(p_77648_4_, p_77648_5_, p_77648_6_)).getBlock();
        if (block == Blocks.SNOW_LAYER && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 7) < 1) {
            p_77648_7_ = 1;
        } else if (block != Blocks.VINE && block != Blocks.TALLGRASS && block != Blocks.DEADBUSH) {
            if (p_77648_7_ == 0) {
                --p_77648_5_;
            }
            if (p_77648_7_ == 1) {
                ++p_77648_5_;
            }
            if (p_77648_7_ == 2) {
                --p_77648_6_;
            }
            if (p_77648_7_ == 3) {
                ++p_77648_6_;
            }
            if (p_77648_7_ == 4) {
                --p_77648_4_;
            }
            if (p_77648_7_ == 5) {
                ++p_77648_4_;
            }
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_1_.setCount(0) {
            return false);
        }
        if (p_77648_3_.canPlaceEntityOnSide(this.field_150935_a, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, (Entity)null, p_77648_1_) && p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, this.field_150935_a, i1 = this.field_150935_a.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0), 3)) {
            if (p_77648_3_.getBlockState(new BlockPos(p_77648_4_, p_77648_5_, p_77648_6_)).getBlock() == this.field_150935_a) {
                this.field_150935_a.onBlockPlacedBy(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, (net.minecraft.entity.EntityLivingBase)p_77648_2_, p_77648_1_);
                this.field_150935_a.onPostBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, i1);
            }
            p_77648_3_.playSoundEffect((double)((float)p_77648_4_ + 0.5f), (double)((float)p_77648_5_ + 0.5f), (double)((float)p_77648_6_ + 0.5f), this.field_150935_a.stepSound.getPlaceSound(), (this.field_150935_a.stepSound.getVolume() + 1.0f) / 2.0f, this.field_150935_a.stepSound.getFrequency() * 0.8f);
            p_77648_1_.shrink(1);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

