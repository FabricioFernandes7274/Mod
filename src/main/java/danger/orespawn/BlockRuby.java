package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRuby extends Block {

    public BlockRuby() {
        super(Material.ROCK);
        this.setHardness(4.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(0.4f);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        // Substitui o renderAsNormalBlock antigo
        return true;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        this.applyMobzillaEffect(entityIn);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        this.applyMobzillaEffect(entityIn);
    }

    /**
     * Lógica original do OreSpawn: Se for o bloco de escama do Mobzilla, dá força ao jogador/entidade.
     */
    private void applyMobzillaEffect(Entity entity) {
        if (this == OreSpawnMain.MyBlockMobzillaScaleBlock && entity instanceof EntityLivingBase) {
            // Na 1.12.2 usamos MobEffects.STRENGTH em vez do ID 5
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 200, 0));
        }
    }
}