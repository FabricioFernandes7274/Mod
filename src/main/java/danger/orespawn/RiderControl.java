/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$ClientTickEvent
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
 */
package danger.orespawn;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class RiderControl {
    private final RiderControlMessage rcm = new RiderControlMessage();
    private final SimpleNetworkWrapper network;
    private int keystate = 0;

    public RiderControl(SimpleNetworkWrapper network) {
        this.network = network;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent evt) {
        int newkeystate = 0;
        if (KeyHandler.KEY_FLY_UP.getIsKeyPressed()) {
            newkeystate = 1;
        }
        if (this.keystate != newkeystate) {
            this.rcm.keystate = newkeystate;
            this.network.sendToServer((IMessage)this.rcm);
            this.keystate = newkeystate;
        }
    }
}

