/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.WorldServer
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.biome.BiomeProviderSingle
 *  net.minecraft.world.chunk.net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.storage.net.minecraft.world.storage.WorldInfo
 *  net.minecraftforge.common.DimensionManager
 */
package danger.orespawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class WorldProviderOreSpawn2
extends WorldProvider {
    public String getDimensionName() {
        return "Dimension-Extreme";
    }

    public boolean canRespawnHere() {
        return true;
    }

    public void registerWorldChunkManager() {
        this.biomeProvider = new BiomeProviderSingle(Biome.net.minecraft.init.Biomes.EXTREME_HILLS, 0.01f);
        this.biomeProvider.getBiome(new net.minecraft.util.math.BlockPos(0, 0, 0));//.setTemperatureRainfall(0.8f, 0.01f);
        this.getDimension() = OreSpawnMain.DimensionID2;
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
                        net.minecraftforge.fml.common.FMLCommonHandler.instance().getMinecraftServerInstance().worlds[j].setDayTime(i);
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

    public net.minecraft.world.chunk.IChunkProvider createChunkGenerator() {
        return new ChunkProviderOreSpawn2(this.world, this.world.getSeed(), true);
    }
}

