package danger.orespawn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityRainbowAnt extends EntityAnt {

    public EntityRainbowAnt(World worldIn) {
        // O "super" chama o construtor da EntityAnt, que já define o tamanho 0.1 
        // e configura a inteligência artificial (nadar, passear e fugir)
        super(worldIn);
    }

    // Não precisamos reescrever o applyEntityAttributes() ou initEntityAI()
    // porque eles serão herdados da EntityAnt exatamente com os mesmos valores.

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);

            // A formiga só transporta se o jogador clicar com a mão vazia
            if (itemstack.isEmpty()) {
                // Alvo da Rainbow Ant: DimensionID3 em vez do DimensionID padrão
                int targetDimension = (player.dimension != OreSpawnMain.DimensionID3) ? OreSpawnMain.DimensionID3 : 0;
                
                WorldServer worldServer = player.getServer().getWorld(targetDimension);
                
                // Realiza o teletransporte seguro da 1.12.2 usando a nossa classe de Teleporter
                player.changeDimension(targetDimension, new OreSpawnTeleporter(worldServer));
                return true;
            }
        }
        
        // Se a mão não estiver vazia, faz a interação padrão de criaturas
        return super.processInteract(player, hand);
    }
}    
