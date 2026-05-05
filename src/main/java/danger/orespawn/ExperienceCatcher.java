package danger.orespawn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ExperienceCatcher extends Item {

    public ExperienceCatcher() {
        // Construtor vazio (sem aquele 'int i' de versões antigas)
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setUnlocalizedName("experience_catcher");
        this.setRegistryName("experience_catcher");
    }

    /**
     * Chamado quando o jogador clica com o botão direito em um bloco.
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        player.swingArm(hand);

        if (!worldIn.isRemote) {
            // Cria uma "caixa de colisão" acima do bloco clicado para procurar orbs de XP
            AxisAlignedBB bb = new AxisAlignedBB(
                    pos.getX() - 0.5D + hitX, pos.getY(), pos.getZ() - 0.5D + hitZ,
                    pos.getX() + 0.5D + hitX, pos.getY() + 2.0D, pos.getZ() + 0.5D + hitZ
            );

            List<EntityXPOrb> orbs = worldIn.getEntitiesWithinAABB(EntityXPOrb.class, bb);
            boolean caught = false;

            for (EntityXPOrb orb : orbs) {
                // Se o XP valer pelo menos 3 e passar na chance de 80% (rand 5 != 1)
                if (orb.xpValue >= 3 && worldIn.rand.nextInt(5) != 1) {
                    orb.setDead(); // Deleta o orb de XP

                    // Dropa o Frasco de Experiência
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + hitX, pos.getY() + 1.0D, pos.getZ() + hitZ, new ItemStack(Items.EXPERIENCE_BOTTLE)));
                    // Devolve uma Linha
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + hitX, pos.getY() + 1.0D, pos.getZ() + hitZ, new ItemStack(Items.STRING)));
                    // Devolve um Graveto
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + hitX, pos.getY() + 1.0D, pos.getZ() + hitZ, new ItemStack(Items.STICK)));

                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1); // Gasta a rede
                    }
                    caught = true;
                    break; // Captura apenas 1 orb por clique
                }
            }

            // Se você clicar e errar o Orb, você derruba a rede no chão
            if (!caught) {
                EntityItem droppedNet = new EntityItem(worldIn, pos.getX() + hitX, pos.getY() + 1.0D, pos.getZ() + hitZ, new ItemStack(this));
                worldIn.spawnEntity(droppedNet);
                
                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    /**
     * Chamado quando clica no ar (sem mirar num bloco).
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        playerIn.swingArm(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}