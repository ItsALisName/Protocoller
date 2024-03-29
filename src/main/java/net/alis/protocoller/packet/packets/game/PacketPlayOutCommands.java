package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PacketPlayOutCommands implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private final List<String> nodes;

    private @Nullable Object rootCommandNode;
    private @Nullable List<Object> rootCommands;

    private boolean legacyPacket = IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_19);

    public PacketPlayOutCommands(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.nodes = new ArrayList<>();
        if (!legacyPacket) {
            this.rootCommands = (List<Object>) packetData.readList(0);
            for(Object o : this.rootCommands) {
                Object clsA = ClassAccessor.get().getOutCommandClassA().cast(Reflect.readField(o, 0, ClassAccessor.get().getOutCommandInterfaceE(), false));
                this.nodes.add(Reflect.readField(clsA, 0, String.class, false));
            }
        } else {
            this.rootCommandNode = packetData.readObject(0, ClassAccessor.get().getRootCommandNodeClass());
            Map<Object, Object> nods = Reflect.readSuperclassField(this.rootCommandNode, 0, Map.class, false);
            for(Map.Entry<Object, Object> entry : nods.entrySet()) this.nodes.add((String) entry.getKey());
        }



    }

    public Collection<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    public void removeCommand(String node) {
        if(!legacyPacket) {
            for(int i = this.rootCommands.size() - 1; i >= 0; i--) {
                Object clsA = ClassAccessor.get().getOutCommandClassA().cast(Reflect.readField(this.rootCommands.get(i), 0, ClassAccessor.get().getOutCommandInterfaceE(), false));
                if(((String) Reflect.readField(clsA, 0, String.class, false)).equalsIgnoreCase(node)) {
                    this.rootCommands.remove(i);
                }
            }
            this.packetData.writeList(0, this.rootCommands);
        } else {
            Reflect.callMethod(this.rootCommandNode, Reflect.getSuperclassMethod(this.rootCommandNode.getClass(), "removeCommand", new Class[]{String.class}), false, node);
        }
        this.nodes.remove(node);
    }

    public void removeCommandIfContains(String s) {
        for(int i = this.nodes.size() - 1; i >= 0; i--) {
            if(this.nodes.get(i).contains(s)) {
                if(!legacyPacket) {
                    this.rootCommands.remove(i);
                } else {
                    Reflect.callMethod(this.rootCommandNode, Reflect.getSuperclassMethod(this.rootCommandNode.getClass(), "removeCommand", new Class[]{String.class}), false, this.nodes.get(i));
                }
                this.nodes.remove(i);
            }
        }
        if(!legacyPacket) this.packetData.writeList(0, this.rootCommands);
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.COMMANDS;
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
