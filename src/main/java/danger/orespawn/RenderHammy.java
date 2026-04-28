package danger.orespawn;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderHammy extends TileEntityItemStackRenderer {
    protected ModelHammy modelHammy = new ModelHammy();
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Hammytexture.png");

    @Override
    public void renderByItem(ItemStack itemStack) {
        GL11.glPushMatrix();
        GL11.glScalef(0.25f, 0.25f, 0.25f);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(texture);
        this.modelHammy.render();
        GL11.glPopMatrix();
    }
}
