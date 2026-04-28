/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package danger.orespawn;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GirlfriendOverlayGui
extends Gui {
    private Minecraft mc;
    private static final ResourceLocation texture = new net.minecraft.util.ResourceLocation("orespawn", "girlfriendgui.png");

    public GirlfriendOverlayGui(Minecraft mc) {
        this.mc = mc;
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        float myf;
        Object e;
        EntityTameable gf;
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        int u = 0;
        int v = 0;
        String outstring = null;
        int color = 0xFF3434;
        FontRenderer fr = this.mc.fontRenderer;
        int barWidth = 182;
        int barHeight = 5;
        float gfHealth = 0.0f;
        Entity entity = null;
        net.minecraft.client.entity.EntityPlayerSP player = null;
        if (this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
            return;
        }
        player = this.mc.player;
        if (player == null) {
            return;
        }
        OreSpawnMain.current_dimension = player.world.provider.getDimension();
        OreSpawnMain.FastGraphicsLeaves = this.mc.gameSettings.fancyGraphics ? 0 : 1;
        if (OreSpawnMain.GuiOverlayEnable == 0) {
            return;
        }
        entity = this.mc.pointedEntity;
        if (entity == null) {
            entity = OreSpawnMain.getPointedAtEntity((World)this.mc.world, (net.minecraft.entity.player.EntityPlayer)player, 16.0);
            if (entity == null) {
                return;
            }
            if (!(entity instanceof net.minecraft.entity.EntityLivingBase)) {
                return;
            }
        }
        if (entity instanceof Girlfriend) {
            gf = null;
            gf = (Girlfriend)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.getPassengers().isEmpty() ? null : .getPassengers().get(0) != 0) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "Girlfriend";
            }
            gfHealth = (float)gf.getGirlfriendHealth() / gf.getMaxHealth();
        }
        if (entity instanceof Boyfriend) {
            gf = null;
            gf = (Boyfriend)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.getPassengers().isEmpty() ? null : .getPassengers().get(0) != 0) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "Boyfriend";
            }
            gfHealth = (float)gf.getBoyfriendHealth() / gf.getMaxHealth();
        }
        if (entity instanceof ThePrince) {
            gf = null;
            gf = (ThePrince)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "The Toddler Prince";
            }
            gfHealth = gf.getHealth() / gf.getMaxHealth();
        }
        if (entity instanceof ThePrincess) {
            gf = null;
            gf = (ThePrincess)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "The Toddler Princess";
            }
            gfHealth = gf.getHealth() / gf.getMaxHealth();
        }
        if (entity instanceof ThePrinceTeen) {
            gf = null;
            gf = (ThePrinceTeen)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "The Young Prince";
            }
            if (gf.getActivity() != 0) {
                return;
            }
            gfHealth = gf.getHealth() / gf.getMaxHealth();
        }
        if (entity instanceof ThePrinceAdult) {
            gf = null;
            gf = (ThePrinceAdult)entity;
            if (!gf.getGameProfile((net.minecraft.entity.EntityLivingBase)player)) {
                return;
            }
            if (gf.hasCustomNameTag()) {
                outstring = gf.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "The Young Adult Prince";
            }
            if (gf.getActivity() != 0) {
                return;
            }
            gfHealth = gf.getHealth() / gf.getMaxHealth();
        }
        if (entity instanceof Dragon) {
            Dragon df = null;
            df = (Dragon)entity;
            if (df.hasCustomNameTag()) {
                outstring = df.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "Dragon";
            }
            if (df.getActivity() != 0) {
                return;
            }
            gfHealth = (float)df.getDragonHealth() / df.getMaxHealth();
        }
        if (entity instanceof EmperorScorpion) {
            e = (EmperorScorpion)entity;
            outstring = "Emperor Scorpion";
            gfHealth = (float)e.getEmperorScorpionHealth() / e.getMaxHealth();
        }
        if (entity instanceof Basilisk) {
            e = (Basilisk)entity;
            outstring = "Basilisk";
            gfHealth = (float)e.getBasiliskHealth() / e.getMaxHealth();
        }
        if (entity instanceof Mothra) {
            e = (Mothra)entity;
            outstring = "Mothra!";
            gfHealth = (float)e.getMothraHealth() / e.getMaxHealth();
        }
        if (entity instanceof Spyro) {
            e = (Spyro)entity;
            if (e.hasCustomNameTag()) {
                outstring = e.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "Baby Dragon";
            }
            gfHealth = (float)e.getSpyroHealth() / e.getMaxHealth();
        }
        if (entity instanceof WormLarge) {
            e = (WormLarge)entity;
            if (!e.noClip) {
                outstring = "Worm";
                gfHealth = e.getHealth() / e.getMaxHealth();
            }
        }
        if (entity instanceof Alien) {
            e = (Alien)entity;
            outstring = "Alien!";
            gfHealth = (float)e.getAlienHealth() / e.getMaxHealth();
        }
        if (entity instanceof WaterDragon) {
            e = (WaterDragon)entity;
            if (e.hasCustomNameTag()) {
                outstring = e.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "WaterDragon";
            }
            gfHealth = (float)e.getWaterDragonHealth() / e.getMaxHealth();
        }
        if (entity instanceof Kraken) {
            e = (Kraken)entity;
            outstring = "Kraken";
            gfHealth = (float)e.getKrakenHealth() / e.getMaxHealth();
        }
        if (entity instanceof Cephadrome) {
            e = (Cephadrome)entity;
            outstring = "Cephadrome";
            gfHealth = (float)e.getCephadromeHealth() / e.getMaxHealth();
            if (e.getActivity() != 0) {
                return;
            }
        }
        if (entity instanceof TrooperBug) {
            e = (TrooperBug)entity;
            outstring = "Jumpy Bug";
            gfHealth = (float)e.getTrooperBugHealth() / e.getMaxHealth();
        }
        if (entity instanceof SpitBug) {
            e = (SpitBug)entity;
            outstring = "Spit Bug";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof PitchBlack) {
            e = (PitchBlack)entity;
            outstring = "Nightmare";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Alosaurus) {
            e = (Alosaurus)entity;
            outstring = "Alosaurus";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Nastysaurus) {
            e = (Nastysaurus)entity;
            outstring = "Nastysaurus";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof TRex) {
            e = (TRex)entity;
            outstring = "T. Rex";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Kyuubi) {
            e = (Kyuubi)entity;
            outstring = "Kyuubi";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Robot2) {
            e = (Robot2)entity;
            outstring = "Robo-Pounder";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Robot4) {
            e = (Robot4)entity;
            outstring = "Robo-Warrior";
            gfHealth = (float)e.getRobot4Health() / e.getMaxHealth();
        }
        if (entity instanceof Triffid) {
            e = (Triffid)entity;
            outstring = "Triffid";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Godzilla) {
            e = (Godzilla)entity;
            outstring = "Mobzilla";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Vortex) {
            e = (Vortex)entity;
            outstring = "Vortex";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Irukandji) {
            e = (Irukandji)entity;
            outstring = "Irukandji";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Mantis) {
            e = (Mantis)entity;
            outstring = "Mantis";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof HerculesBeetle) {
            e = (HerculesBeetle)entity;
            outstring = "Hercules Beetle";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof TheKing) {
            e = (TheKing)entity;
            outstring = "The King";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof TheQueen) {
            e = (TheQueen)entity;
            outstring = "The Queen";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof SeaViper) {
            e = (SeaViper)entity;
            outstring = "Sea Viper";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof SeaMonster) {
            e = (SeaMonster)entity;
            outstring = "Sea Monster";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Molenoid) {
            e = (Molenoid)entity;
            outstring = "Molenoid";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof CaterKiller) {
            e = (CaterKiller)entity;
            outstring = "CaterKiller";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Leon) {
            e = (Leon)entity;
            if (e.hasCustomNameTag()) {
                outstring = e.getCustomNameTag();
            }
            if (outstring == null || outstring.equals("")) {
                outstring = "Leonopteryx";
            }
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Hammerhead) {
            e = (Hammerhead)entity;
            outstring = "Hammerhead";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof BandP) {
            e = (BandP)entity;
            outstring = e.getWhat() == 0 ? "Banker" : "Politician";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof SpiderRobot) {
            e = (SpiderRobot)entity;
            outstring = "Giant Robot Spider";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof GiantRobot) {
            e = (GiantRobot)entity;
            outstring = "Jeffery";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof AntRobot) {
            e = (AntRobot)entity;
            outstring = "Giant Robot Red Ant";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (entity instanceof Crab && (myf = (e = (Crab)entity).getCrabScale()) > 0.75f) {
            outstring = "Very Large Crab";
            gfHealth = e.getHealth() / e.getMaxHealth();
        }
        if (outstring == null) {
            return;
        }
        ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int width = res.getScaledWidth();
        int barWidthFilled = (int)(gfHealth * (float)(barWidth + 1));
        int x = width / 2 - barWidth / 2;
        int y = 25;
        if (player.isInsideOfMaterial(Material.WATER) || player.getTotalArmorValue() > 0) {
            y -= 10;
        }
        fr.drawStringWithShadow(outstring, width / 2 - fr.getStringWidth(outstring) / 2, y - 10, color);
        this.mc.textureManager.bindTexture(texture);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(x, y, u, v, barWidth, barHeight);
        if (barWidthFilled > 0) {
            this.drawTexturedModalRect(x, y, u, v + barHeight, barWidthFilled, barHeight);
        }
    }
}

