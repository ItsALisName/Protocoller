package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.core.BlockPosition;
import net.alis.protocoller.parent.network.chat.ChatSerializer;

import java.lang.reflect.Array;

public class PacketPlayInUpdateSign implements Packet {

    private final PacketDataSerializer packetData;
    private BlockPosition position;
    private String[] lines;

    private final boolean legacyPacket = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9);

    public PacketPlayInUpdateSign(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.lines = new String[4];
        if(legacyPacket) {
            Object[] components = packetData.readObject(0, Array.newInstance(ClassesContainer.INSTANCE.getIChatBaseComponentClass(), 1).getClass());
            if(components.length > 0){
                for (int i = 0; i < 4; i++) {
                    if(components[i] != null){
                        this.lines[i] = ChatSerializer.fromComponent(components[i]);
                    }
                }
            }
        } else {
            String[] pLines = packetData.readStringArray(0);
            if(pLines.length > 0){
                System.arraycopy(pLines, 0, this.lines, 0, 4);
            }
        }
    }

    public PacketPlayInUpdateSign(BlockPosition position, String[] lines) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(legacyPacket) {
                    Object[] icbc = new Object[4];
                    for(int i = 0; i < 4; i++) {
                        if(lines[i] != null) {
                            icbc[i] = ChatSerializer.fromJSONOrString(lines[i], false, true);
                        } else {
                            icbc[i] = ChatSerializer.fromJSONOrString("error[us-0]", false, false);
                        }
                    }
                    params = new IndexedParam[]{
                            new IndexedParam<>(position.createOriginal(), 0),
                            new IndexedParam<>(icbc, 0)
                    };
                    this.packetData = new PacketDataSerializer(creator.create(params));
                } else {
                    params = new IndexedParam[]{
                            new IndexedParam<>(position.createOriginal(), 0),
                            new IndexedParam<>(lines, 0)
                    };
                    this.packetData = new PacketDataSerializer(creator.create(params));
                }
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, position.createOriginal(), lines[0], lines[1], lines[2], lines[3]));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.position = position;
        this.lines = lines;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.packetData.writeBlockPosition(0, position);
        this.position = position;
    }

    public String[] getLines() {
        return lines;
    }

    public void setLines(String[] lines) {
        this.packetData.writeStringArray(0, lines);
        this.lines = lines;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.UPDATE_SIGN;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
