package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ExperienceSword extends ItemSword {

    public ExperienceSword(Item.ToolMaterial material) {
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(1400); // Durabilidade
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setUnlocalizedName("experience_sword");
        this.setRegistryName("experience_sword");
    }

    /**
     * Chamado quando o item é criado no jogo (crafting ou pegando do criativo)
     */
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        this.applyEnchantments(stack);
    }

    /**
     * Garante os encantamentos passivamente e aplica o bônus da Armadura de Experiência.
     */
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            this.applyEnchantments(stack);
        }

        // Lógica de Sinergia com a Armadura de Experiência
        if (entityIn instanceof EntityPlayer && !worldIn.isRemote) {
            EntityPlayer player = (EntityPlayer) entityIn;

            if (worldIn.rand.nextInt(60) == 0) {
                // Checa as peças de armadura equipadas (Botas, Calças, Peito, Capacete)
                for (ItemStack armorStack : player.getArmorInventoryList()) {
                    if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemOreSpawnArmor) {
                        ItemOreSpawnArmor armor = (ItemOreSpawnArmor) armorStack.getItem();
                        
                        // material id 4 = Experience Armor no código original do OreSpawn
                        if (armor.get_armor_material() == 4) {
                            int chance = 10;
                            double yOffset = 1.0D;

                            // Ajusta a chance e a altura das partículas baseado no tipo de peça
                            EntityEquipmentSlot slot = armor.armorType;
                            if (slot == EntityEquipmentSlot.HEAD) { chance = 10; yOffset = 1.5D; }
                            else if (slot == EntityEquipmentSlot.CHEST) { chance = 20; yOffset = 1.25D; }
                            else if (slot == EntityEquipmentSlot.LEGS) { chance = 30; yOffset = 0.75D; }
                            else if (slot == EntityEquipmentSlot.FEET) { chance = 40; yOffset = 0.25D; }

                            // Dá 1 de XP passivo e spawna partículas
                            if (worldIn.rand.nextInt(chance) == 0) {
                                player.addExperience(1);
                                ((WorldServer) worldIn).spawnParticle(
                                        EnumParticleTypes.PORTAL, 
                                        player.posX, player.posY + yOffset, player.posZ, 
                                        1, 0.2D, 0.2D, 0.2D, 0.0D
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adiciona os Encantamentos Fixos da Arma
     */
    private void applyEnchantments(ItemStack stack) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack) <= 0) {
            stack.addEnchantment(Enchantments.SHARPNESS, 2);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) <= 0) {
            stack.addEnchantment(Enchantments.UNBREAKING, 3);
        }
    }

    /**
     * O núcleo da arma: Dá XP ao bater e o dano escala com seu Level atual.
     */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;

            if (!player.world.isRemote) {
                // 1. Ganha 10 de XP instântaneo a cada pancada
                player.addExperience(10);

                // 2. O Dano Extra é igual à METADE do nível do jogador
                float bonusDamage = (float) (player.experienceLevel / 2);

                if (bonusDamage > 0.0F && target != null) {
                    target.attackEntityFrom(DamageSource.causePlayerDamage(player), bonusDamage);
                    
                    // 3. Spawna partículas de portal de acordo com o dano bônus causado
                    int particleCount = (int) (bonusDamage / 2.0F) + 1;
                    ((WorldServer) player.world).spawnParticle(
                            EnumParticleTypes.PORTAL, 
                            target.posX, target.posY + 1.0D, target.posZ, 
                            particleCount, 0.5D, 0.5D, 0.5D, 0.1D
                    );
                }
            }
        }
        // Gasta 1 de durabilidade da arma
        stack.damageItem(1, attacker);
        return true;
    }
}