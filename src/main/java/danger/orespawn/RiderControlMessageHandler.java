/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.simpleimpl.IMessage
 *  cpw.mods.fml.common.network.simpleimpl.IMessageHandler
 *  cpw.mods.fml.common.network.simpleimpl.net.minecraftforge.fml.common.network.simpleimpl.MessageContext
 *  cpw.mods.fml.relauncher.Side
 *  io.netty.channel.ChannelHandler$Sharable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package danger.orespawn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

@ChannelHandler.Sharable
public class RiderControlMessageHandler
implements IMessageHandler<RiderControlMessage, IMessage> {
    private static final Logger L = LogManager.getLogger();

    public IMessage onMessage(RiderControlMessage message, net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            return null;
        }
        OreSpawnMain.flyup_keystate = message.keystate;
        return null;
    }
}

