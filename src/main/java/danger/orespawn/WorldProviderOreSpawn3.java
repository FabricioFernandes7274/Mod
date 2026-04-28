/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.WorldServer
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.biome.BiomeProvider
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.storage.net.minecraft.world.storage.WorldInfo
 *  net.minecraftforge.common.Biome
 *  net.minecraftforge.common.DimensionManager
 */
package danger.orespawn;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class WorldProviderOreSpawn3
extends WorldProvider {
    private BiomeGenUtopianPlains MyPlains = (BiomeGenUtopianPlains)new BiomeGenUtopianPlains(OreSpawnMain.BiomeVillageID).setColor(353825).setBiomeName("Villages")//.setTemperatureRainfall(0.7f, 0.5f);

    public String getDimensionName() {
        return "Dimension-VillageMania";
    }

    public boolean canRespawnHere() {
        return true;
    }

    public void registerBiomeProvider() {
        this.MyPlains.setVillageCreatures();
        this.this.biomeProvider = new net.minecraft.world.biome.BiomeProviderSingle(net.minecraft.init.Biomes.PLAINS);
        this.biomeProvider.getBiome(new net.minecraft.util.math.BlockPos(0, 0, 0));//.setTemperatureRainfall(0.7f, 0.5f);
        this.setDimension(OreSpawnMain.DimensionID3);
        Biome.addVillageBiome((Biome)this.MyPlains, (boolean)true);
    }

    public void setWorldTime(long time) {
        WorldServer ws = net.minecraftforge.common.DimensionManager.getWorld((int)this.getDimension());
        if (ws != null) {
            net.minecraft.world.storage.WorldInfo w = ws.getWorldInfo();
            if (w != null) {
                if (time % 24000L > 12000L && ws.areAllPlayersAsleep()) {
                    long i = time + 24000L;
                    i -= i % 24000L;
                    for (int j = 0; j < net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().worlds.length; ++j) {
                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().worlds[j].setWorldTime(i);
                    }
                } else {
                    super.setWorldTime(time);
                }
            } else {
                super.setWorldTime(time);
            }
        } else {
            super.setWorldTime(time);
        }
    }

    public net.minecraft.world.gen.IChunkGenerator createChunkGenerator() {
        return new ChunkProviderOreSpawn3(this.getEntityWorld(), this.getEntityWorld().getSeed(), true);
    }
}

