/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.renderer.texture.net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemArmor$ArmorMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOreSpawnArmor
extends ItemArmor {
    protected net.minecraft.client.renderer.texture.TextureAtlasSprite itemTexture;
    private int armor_material = 0;
    private int armor_type = 0;
    private int original_d = 0;

    public ItemOreSpawnArmor(int par1, ItemArmor.ArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
        super(par2EnumArmorMaterial, par3, par4);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.armor_material = 0;
        if (par2EnumArmorMaterial == OreSpawnMain.armorLAVAEEL) {
            this.armor_material = 1;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorMOTHSCALE) {
            this.armor_material = 2;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorEMERALD) {
            this.armor_material = 3;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorEXPERIENCE) {
            this.armor_material = 4;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorRUBY) {
            this.armor_material = 5;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorAMETHYST) {
            this.armor_material = 6;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorPINK) {
            this.armor_material = 7;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorTIGERSEYE) {
            this.armor_material = 8;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorPEACOCK) {
            this.armor_material = 9;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorMOBZILLA) {
            this.armor_material = 10;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorROYAL) {
            this.armor_material = 11;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorLAPIS) {
            this.armor_material = 12;
        }
        if (par2EnumArmorMaterial == OreSpawnMain.armorQUEEN) {
            this.armor_material = 13;
        }
        this.armor_type = par4;
        this.original_d = this.damageReduceAmount;
    }

    public int get_armor_material() {
        return this.armor_material;
    }

    public int get_armor_type() {
        return this.armor_type;
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, net.minecraft.entity.player.EntityPlayer par3EntityPlayer) {
        ArmorStats a = null;
        if (this.armor_material == 0) {
            a = OreSpawnMain.Ultimate_armorstats;
        }
        if (this.armor_material == 1) {
            a = OreSpawnMain.LavaEel_armorstats;
        }
        if (this.armor_material == 2) {
            a = OreSpawnMain.MothScale_armorstats;
        }
        if (this.armor_material == 3) {
            a = OreSpawnMain.Emerald_armorstats;
        }
        if (this.armor_material == 4) {
            a = OreSpawnMain.Experience_armorstats;
        }
        if (this.armor_material == 5) {
            a = OreSpawnMain.Ruby_armorstats;
        }
        if (this.armor_material == 6) {
            a = OreSpawnMain.Amethyst_armorstats;
        }
        if (this.armor_material == 7) {
            a = OreSpawnMain.Pink_armorstats;
        }
        if (this.armor_material == 8) {
            a = OreSpawnMain.TigersEye_armorstats;
        }
        if (this.armor_material == 9) {
            a = OreSpawnMain.Peacock_armorstats;
        }
        if (this.armor_material == 10) {
            a = OreSpawnMain.Mobzilla_armorstats;
        }
        if (this.armor_material == 11) {
            a = OreSpawnMain.Royal_armorstats;
        }
        if (this.armor_material == 12) {
            a = OreSpawnMain.Lapis_armorstats;
        }
        if (this.armor_material == 13) {
            a = OreSpawnMain.Queen_armorstats;
        }
        if (a != null) {
            if (a.e_protection != 0) {
                par1ItemStack.addEnchantment(Enchantments.PROTECTION, a.e_protection);
            }
            if (a.e_fireprotection != 0) {
                par1ItemStack.addEnchantment(Enchantments.FIRE_PROTECTION, a.e_fireprotection);
            }
            if (a.e_blastprotection != 0) {
                par1ItemStack.addEnchantment(Enchantments.BLAST_PROTECTION, a.e_blastprotection);
            }
            if (a.e_projectileprotection != 0) {
                par1ItemStack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, a.e_projectileprotection);
            }
            if (a.e_unbreaking != 0) {
                par1ItemStack.addEnchantment(Enchantments.UNBREAKING, a.e_unbreaking);
            }
            if (this.armor_type == 3 && a.e_featherfalling != 0) {
                par1ItemStack.addEnchantment(Enchantments.FEATHER_FALLING, a.e_featherfalling);
            }
            if (this.armor_type == 0) {
                if (a.e_respiration != 0) {
                    par1ItemStack.addEnchantment(Enchantments.RESPIRATION, a.e_respiration);
                }
                if (a.e_aquaaffinity != 0) {
                    par1ItemStack.addEnchantment(Enchantments.AQUA_AFFINITY, a.e_aquaaffinity);
                }
            }
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        ArmorStats a = null;
        int lvl = 0;
        int enchanted = 0;
        if (this.armor_material == 0) {
            a = OreSpawnMain.Ultimate_armorstats;
        }
        if (this.armor_material == 1) {
            a = OreSpawnMain.LavaEel_armorstats;
        }
        if (this.armor_material == 2) {
            a = OreSpawnMain.MothScale_armorstats;
        }
        if (this.armor_material == 3) {
            a = OreSpawnMain.Emerald_armorstats;
        }
        if (this.armor_material == 4) {
            a = OreSpawnMain.Experience_armorstats;
        }
        if (this.armor_material == 5) {
            a = OreSpawnMain.Ruby_armorstats;
        }
        if (this.armor_material == 6) {
            a = OreSpawnMain.Amethyst_armorstats;
        }
        if (this.armor_material == 7) {
            a = OreSpawnMain.Pink_armorstats;
        }
        if (this.armor_material == 8) {
            a = OreSpawnMain.TigersEye_armorstats;
        }
        if (this.armor_material == 9) {
            a = OreSpawnMain.Peacock_armorstats;
        }
        if (this.armor_material == 10) {
            a = OreSpawnMain.Mobzilla_armorstats;
        }
        if (this.armor_material == 11) {
            a = OreSpawnMain.Royal_armorstats;
        }
        if (this.armor_material == 12) {
            a = OreSpawnMain.Lapis_armorstats;
        }
        if (this.armor_material == 13) {
            a = OreSpawnMain.Queen_armorstats;
        }
        if (a != null) {
            enchanted = a.e_aquaaffinity + a.e_blastprotection + a.e_featherfalling + a.e_fireprotection;
            if ((enchanted += a.e_projectileprotection + a.e_protection + a.e_respiration + a.e_unbreaking) > 0) {
                lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, (ItemStack)stack);
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.RESPIRATION, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.AQUA_AFFINITY, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, (ItemStack)stack);
                }
                if (lvl <= 0) {
                    lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FEATHER_FALLING, (ItemStack)stack);
                }
                if (lvl == 0) {
                    if (a.e_protection != 0) {
                        stack.addEnchantment(Enchantments.PROTECTION, a.e_protection);
                    }
                    if (a.e_fireprotection != 0) {
                        stack.addEnchantment(Enchantments.FIRE_PROTECTION, a.e_fireprotection);
                    }
                    if (a.e_blastprotection != 0) {
                        stack.addEnchantment(Enchantments.BLAST_PROTECTION, a.e_blastprotection);
                    }
                    if (a.e_projectileprotection != 0) {
                        stack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, a.e_projectileprotection);
                    }
                    if (a.e_unbreaking != 0) {
                        stack.addEnchantment(Enchantments.UNBREAKING, a.e_unbreaking);
                    }
                    if (this.armor_type == 3 && a.e_featherfalling != 0) {
                        stack.addEnchantment(Enchantments.FEATHER_FALLING, a.e_featherfalling);
                    }
                    if (this.armor_type == 0) {
                        if (a.e_respiration != 0) {
                            stack.addEnchantment(Enchantments.RESPIRATION, a.e_respiration);
                        }
                        if (a.e_aquaaffinity != 0) {
                            stack.addEnchantment(Enchantments.AQUA_AFFINITY, a.e_aquaaffinity);
                        }
                    }
                }
            }
        }
    }

    public String getArmorTexture(ItemStack armor, Entity e, int slot, String layer) {
        if (this.armor_type == 0 || this.armor_type == 1 || this.armor_type == 3) {
            if (this.armor_material == 0) {
                return "orespawn:ultimate_1.png";
            }
            if (this.armor_material == 1) {
                return "orespawn:lavaeel_1.png";
            }
            if (this.armor_material == 2) {
                return "orespawn:mothscale_1.png";
            }
            if (this.armor_material == 4) {
                return "orespawn:experience_1.png";
            }
            if (this.armor_material == 5) {
                return "orespawn:ruby_1.png";
            }
            if (this.armor_material == 6) {
                return "orespawn:amethyst_1.png";
            }
            if (this.armor_material == 7) {
                return "orespawn:pink_1.png";
            }
            if (this.armor_material == 8) {
                return "orespawn:tigerseye_1.png";
            }
            if (this.armor_material == 9) {
                return "orespawn:peacock_1.png";
            }
            if (this.armor_material == 10) {
                return "orespawn:mobzilla_1.png";
            }
            if (this.armor_material == 11) {
                return "orespawn:royal_1.png";
            }
            if (this.armor_material == 12) {
                return "orespawn:lapis_1.png";
            }
            if (this.armor_material == 13) {
                return "orespawn:queen_1.png";
            }
            return "orespawn:emerald_1.png";
        }
        if (this.armor_material == 0) {
            return "orespawn:ultimate_2.png";
        }
        if (this.armor_material == 1) {
            return "orespawn:lavaeel_2.png";
        }
        if (this.armor_material == 2) {
            return "orespawn:mothscale_2.png";
        }
        if (this.armor_material == 4) {
            return "orespawn:experience_2.png";
        }
        if (this.armor_material == 5) {
            return "orespawn:ruby_2.png";
        }
        if (this.armor_material == 6) {
            return "orespawn:amethyst_2.png";
        }
        if (this.armor_material == 7) {
            return "orespawn:pink_2.png";
        }
        if (this.armor_material == 8) {
            return "orespawn:tigerseye_2.png";
        }
        if (this.armor_material == 9) {
            return "orespawn:peacock_2.png";
        }
        if (this.armor_material == 10) {
            return "orespawn:mobzilla_2.png";
        }
        if (this.armor_material == 11) {
            return "orespawn:royal_2.png";
        }
        if (this.armor_material == 12) {
            return "orespawn:lapis_2.png";
        }
        if (this.armor_material == 13) {
            return "orespawn:queen_2.png";
        }
        return "orespawn:emerald_2.png";
    }

    public void onArmorTick(World world, net.minecraft.entity.player.EntityPlayer player, ItemStack itemStack) {
        ItemStack boots = null;
        Object ia = null;
        Object it = null;
        if ((this.armor_material == 11 || this.armor_material == 9) && player != null && (boots = player.getEquipmentInSlot(1)) != null && (boots.getItem() == OreSpawnMain.RoyalBoots && OreSpawnMain.RoyalGlideEnable != 0 || boots.getItem() == OreSpawnMain.PeacockFeatherBoots)) {
            if (player.motionY < (double)-0.1f) {
                player.motionY = -0.1f;
            }
            player.fallDistance = 0.0f;
        }
        if (this.armor_material == 13 && player != null && (boots = player.getEquipmentInSlot(1)) != null && boots.getItem() == OreSpawnMain.QueenBoots && OreSpawnMain.RoyalGlideEnable != 0) {
            if (player.motionY < -0.25) {
                player.motionY = -0.25;
            }
            player.fallDistance = 0.0f;
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void registerTextures(net.minecraft.client.renderer.texture.TextureMap iconRegister) {
        this.itemTexture = iconRegister.registerSprite(new net.minecraft.util.ResourceLocation("orespawn:" + this.getUnlocalizedName().substring(5)));
    }
}

