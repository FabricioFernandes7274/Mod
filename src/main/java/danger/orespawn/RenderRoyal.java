package danger.orespawn;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderRoyal extends TileEntityItemStackRenderer {
    protected ModelRoyal modelRoyal = new ModelRoyal();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Royaltexture.png");

    @Override
    public void renderByItem(ItemStack itemStack) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
        this.modelRoyal.render();
        GL11.glPopMatrix();
    }
}
