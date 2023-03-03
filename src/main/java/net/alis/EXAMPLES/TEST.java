package net.alis.EXAMPLES;

import net.alis.protocoller.Protocoller;
import net.alis.protocoller.ApiUser;
import net.alis.protocoller.event.impl.PacketListener;
import org.bukkit.plugin.Plugin;

public class TEST {

    public TEST(Plugin plugin) {
        ApiUser user = Protocoller.registerUser(plugin);
        user.api().getEventManager().getSynchronousManager().registerListener(user, new PacketListener() {

            /*@PacketEventHandler
            public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
                if(event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    PacketPlayInBlockDig dig = new PacketPlayInBlockDig(event.getData());
                    BlockPosition pos = dig.getBlockPosition();
                    Bukkit.getConsoleSender().sendMessage("CORDS: X: " + pos.getX() + "; Y: " + pos.getY() + "; Z: " + pos.getZ());
                    Bukkit.getConsoleSender().sendMessage("DIG TYPE: " + dig.getDigType().name());
                    Bukkit.getConsoleSender().sendMessage("FACING: " + dig.getFacing().getName());
                }
            }*/

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