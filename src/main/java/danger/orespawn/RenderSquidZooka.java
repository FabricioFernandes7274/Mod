package danger.orespawn;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class RenderSquidZooka extends TileEntityItemStackRenderer {
    protected ModelSquidZooka modelSquidZooka = new ModelSquidZooka();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "SquidZookatexture.png");

    @Override
    public void renderByItem(ItemStack itemStack) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
        this.modelSquidZooka.render();
        GL11.glPopMatrix();
    }
}
