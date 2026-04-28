/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RatSword
extends ItemSword {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int weaponDamage;
    private final Item.ToolMaterial toolMaterial;

    public RatSword(int par1, Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDurability(1300);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public String getMaterialName() {
        return "Rat";
    }

    public boolean hitEntity(ItemStack par1ItemStack, net.minecraft.entity.EntityLivingBase par2EntityLiving, net.minecraft.entity.EntityLivingBase par3EntityLiving) {
        int var2 = 5;
        if (par2EntityLiving != null && !par2EntityLiving.world.isRemote) {
            int num = 1 + par2EntityLiving.world.rand.nextInt(6);
            for (int i = 0; i < num; ++i) {
                Rat r = null;
                r = (Rat)RatSword.spawnCreature(par2EntityLiving.world, 0, "Rat", par2EntityLiving.posX + (double)(par2EntityLiving.world.rand.nextFloat() - par2EntityLiving.world.rand.nextFloat()) * 0.5, par2EntityLiving.posY + (double)par2EntityLiving.world.rand.nextFloat() + 0.01, par2EntityLiving.posZ + (double)(par2EntityLiving.world.rand.nextFloat() - par2EntityLiving.world.rand.nextFloat()) * 0.5);
                if (r == null) continue;
                r.setOwner(par3EntityLiving);
            }
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public static Entity spawnCreature(World par0World, int par1, String name, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = name == null ? EntityList.createEntityByID((int)par1, (World)par0World) : EntityList.createEntityByIDFromName((String)name, (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

