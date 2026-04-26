/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package danger.orespawn;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class CrystalFurnaceGUI
extends GuiContainer {
    private static final ResourceLocation furnaceGuiTextures = new net.minecraft.util.ResourceLocation("textures/gui/container/furnace.png");
    private TileEntityCrystalFurnace furnaceInventory;

    public CrystalFurnaceGUI(InventoryPlayer par1InventoryPlayer, TileEntityCrystalFurnace par2TileEntityCrystalFurnace) {
        super((Container)new ContainerCrystalFurnace(par1InventoryPlayer, par2TileEntityCrystalFurnace));
        this.furnaceInventory = par2TileEntityCrystalFurnace;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = this.furnaceInventory.isCustomInventoryName() ? this.furnaceInventory.getInventoryName() : I18n.format((String)this.furnaceInventory.getInventoryName(), (Object[])new Object[0]);
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 0x404040);
        this.fontRenderer.drawString(I18n.format((String)"container.inventory", (Object[])new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        int i1;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if (this.furnaceInventory.isBurning()) {
            i1 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        i1 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
}

