package danger.orespawn;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityUnstableAnt extends EntityAnt {

    public EntityUnstableAnt(World worldIn) {
        // O construtor da EntityAnt já cuida do tamanho e da Inteligência Artificial
        super(worldIn);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player instanceof EntityPlayerMP && !this.world.isRemote) {
            ItemStack itemstack = player.getHeldItem(hand);

            // A formiga instável só teletransporta se o jogador clicar com a mão vazia
            if (itemstack.isEmpty()) {
                // Alvo da Unstable Ant: DimensionID4 (Danger/Chaos Dimension)
                int targetDimension = (player.dimension != OreSpawnMain.DimensionID4) ? OreSpawnMain.DimensionID4 : 0;
                
                WorldServer worldServer = player.getServer().getWorld(targetDimension);
                
                // Teletransporte da 1.12.2 usando o OreSpawnTeleporter
                player.changeDimension(targetDimension, new OreSpawnTeleporter(worldServer));
                return true;
            }
        }
        
        // Se a mão não estiver vazia, faz a interação padrão
        return super.processInteract(player, hand);
    }
}