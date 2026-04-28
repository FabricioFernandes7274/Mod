/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.IGuiHandler
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 */
package danger.orespawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IGuiHandler;

public class OreSpawnGUIHandler
implements IGuiHandler {
    public Object getServerGuiElement(int ID, net.minecraft.entity.player.EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        switch (ID) {
            case 0: {
                if (!(tileEntity instanceof TileEntityCrystalFurnace)) break;
                return new ContainerCrystalFurnace(player.inventory, (TileEntityCrystalFurnace)tileEntity);
            }
            case 1: {
                return new ContainerCrystalWorkbench(player.inventory, world, x, y, z);
            }
        }
        return null;
    }

    public Object getClientGuiElement(int ID, net.minecraft.entity.player.EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        switch (ID) {
            case 0: {
                if (!(tileEntity instanceof TileEntityCrystalFurnace)) break;
                return new CrystalFurnaceGUI(player.inventory, (TileEntityCrystalFurnace)tileEntity);
            }
            case 1: {
                return new CrystalWorkbenchGUI(player.inventory, world, x, y, z);
            }
        }
        return null;
    }
}

