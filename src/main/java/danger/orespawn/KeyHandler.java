/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.ClientRegistry
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.net.minecraftforge.fml.common.gameevent.InputEvent$Keynet.minecraftforge.fml.common.gameevent.InputEvent
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 */
package danger.orespawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {
    private final Minecraft mc = Minecraft.getMinecraft();
    public static final String KEY_CATEGORY = "key.categories.orespawn";
    public static final KeyBinding KEY_FLY_UP = new KeyBinding("OreSpawn UP/FAST", 56, "key.categories.orespawn");

    public KeyHandler() {
        ClientRegistry.registerKeyBinding((KeyBinding)KEY_FLY_UP);
    }

    @SubscribeEvent
    public void onKeyInput(net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent event) {
    }
}

