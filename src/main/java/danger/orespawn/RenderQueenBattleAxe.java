package danger.orespawn;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RenderQueenBattleAxe extends TileEntityItemStackRenderer {
    protected ModelQueenBattleAxe modelQueenBattleAxe = new ModelQueenBattleAxe();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "QueenBattleAxetexture.png");

    @Override
    public void renderByItem(ItemStack itemStack) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
        this.modelQueenBattleAxe.render();
        GL11.glPopMatrix();
    }
}
