package danger.orespawn;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxyOreSpawn extends CommonProxyOreSpawn {
    
    @Override
    public void registerRenderThings() {
        MinecraftForge.EVENT_BUS.register(new GirlfriendOverlayGui(Minecraft.getMinecraft()));
        
        // Na 1.12.2, usamos lambdas (IRenderFactory) para passar o RenderManager (m) para cada classe
        RenderingRegistry.registerEntityRenderingHandler(Girlfriend.class, m -> new RenderGirlfriend(m, new ModelBiped(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Boyfriend.class, m -> new RenderBoyfriend(m, new ModelBiped(), 0.55f));
        RenderingRegistry.registerEntityRenderingHandler(RedCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(GoldCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(EnchantedCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(CrystalCow.class, m -> new RenderEnchantedCow(m, new ModelCow(), 0.7f));
        
        RenderingRegistry.registerEntityRenderingHandler(Shoes.class, m -> new RenderShoe(m));
        RenderingRegistry.registerEntityRenderingHandler(SunspotUrchin.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(WaterBall.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(InkSack.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(LaserBall.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(IceBall.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(Acid.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(DeadIrukandji.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(BerthaHit.class, m -> new RenderItemUrchin(m));
        RenderingRegistry.registerEntityRenderingHandler(EntityCage.class, m -> new RenderCage(m));
        
        // Para a flecha final, deves usar a tua classe RenderUltimateArrow que recebe o RenderManager
        RenderingRegistry.registerEntityRenderingHandler(UltimateArrow.class, m -> new RenderUltimateArrow(m));
        RenderingRegistry.registerEntityRenderingHandler(UltimateFishHook.class, m -> new RenderUltimateFishHook(m)); // Necessita de classe própria ou RenderFish nativo ajustado
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownRock.class, m -> new RenderThrownRock(m));
        
        RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, m -> new RenderButterfly(m, new ModelButterfly(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Firefly.class, m -> new RenderFirefly(m, new ModelFirefly(2.5f), 0.2f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(EntityLunaMoth.class, m -> new RenderButterfly(m, new ModelButterfly(0.75f), 0.4f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(EntityMosquito.class, m -> new RenderMosquito(m, new ModelMosquito(), 0.3f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Ghost.class, m -> new RenderGhost(m, new ModelGhost(), 0.0f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(GhostSkelly.class, m -> new RenderGhostSkelly(m, new ModelGhostSkelly(), 0.0f, 1.05f));
        RenderingRegistry.registerEntityRenderingHandler(Mothra.class, m -> new RenderButterfly(m, new ModelButterfly(0.2f), 0.75f, 10.0f));
        
        RenderingRegistry.registerEntityRenderingHandler(EntityAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityRedAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.15f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(EntityRainbowAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(EntityUnstableAnt.class, m -> new RenderAnt(m, new ModelAnt(), 0.1f, 0.25f));
        
        RenderingRegistry.registerEntityRenderingHandler(Alosaurus.class, m -> new RenderAlosaurus(m, new ModelAlosaurus(0.22f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(TRex.class, m -> new RenderTRex(m, new ModelTRex(0.2f), 1.0f, 1.2f));
        RenderingRegistry.registerEntityRenderingHandler(Tshirt.class, m -> new RenderTshirt(m, new ModelTshirt(0.22f), 1.0f, 0.33f));
        RenderingRegistry.registerEntityRenderingHandler(Cryolophosaurus.class, m -> new RenderCryolophosaurus(m, new ModelCryolophosaurus(0.75f), 0.75f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Basilisk.class, m -> new RenderBasilisk(m, new ModelBasilisk(0.3f), 0.5f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Camarasaurus.class, m -> new RenderCamarasaurus(m, new ModelCamarasaurus(0.65f), 0.65f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(Hydrolisc.class, m -> new RenderHydrolisc(m, new ModelHydrolisc(0.65f), 0.65f, 0.65f));
        RenderingRegistry.registerEntityRenderingHandler(VelocityRaptor.class, m -> new RenderVelocityRaptor(m, new ModelVelocityRaptor(1.25f), 0.55f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Dragonfly.class, m -> new RenderDragonfly(m, new ModelDragonfly(2.0f), 0.3f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Bee.class, m -> new RenderBee(m, new ModelBee(2.0f), 0.9f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(EmperorScorpion.class, m -> new RenderEmperorScorpion(m, new ModelEmperorScorpion(0.22f), 0.95f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Spyro.class, m -> new RenderSpyro(m, new ModelSpyro(0.65f), 0.65f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Baryonyx.class, m -> new RenderBaryonyx(m, new ModelBaryonyx(0.25f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(GammaMetroid.class, m -> new RenderGammaMetroid(m, new ModelGammaMetroid(0.45f), 0.75f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Cockateil.class, m -> new RenderCockateil(m, new ModelCockateil(1.0f), 0.3f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(RubyBird.class, m -> new RenderCockateil(m, new ModelCockateil(1.0f), 0.3f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Kyuubi.class, m -> new RenderKyuubi(m, new ModelKyuubi(0.5f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Scorpion.class, m -> new RenderScorpion(m, new ModelScorpion(0.62f), 0.35f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(CaveFisher.class, m -> new RenderCaveFisher(m, new ModelCaveFisher(0.62f), 0.35f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Alien.class, m -> new RenderAlien(m, new ModelAlien(0.22f), 0.35f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(WaterDragon.class, m -> new RenderWaterDragon(m, new ModelWaterDragon(0.5f), 0.85f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(AttackSquid.class, m -> new RenderAttackSquid(m, new ModelAttackSquid(1.0f), 0.25f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Elevator.class, m -> new RenderElevator(m));
        
        RenderingRegistry.registerEntityRenderingHandler(Robot1.class, m -> new RenderRobot1(m, new ModelRobot1(2.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot2.class, m -> new RenderRobot2(m, new ModelRobot2(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot3.class, m -> new RenderRobot3(m, new ModelRobot3(1.0f), 1.0f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(Robot4.class, m -> new RenderRobot4(m, new ModelRobot4(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Robot5.class, m -> new RenderRobot5(m, new ModelRobot5(1.0f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Kraken.class, m -> new RenderKraken(m, new ModelKraken(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Lizard.class, m -> new RenderLizard(m, new ModelLizard(0.65f), 0.75f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cephadrome.class, m -> new RenderCephadrome(m, new ModelCephadrome(0.55f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Dragon.class, m -> new RenderDragon(m, new ModelDragon(0.65f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Chipmunk.class, m -> new RenderChipmunk(m, new ModelChipmunk(1.0f), 0.15f, 0.9f));
        RenderingRegistry.registerEntityRenderingHandler(Gazelle.class, m -> new RenderGazelle(m, new ModelGazelle(0.65f), 0.45f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Ostrich.class, m -> new RenderOstrich(m, new ModelOstrich(0.65f), 0.55f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(TrooperBug.class, m -> new RenderTrooperBug(m, new ModelTrooperBug(0.22f), 0.95f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(SpitBug.class, m -> new RenderSpitBug(m, new ModelSpitBug(0.55f), 0.55f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(StinkBug.class, m -> new RenderStinkBug(m, new ModelStinkBug(0.75f), 0.35f, 0.85f));
        RenderingRegistry.registerEntityRenderingHandler(Island.class, m -> new RenderIsland(m, new ModelIsland(1.0f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(IslandToo.class, m -> new RenderIslandToo(m, new ModelIsland(1.0f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CreepingHorror.class, m -> new RenderCreepingHorror(m, new ModelCreepingHorror(), 0.45f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(TerribleTerror.class, m -> new RenderTerribleTerror(m, new ModelTerribleTerror(), 0.45f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(CliffRacer.class, m -> new RenderCliffRacer(m, new ModelCliffRacer(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Triffid.class, m -> new RenderTriffid(m, new ModelTriffid(1.0f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(PitchBlack.class, m -> new RenderPitchBlack(m, new ModelPitchBlack(0.65f), 1.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(LurkingTerror.class, m -> new RenderLurkingTerror(m, new ModelLurkingTerror(), 0.45f, 0.85f));
        
        RenderingRegistry.registerEntityRenderingHandler(Godzilla.class, m -> new RenderGodzilla(m, new ModelGodzilla(0.2f), 1.0f, 2.0f));
        RenderingRegistry.registerEntityRenderingHandler(GodzillaHead.class, m -> new RenderGodzillaHead(m, null, 0.0f, 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(KingHead.class, m -> new RenderKingHead(m, null, 0.0f, 0.0f));
        RenderingRegistry.registerEntityRenderingHandler(QueenHead.class, m -> new RenderQueenHead(m, null, 0.0f, 0.0f));
        
        RenderingRegistry.registerEntityRenderingHandler(WormSmall.class, m -> new RenderWormSmall(m, new ModelWormSmall(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(WormMedium.class, m -> new RenderWormMedium(m, new ModelWormMedium(), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(WormLarge.class, m -> new RenderWormLarge(m, new ModelWormLarge(), 0.9f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cassowary.class, m -> new RenderCassowary(m, new ModelCassowary(0.55f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(GoldFish.class, m -> new RenderGoldFish(m, new ModelGoldFish(0.7f), 0.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CloudShark.class, m -> new RenderCloudShark(m, new ModelCloudShark(1.0f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(LeafMonster.class, m -> new RenderLeafMonster(m, new ModelLeafMonster(), 0.65f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EnderKnight.class, m -> new RenderEnderKnight(m, new ModelEnderKnight(0.21f), 0.3f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EnderReaper.class, m -> new RenderEnderReaper(m, new ModelEnderReaper(0.23f), 0.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Beaver.class, m -> new RenderBeaver(m, new ModelBeaver(0.5f), 0.15f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Termite.class, m -> new RenderAnt(m, new ModelAnt(), 0.15f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(Fairy.class, m -> new RenderFairy(m, new ModelFairy(1.5f), 0.1f, 0.35f));
        RenderingRegistry.registerEntityRenderingHandler(Peacock.class, m -> new RenderPeacock(m, new ModelPeacock(0.75f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Rotator.class, m -> new RenderRotator(m, new ModelRotator(0.25f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Vortex.class, m -> new RenderVortex(m, new ModelVortex(0.25f), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(DungeonBeast.class, m -> new RenderDungeonBeast(m, new ModelDungeonBeast(0.62f), 0.25f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Rat.class, m -> new RenderRat(m, new ModelRat(1.0f), 0.1f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Flounder.class, m -> new RenderFlounder(m, new ModelFlounder(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Whale.class, m -> new RenderWhale(m, new ModelWhale(), 0.1f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Irukandji.class, m -> new RenderIrukandji(m, new ModelIrukandji(1.0f), 0.1f, 0.25f));
        RenderingRegistry.registerEntityRenderingHandler(Skate.class, m -> new RenderSkate(m, new ModelSkate(1.0f), 0.1f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Urchin.class, m -> new RenderUrchin(m, new ModelUrchin(1.0f), 0.35f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Mantis.class, m -> new RenderMantis(m, new ModelMantis(2.0f), 0.9f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(HerculesBeetle.class, m -> new RenderHerculesBeetle(m, new ModelHerculesBeetle(1.0f), 0.99f, 1.1f));
        RenderingRegistry.registerEntityRenderingHandler(Stinky.class, m -> new RenderStinky(m, new ModelStinky(0.65f), 0.75f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Coin.class, m -> new RenderCoin(m, new ModelCoin(0.22f), 0.75f, 0.125f));
        RenderingRegistry.registerEntityRenderingHandler(TheKing.class, m -> new RenderTheKing(m, new ModelTheKing(0.65f), 1.9f, 2.1f));
        RenderingRegistry.registerEntityRenderingHandler(TheQueen.class, m -> new RenderTheQueen(m, new ModelTheQueen(0.65f), 1.9f, 2.0f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrince.class, m -> new RenderThePrince(m, new ModelThePrince(0.65f), 0.75f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(Molenoid.class, m -> new RenderMolenoid(m, new ModelMolenoid(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SeaMonster.class, m -> new RenderSeaMonster(m, new ModelSeaMonster(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SeaViper.class, m -> new RenderSeaViper(m, new ModelSeaViper(0.5f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(EasterBunny.class, m -> new RenderEasterBunny(m, new ModelEasterBunny(0.55f), 0.5f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(CaterKiller.class, m -> new RenderCaterKiller(m, new ModelCaterKiller(0.22f), 1.0f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(Leon.class, m -> new RenderLeon(m, new ModelLeon(0.22f), 1.0f, 1.75f));
        RenderingRegistry.registerEntityRenderingHandler(Hammerhead.class, m -> new RenderHammerhead(m, new ModelHammerhead(0.33f), 1.0f, 2.5f));
        RenderingRegistry.registerEntityRenderingHandler(RubberDucky.class, m -> new RenderRubberDucky(m, new ModelRubberDucky(1.0f), 0.15f, 0.75f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrinceTeen.class, m -> new RenderThePrinceTeen(m, new ModelThePrinceTeen(0.65f), 1.0f, 1.25f));
        RenderingRegistry.registerEntityRenderingHandler(BandP.class, m -> new RenderBandP(m, new ModelBandP(0.4f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(RockBase.class, m -> new RenderRockBase(m, new ModelRockBase(1.0f), 0.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(PurplePower.class, m -> new RenderPurplePower(m, new ModelPurplePower(1.0f), 0.3f, 2.75f));
        RenderingRegistry.registerEntityRenderingHandler(Brutalfly.class, m -> new RenderBrutalfly(m, new ModelBrutalfly(0.2f), 0.75f, 9.0f));
        RenderingRegistry.registerEntityRenderingHandler(Nastysaurus.class, m -> new RenderNastysaurus(m, new ModelNastysaurus(0.65f), 1.0f, 1.5f));
        RenderingRegistry.registerEntityRenderingHandler(Pointysaurus.class, m -> new RenderPointysaurus(m, new ModelPointysaurus(1.0f), 1.0f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Cricket.class, m -> new RenderCricket(m, new ModelCricket(2.5f), 0.15f, 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrincess.class, m -> new RenderThePrincess(m, new ModelThePrincess(0.65f), 0.7f, 0.7f));
        RenderingRegistry.registerEntityRenderingHandler(Frog.class, m -> new RenderFrog(m, new ModelFrog(1.0f), 0.35f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(ThePrinceAdult.class, m -> new RenderThePrinceAdult(m, new ModelThePrinceAdult(0.65f), 1.2f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SpiderRobot.class, m -> new RenderSpiderRobot(m, new ModelSpiderRobot(1.0f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(SpiderDriver.class, m -> new RenderSpiderDriver(m, new ModelSpider(), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler(GiantRobot.class, m -> new RenderGiantRobot(m, new ModelGiantRobot(0.25f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(AntRobot.class, m -> new RenderAntRobot(m, new ModelAntRobot(1.0f), 0.99f, 1.0f));
        RenderingRegistry.registerEntityRenderingHandler(Crab.class, m -> new RenderCrab(m, new ModelCrab(1.0f), 0.99f, 1.0f));
        
        // --- Registo de Modelos de Item na 1.12.2 ---
        // Na 1.12.2 o recomendado é fazer isto no ModelRegistryEvent, 
        // mas sendo chamado no ClientProxy.preInit ou init, o ModelLoader funcionará perfeitamente e evitará falhas no render.
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyBertha, 0, new ModelResourceLocation("orespawn:bertha", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MySlice, 0, new ModelResourceLocation("orespawn:slice", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyRoyal, 0, new ModelResourceLocation("orespawn:royal", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MySquidZooka, 0, new ModelResourceLocation("orespawn:squidzooka", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyHammy, 0, new ModelResourceLocation("orespawn:hammy", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyBattleAxe, 0, new ModelResourceLocation("orespawn:battleaxe", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyChainsaw, 0, new ModelResourceLocation("orespawn:chainsaw", "inventory"));
        ModelLoader.setCustomModelResourceLocation(OreSpawnMain.MyQueenBattleAxe, 0, new ModelResourceLocation("orespawn:queenbattleaxe", "inventory"));
    }

    @Override
    public void registerSoundThings() {
        // Regista a classe de sons no Bus universal do Forge
        MinecraftForge.EVENT_BUS.register(new OreSpawnSounds());
    }

    @Override
    public void registerKeyboardInput() {
        KeyHandler k = new KeyHandler();
        // FMLCommonHandler.instance().bus() não existe mais, usamos EVENT_BUS
        MinecraftForge.EVENT_BUS.register(k);
        OreSpawnMain.MyKeyhandler = k;
    }

    @Override
    public void registerNetworkStuff() {
        super.registerNetworkStuff();
        // Registo da classe de controlo de montada
        MinecraftForge.EVENT_BUS.register(new RiderControl(this.getNetwork()));
    }

    @Override
    public int setArmorPrefix(String string) {
        // Na 1.12.2 este método foi deprecado porque as armaduras chamam a textura através de ItemArmor#getArmorTexture.
        // Podes deixá-lo retornar 0 para satisfazer possíveis overrides sem partir o código antigo.
        return 0;
    }
}