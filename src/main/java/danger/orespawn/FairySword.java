package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class FairySword extends ItemSword {

    public FairySword(Item.ToolMaterial material) {
        // Removemos o "int par1" que era o antigo ID do item da versão 1.7.10
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(1300); // Substitui setMaxDurability
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.setUnlocalizedName("fairy_sword");
        this.setRegistryName("fairy_sword");
    }

    /**
     * O núcleo da arma: Bateu no inimigo, spawnou Fadas
     */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        // Verifica se existe alvo e se estamos operando no servidor
        if (target != null && !target.world.isRemote) {
            World world = target.world;
            
            // Escolhe gerar entre 1 e 3 fadas por hit
            int numFaries = 1 + world.rand.nextInt(3);
            
            for (int i = 0; i < numFaries; ++i) {
                // Instanciação direta e limpa da entidade!
                Fairy fairy = new Fairy(world);
                
                // Define uma posição aleatória ao redor da cabeça/tronco do alvo
                fairy.setLocationAndAngles(
                    target.posX + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5D, 
                    target.posY + world.rand.nextFloat() + 0.01D, 
                    target.posZ + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5D, 
                    world.rand.nextFloat() * 360.0F, 
                    0.0F
                );

                // Define o dono da Fada se quem bateu for um jogador
                if (attacker instanceof EntityPlayer) {
                    fairy.setOwner((EntityPlayer) attacker);
                }

                // Spawna no mundo e toca o som padrão da Fada
                world.spawnEntity(fairy);
                fairy.playLivingSound();
            }
        }
        
        // Gasta 1 de durabilidade
        stack.damageItem(1, attacker);
        return true;
    }

    // Retorna o tempo máximo da animação de uso do item (bloqueio de espada)
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }
}