/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.RenderingRegistry
 *  cpw.mods.fml.common.FMLCommonHandler
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelCow
 *  net.minecraft.client.model.ModelSpider
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderArrow
 *  net.minecraft.client.renderer.entity.RenderFish
 *  net.minecraft.item.Item
 *  net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType
 *  net.minecraftforge.client.MinecraftForgeClient
 *  net.minecraftforge.common.MinecraftForge
 */
package danger.orespawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.Render;

import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.item.Item;
import net.minecraftforge.client.net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxyOreSpawn
extends CommonProxyOreSpawn {
    @Override
    public void registerRenderThings() {
        MinecraftForge.EVENT_BUS.register((Object)new GirlfriendOverlayGui(Minecraft.getMinecraft()));
        RenderingRegistry.registerEntityRenderingHandler(Girlfriend.class, new RenderGirlfriend(new ModelBiped(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Boyfriend.class, new RenderBoyfriend(new ModelBiped(), 0.55f));
        RenderingRegistry.registerEntityRenderingHandler(RedCow.class, new RenderEnchantedCow(new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(GoldCow.class, new RenderEnchantedCow(new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(EnchantedCow.class, new RenderEnchantedCow(new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(CrystalCow.class, new RenderEnchantedCow(new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(Shoes.class, new RenderShoe());
        RenderingRegistry.registerEntityRenderingHandler(SunspotUrchin.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(WaterBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(InkSack.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(LaserBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(IceBall.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(Acid.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(DeadIrukandji.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(BerthaHit.class, new RenderItemUrchin());
        RenderingRegistry.registerEntityRenderingHandler(EntityCage.class, new RenderCage());
        RenderingRegistry.registerEntityRenderingHandler(UltimateFishHook.class, new RenderFish());
        RenderingRegistry.registerEntityRenderingHandler(UltimateArrow.class, //new RenderArrow());
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownRock.class, new RenderThrownRock());
        RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, new RenderButterfly(new ModelButterfly(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Firefly.class, new RenderFirefly(new ModelFirefly(2.5f), 0.2f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(EntityLunaMoth.class, new RenderButterfly(new ModelButterfly(0.75f), 0.4f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(EntityMosquito.class, new RenderMosquito(new ModelMosquito(), 0.3f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Ghost.class, new RenderGhost(new ModelGhost(), 0.0f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(GhostSkelly.class, new RenderGhostSkelly(new ModelGhostSkelly(), 0.0f, 1.05f));
        RenderingRegistry.registerEntityRenderingHandler(Mothra.class, new RenderButterfly(new ModelButterfly(0.2f), 0.75f, 10.0f));
        RenderingRegistry.registerEntityRenderingHandler(EntityAnt.class, new RenderAnt(new ModelAnt(), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityRedAnt.class, new RenderAnt(new ModelAnt(), 0.15f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(EntityRainbowAnt.class, new RenderAnt(new ModelAnt(), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityUnstableAnt.class, new RenderAnt(new ModelAnt(), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(Alosaurus.class, new RenderAlosaurus(new ModelAlosaurus(0.22f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(TRex.class, new RenderTRex(new ModelTRex(0.2f), 1.0f, 1.2f));
        RenderingRegistry.registerEntityRenderingHandler(Tshirt.class, new RenderTshirt(new ModelTshirt(0.22f), 1.0f, 0.33f));
        RenderingRegistry.registerEntityRenderingHandler(Cryolophosaurus.class, new RenderCryolophosaurus(new ModelCryolophosaurus(0.75f), 0.75f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Basilisk.class, new RenderBasilisk(new ModelBasilisk(0.3f), 0.5f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Camarasaurus.class, new RenderCamarasaurus(new ModelCamarasaurus(0.65f), 0.65f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(Hydrolisc.class, new RenderHydrolisc(new ModelHydrolisc(0.65f), 0.65f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(VelocityRaptor.class, new RenderVelocityRaptor(new ModelVelocityRaptor(1.25f), 0.55f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Dragonfly.class, new RenderDragonfly(new ModelDragonfly(2.0f), 0.3f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Bee.class, new RenderBee(new ModelBee(2.0f), 0.9f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(EmperorScorpion.class, new RenderEmperorScorpion(new ModelEmperorScorpion(0.22f), 0.95f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Spyro.class, new RenderSpyro(new ModelSpyro(0.65f), 0.65f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Baryonyx.class, new RenderBaryonyx(new ModelBaryonyx(0.25f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(GammaMetroid.class, new RenderGammaMetroid(new ModelGammaMetroid(0.45f), 0.75f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Cockateil.class, new RenderCockateil(new ModelCockateil(1.0f), 0.3f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(RubyBird.class, new RenderCockateil(new ModelCockateil(1.0f), 0.3f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Kyuubi.class, new RenderKyuubi(new ModelKyuubi(0.5f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Scorpion.class, new RenderScorpion(new ModelScorpion(0.62f), 0.35f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(CaveFisher.class, new RenderCaveFisher(new ModelCaveFisher(0.62f), 0.35f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Alien.class, new RenderAlien(new ModelAlien(0.22f), 0.35f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(WaterDragon.class, new RenderWaterDragon(new ModelWaterDragon(0.5f), 0.85f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(AttackSquid.class, new RenderAttackSquid(new ModelAttackSquid(1.0f), 0.25f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Elevator.class, new RenderElevator());
        RenderingRegistry.registerEntityRenderingHandler(Robot1.class, new RenderRobot1(new ModelRobot1(2.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot2.class, new RenderRobot2(new ModelRobot2(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot3.class, new RenderRobot3(new ModelRobot3(1.0f), 1.0f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Robot4.class, new RenderRobot4(new ModelRobot4(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot5.class, new RenderRobot5(new ModelRobot5(1.0f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Kraken.class, new RenderKraken(new ModelKraken(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Lizard.class, new RenderLizard(new ModelLizard(0.65f), 0.75f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cephadrome.class, new RenderCephadrome(new ModelCephadrome(0.55f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Dragon.class, new RenderDragon(new ModelDragon(0.65f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Chipmunk.class, new RenderChipmunk(new ModelChipmunk(1.0f), 0.15f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Gazelle.class, new RenderGazelle(new ModelGazelle(0.65f), 0.45f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Ostrich.class, new RenderOstrich(new ModelOstrich(0.65f), 0.55f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(TrooperBug.class, new RenderTrooperBug(new ModelTrooperBug(0.22f), 0.95f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(SpitBug.class, new RenderSpitBug(new ModelSpitBug(0.55f), 0.55f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(StinkBug.class, new RenderStinkBug(new ModelStinkBug(0.75f), 0.35f, 0.85f));
        RenderingRegistry.registerEntityRenderingHandler(Island.class, new RenderIsland(new ModelIsland(1.0f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(IslandToo.class, new RenderIslandToo(new ModelIsland(1.0f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CreepingHorror.class, new RenderCreepingHorror(new ModelCreepingHorror(), 0.45f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(TerribleTerror.class, new RenderTerribleTerror(new ModelTerribleTerror(), 0.45f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(CliffRacer.class, new RenderCliffRacer(new ModelCliffRacer(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Triffid.class, new RenderTriffid(new ModelTriffid(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(PitchBlack.class, new RenderPitchBlack(new ModelPitchBlack(0.65f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(LurkingTerror.class, new RenderLurkingTerror(new ModelLurkingTerror(), 0.45f, 0.85f));
        RenderingRegistry.registerEntityRenderingHandler(Godzilla.class, new RenderGodzilla(new ModelGodzilla(0.2f), 1.0f, 2.0f));
        RenderingRegistry.registerEntityRenderingHandler(GodzillaHead.class, new RenderGodzillaHead(null, 0.0f, 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(KingHead.class, new RenderKingHead(null, 0.0f, 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(QueenHead.class, new RenderQueenHead(null, 0.0f, 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(WormSmall.class, new RenderWormSmall(new ModelWormSmall(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(WormMedium.class, new RenderWormMedium(new ModelWormMedium(), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(WormLarge.class, new RenderWormLarge(new ModelWormLarge(), 0.9f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cassowary.class, new RenderCassowary(new ModelCassowary(0.55f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(GoldFish.class, new RenderGoldFish(new ModelGoldFish(0.7f), 0.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CloudShark.class, new RenderCloudShark(new ModelCloudShark(1.0f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(LeafMonster.class, new RenderLeafMonster(new ModelLeafMonster(), 0.65f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EnderKnight.class, new RenderEnderKnight(new ModelEnderKnight(0.21f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EnderReaper.class, new RenderEnderReaper(new ModelEnderReaper(0.23f), 0.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Beaver.class, new RenderBeaver(new ModelBeaver(0.5f), 0.15f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Termite.class, new RenderAnt(new ModelAnt(), 0.15f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(Fairy.class, new RenderFairy(new ModelFairy(1.5f), 0.1f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(Peacock.class, new RenderPeacock(new ModelPeacock(0.75f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Rotator.class, new RenderRotator(new ModelRotator(0.25f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Vortex.class, new RenderVortex(new ModelVortex(0.25f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(DungeonBeast.class, new RenderDungeonBeast(new ModelDungeonBeast(0.62f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Rat.class, new RenderRat(new ModelRat(1.0f), 0.1f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Flounder.class, new RenderFlounder(new ModelFlounder(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Whale.class, new RenderWhale(new ModelWhale(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Irukandji.class, new RenderIrukandji(new ModelIrukandji(1.0f), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(Skate.class, new RenderSkate(new ModelSkate(1.0f), 0.1f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Urchin.class, new RenderUrchin(new ModelUrchin(1.0f), 0.35f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Mantis.class, new RenderMantis(new ModelMantis(2.0f), 0.9f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(HerculesBeetle.class, new RenderHerculesBeetle(new ModelHerculesBeetle(1.0f), 0.99f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(Stinky.class, new RenderStinky(new ModelStinky(0.65f), 0.75f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Coin.class, new RenderCoin(new ModelCoin(0.22f), 0.75f, 0.125f));
        RenderingRegistry.registerEntityRenderingHandler(TheKing.class, new RenderTheKing(new ModelTheKing(0.65f), 1.9f, 2.1f));
        RenderingRegistry.registerEntityRenderingHandler(TheQueen.class, new RenderTheQueen(new ModelTheQueen(0.65f), 1.9f, 2.0f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrince.class, new RenderThePrince(new ModelThePrince(0.65f), 0.75f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Molenoid.class, new RenderMolenoid(new ModelMolenoid(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SeaMonster.class, new RenderSeaMonster(new ModelSeaMonster(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SeaViper.class, new RenderSeaViper(new ModelSeaViper(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EasterBunny.class, new RenderEasterBunny(new ModelEasterBunny(0.55f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CaterKiller.class, new RenderCaterKiller(new ModelCaterKiller(0.22f), 1.0f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Leon.class, new RenderLeon(new ModelLeon(0.22f), 1.0f, 1.75f));
        RenderingRegistry.registerEntityRenderingHandler(Hammerhead.class, new RenderHammerhead(new ModelHammerhead(0.33f), 1.0f, 2.5f));
        RenderingRegistry.registerEntityRenderingHandler(RubberDucky.class, new RenderRubberDucky(new ModelRubberDucky(1.0f), 0.15f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrinceTeen.class, new RenderThePrinceTeen(new ModelThePrinceTeen(0.65f), 1.0f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(BandP.class, new RenderBandP(new ModelBandP(0.4f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(RockBase.class, new RenderRockBase(new ModelRockBase(1.0f), 0.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(PurplePower.class, new RenderPurplePower(new ModelPurplePower(1.0f), 0.3f, 2.75f));
        RenderingRegistry.registerEntityRenderingHandler(Brutalfly.class, new RenderBrutalfly(new ModelBrutalfly(0.2f), 0.75f, 9.0f));
        RenderingRegistry.registerEntityRenderingHandler(Nastysaurus.class, new RenderNastysaurus(new ModelNastysaurus(0.65f), 1.0f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Pointysaurus.class, new RenderPointysaurus(new ModelPointysaurus(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cricket.class, new RenderCricket(new ModelCricket(2.5f), 0.15f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrincess.class, new RenderThePrincess(new ModelThePrincess(0.65f), 0.7f, 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(Frog.class, new RenderFrog(new ModelFrog(1.0f), 0.35f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrinceAdult.class, new RenderThePrinceAdult(new ModelThePrinceAdult(0.65f), 1.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SpiderRobot.class, new RenderSpiderRobot(new ModelSpiderRobot(1.0f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SpiderDriver.class, new RenderSpiderDriver(new ModelSpider(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(GiantRobot.class, new RenderGiantRobot(new ModelGiantRobot(0.25f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(AntRobot.class, new RenderAntRobot(new ModelAntRobot(1.0f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Crab.class, new RenderCrab(new ModelCrab(1.0f), 0.99f, 1.0f));
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyBertha, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderBertha());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MySlice, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderSlice());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyRoyal, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderRoyal());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MySquidZooka, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderSquidZooka());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyHammy, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderHammy());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyBattleAxe, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderBattleAxe());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyChainsaw, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderChainsaw());
        MinecraftForgeClient.registerItemRenderer((Item)OreSpawnMain.MyQueenBattleAxe, (net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType)new RenderQueenBattleAxe());
    }

    @Override
    public void registerSoundThings() {
        MinecraftForge.EVENT_BUS.register((Object)new OreSpawnSounds());
    }

    @Override
    public void registerKeyboardInput() {
        KeyHandler k = new KeyHandler();
        FMLCommonHandler.instance().bus().register((Object)k);
        OreSpawnMain.MyKeyhandler = k;
    }

    @Override
    public void registerNetworkStuff() {
        super.registerNetworkStuff();
        FMLCommonHandler.instance().bus().register((Object)new RiderControl(this.getNetwork()));
    }

    @Override
    public int setArmorPrefix(String string) {
        return RenderingRegistry.addNewArmourRendererPrefix((String)string);
    }
}

