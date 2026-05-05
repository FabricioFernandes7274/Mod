package danger.orespawn;

import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class DungeonSpawnerBlock extends BlockReed {

    protected DungeonSpawnerBlock() {
        super();
        // Na 1.12.2, setBlockBounds foi substituído por getBoundingBox, 
        // mas como herdamos de BlockReed, ele já tem uma caixa definida.
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        // Verifica se o bloco abaixo é sólido
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        for (int j1 = 0; j1 < 5; ++j1) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, 
                (double)pos.getX() + rand.nextFloat(), 
                (double)pos.getY() + rand.nextFloat(), 
                (double)pos.getZ() + rand.nextFloat(), 
                (rand.nextFloat() - rand.nextFloat()) / 4.0D, 
                (double)rand.nextFloat() / 2.0D, 
                (rand.nextFloat() - rand.nextFloat()) / 4.0D);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            // Agenda a atualização para gerar a estrutura em ~20 segundos (400 ticks)
            worldIn.scheduleUpdate(pos, this, 400);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) {
            return;
        }

        int clickedX = pos.getX();
        int clickedY = pos.getY();
        int clickedZ = pos.getZ();

        // Remove o bloco e o bloco acima antes de gerar a dungeon
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);

        int type = rand.nextInt(50);
        
        // Lógica de geração de estruturas (OreSpawn original)
        if (type == 0) OreSpawnMain.OreSpawnTrees.FairyTree(world, clickedX, clickedY, clickedZ);
        else if (type == 1) OreSpawnMain.OreSpawnTrees.FairyCastleTree(world, clickedX, clickedY, clickedZ);
        else if (type == 2) OreSpawnMain.MyDungeon.makeEnormousCastle(world, clickedX, clickedY, clickedZ);
        else if (type == 3) OreSpawnMain.MyDungeon.makeRotatorStation(world, clickedX, clickedY, clickedZ);
        else if (type == 4) OreSpawnMain.MyDungeon.makeBeeHive(world, clickedX, clickedY, clickedZ);
        else if (type == 5) OreSpawnMain.MyDungeon.makeHauntedHouse(world, clickedX, clickedY, clickedZ);
        else if (type == 6) OreSpawnMain.MyDungeon.makeMantisHive(world, clickedX, clickedY, clickedZ);
        else if (type == 7) OreSpawnMain.MyDungeon.makeKyuubiDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 8) OreSpawnMain.MyDungeon.makeSmallBeeHive(world, clickedX, clickedY, clickedZ);
        else if (type == 9) OreSpawnMain.MyDungeon.makeShadowDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 10) OreSpawnMain.MyDungeon.makeAlienWTFDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 11) OreSpawnMain.MyDungeon.makeEnderKnightDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 12) OreSpawnMain.MyDungeon.makePlayPool(world, clickedX, clickedY, clickedZ);
        else if (type == 13) OreSpawnMain.MyDungeon.makeWaterDragonLair(world, clickedX, clickedY, clickedZ);
        else if (type == 14) OreSpawnMain.MyDungeon.makeCloudSharkDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 15) OreSpawnMain.MyDungeon.makeLeafMonsterDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 16) OreSpawnMain.MyDungeon.makeMiniDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 17) OreSpawnMain.MyDungeon.makeGoldFishBowl(world, clickedX, clickedY, clickedZ);
        else if (type == 18) OreSpawnMain.MyDungeon.makeEnderReaperGraveyard(world, clickedX, clickedY, clickedZ);
        else if (type == 19) OreSpawnMain.MyDungeon.makeSpitBugLair(world, clickedX, clickedY, clickedZ);
        else if (type == 20) OreSpawnMain.MyDungeon.makeIgloo(world, clickedX, clickedY, clickedZ);
        else if (type == 21) OreSpawnMain.MyDungeon.makeDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 22) OreSpawnMain.RubyDungeon.makeDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 23) OreSpawnMain.BMaze.buildBasiliskMaze(world, clickedX, clickedY, clickedZ);
        else if (type == 24) OreSpawnMain.MyDungeon.makeEnderDragonHospital(world, clickedX, clickedY, clickedZ);
        else if (type == 25) OreSpawnMain.MyDungeon.makeCrystalHauntedHouse(world, clickedX, clickedY, clickedZ);
        else if (type == 26) OreSpawnMain.MyDungeon.makeBouncyCastle(world, clickedX, clickedY, clickedZ);
        else if (type == 27) OreSpawnMain.MyDungeon.makeEnderCastle(world, clickedX, clickedY, clickedZ);
        else if (type == 28) OreSpawnMain.MyDungeon.makeDamselInDistress(world, clickedX, clickedY, clickedZ);
        else if (type == 29) OreSpawnMain.MyDungeon.makeIncaPyramid(world, clickedX, clickedY, clickedZ);
        else if (type == 30) OreSpawnMain.MyDungeon.makeRobotLab(world, clickedX, clickedY, clickedZ);
        else if (type == 31) OreSpawnMain.MyDungeon.makeKingAltar(world, clickedX, clickedY, clickedZ);
        else if (type == 32) OreSpawnMain.MyDungeon.makeLeonNest(world, clickedX, clickedY, clickedZ);
        else if (type == 33) OreSpawnMain.MyDungeon.makeCrystalBattleTower(world, clickedX, clickedY, clickedZ);
        else if (type == 34) OreSpawnMain.MyDungeon.makeCephadromeAltar(world, clickedX, clickedY, clickedZ);
        else if (type == 35) OreSpawnMain.MyDungeon.makeGirlfriendIsland(world, clickedX, clickedY, clickedZ);
        else if (type == 36) OreSpawnMain.MyDungeon.makeGreenhouseDungeon(world, clickedX, clickedY, clickedZ);
        else if (type == 37) OreSpawnMain.MyDungeon.makeMonsterIsland(world, clickedX, clickedY, clickedZ);
        else if (type == 38) OreSpawnMain.MyDungeon.makeNightmareRookery(world, clickedX, clickedY, clickedZ);
        else if (type == 39) OreSpawnMain.MyDungeon.makeStinkyHouse(world, clickedX, clickedY, clickedZ);
        else if (type == 40) OreSpawnMain.MyDungeon.makeRubberDuckyPond(world, clickedX, clickedY, clickedZ);
        else if (type == 41) OreSpawnMain.MyDungeon.makeWhiteHouse(world, clickedX, clickedY, clickedZ);
        else if (type == 42) OreSpawnMain.MyDungeon.makeQueenAltar(world, clickedX, clickedY, clickedZ);
        else if (type == 43) OreSpawnMain.MyDungeon.makeFrogPond(world, clickedX, clickedY + 1, clickedZ);
        else if (type == 44) OreSpawnMain.MyDungeon.makePumpkin(world, clickedX, clickedY + 1, clickedZ);
        else if (type == 45) OreSpawnMain.MyDungeon.makeRoundRotator(world, clickedX, clickedY + 1, clickedZ);
        else if (type == 46) OreSpawnMain.MyDungeon.makeRainbow(world, clickedX, clickedY, clickedZ);
        else if (type == 47) OreSpawnMain.MyDungeon.makeEnormousCastleQ(world, clickedX, clickedY, clickedZ);
        else if (type == 48) OreSpawnMain.MyDungeon.makeSpiderHangout(world, clickedX, clickedY, clickedZ);
        else if (type == 49) OreSpawnMain.MyDungeon.makeRedAntHangout(world, clickedX, clickedY, clickedZ);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(OreSpawnMain.RandomDungeon);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return OreSpawnMain.RandomDungeon;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }
}