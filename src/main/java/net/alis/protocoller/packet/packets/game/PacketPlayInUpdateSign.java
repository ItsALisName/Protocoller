package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.core.BlockPosition;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public class PacketPlayInUpdateSign implements PlayInPacket {

    private final PacketDataContainer packetData;
    private BlockPosition position;
    private String[] lines;

    private final boolean legacyPacket = GlobalProvider.get().getServer().getVersion().lessThan(Version.v1_9);

    public PacketPlayInUpdateSign(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.position = packetData.readBlockPosition(0);
        this.lines = new String[4];
        if(legacyPacket) {
            Object[] components = packetData.readObject(0, Array.newInstance(ClassAccessor.get().getIChatBaseComponentClass(), 1).getClass());
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
        PacketBuilder creator = PacketBuilder.get(getPacketType());
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
                    this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                } else {
                    params = new IndexedParam[]{
                            new IndexedParam<>(position.createOriginal(), 0),
                            new IndexedParam<>(lines, 0)
                    };
                    this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                }
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, position.createOriginal(), lines[0], lines[1], lines[2], lines[3]));
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
