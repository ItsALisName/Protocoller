package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.PacketUtils;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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

    private boolean legacyPacket = GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_19);

    public PacketPlayOutCommands(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.nodes = new ArrayList<>();
        if (!legacyPacket) {
            this.rootCommands = packetData.readList(0);
            for(Object o : this.rootCommands) {
                Object clsA = ClassesContainer.INSTANCE.getOutCommandClassA().cast(Reflection.readField(o, 0, ClassesContainer.INSTANCE.getOutCommandInterfaceE()));
                this.nodes.add(Reflection.readField(clsA, 0, String.class));
            }
        } else {
            this.rootCommandNode = packetData.readObject(0, ClassesContainer.INSTANCE.getRootCommandNodeClass());
            Map<Object, Object> nods = Reflection.readSuperclassField(this.rootCommandNode, 0, Map.class);
            for(Map.Entry<Object, Object> entry : nods.entrySet()) this.nodes.add((String) entry.getKey());
        }
    }

    public Collection<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    public void removeCommand(String node) {
        if(!legacyPacket) {
            for(int i = this.rootCommands.size() - 1; i >= 0; i--) {
                Object clsA = ClassesContainer.INSTANCE.getOutCommandClassA().cast(Reflection.readField(this.rootCommands.get(i), 0, ClassesContainer.INSTANCE.getOutCommandInterfaceE()));
                if(((String)Reflection.readField(clsA, 0, String.class)).equalsIgnoreCase(node)) {
                    this.rootCommands.remove(i);
                }
            }
            this.packetData.writeList(0, this.rootCommands);
        } else {
            Reflection.callMethod(this.rootCommandNode, Reflection.getSuperclassMethod(this.rootCommandNode.getClass(), "removeCommand", new Class[]{String.class}), node);
        }
        this.nodes.remove(node);
    }

    public void removeCommandIfContains(String s) {
        for(int i = this.nodes.size() - 1; i >= 0; i--) {
            if(this.nodes.get(i).contains(s)) {
                if(!legacyPacket) {
                    this.rootCommands.remove(i);
                } else {
                    Reflection.callMethod(this.rootCommandNode, Reflection.getSuperclassMethod(this.rootCommandNode.getClass(), "removeCommand", new Class[]{String.class}), this.nodes.get(i));
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
