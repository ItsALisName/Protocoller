package net.alis.EXAMPLES;

import net.alis.protocoller.Protocoller;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.event.impl.PacketEventHandler;
import net.alis.protocoller.event.impl.PacketListener;
import net.alis.protocoller.event.synchronous.PacketPlayReceiveEvent;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.packets.game.*;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.phys.MovingObjectPositionBlock;
import net.alis.protocoller.parent.util.Facing;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TEST {

    public TEST(Plugin plugin) {
        ApiUser user = Protocoller.registerUser(plugin);
        user.api().getEventManager().getSynchronousManager().registerListener(user, new PacketListener() {

            @PacketEventHandler
            public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
                if(event.getPacketType() == PacketType.Play.Client.USE_ITEM) {

                    /*PacketPlayInUseItem inUseItem = new PacketPlayInUseItem(event.getData());
                    MovingObjectPositionBlock m = inUseItem.getBlockHitResult();
                    LogsManager.get().getLogger().info("COORDS: " + m.getPosition().getX() + ", " + m.getPosition().getY() + ", " + m.getPosition().getZ());
                    LogsManager.get().getLogger().info("FACING: " + m.getFacing().getName());
                    LogsManager.get().getLogger().info("AXIS: " + m.getFacing().getAxis().getName());
                    LogsManager.get().getLogger().info("HAND: " + inUseItem.getHand().name());
                    LogsManager.get().getLogger().info("MOV. OBJ. TYPE: " + m.getMovingObjectType().name());*/
                }

                // TODO: 05.03.2023 PacketPlayInWindowClick
            }

            /*@Override
            @PacketEventHandler
            public void onPacketLoginSend(PacketLoginSendEvent event) {
                if(event.getPacketType() == PacketType.Login.Server.SET_COMPRESSION) {
                    PacketLoginOutDisconnect d = new PacketLoginOutDisconnect(new ChatComponent("LOH").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "lol"));
                    event.setPacket(d);
                }
            }*/

            /*@Override
            @PacketEventHandler
            public void onPacketLoginReceive(PacketLoginReceiveEvent event) {
                if(event.getPacketType() == PacketType.Login.Client.START) {
                    PacketLoginOutDisconnect d = new PacketLoginOutDisconnect(new ChatComponent("LOH").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "lol"));
                    event.getChannel().writeAndFlush(d.getPacketData().getObject());
                }
            }*/
        });
    }

}