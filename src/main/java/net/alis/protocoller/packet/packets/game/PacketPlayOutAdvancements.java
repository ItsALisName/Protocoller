package net.alis.protocoller.packet.packets.game;

import com.google.common.annotations.VisibleForTesting;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.managers.LogsManager;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.resources.MinecraftKey;
import net.alis.protocoller.util.ObjectAccessor;
import net.alis.protocoller.util.annotations.AddedSince;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;

import java.util.*;

import static net.alis.protocoller.bukkit.enums.Version.v1_13;

@AddedSince(v1_13)
@VisibleForTesting
public class PacketPlayOutAdvancements implements Packet {

    private final PacketDataContainer packetData;
    private boolean clearCurrent;
    private List<MinecraftKey> toEarn;
    private Set<MinecraftKey> toRemove;

    public PacketPlayOutAdvancements(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.toEarn = new ArrayList<>(); this.toRemove = new HashSet<>();
        this.clearCurrent = packetData.readBoolean(0);
        Set<Object> keys = packetData.readSet(0);
        Map<Object, Object> adv = packetData.readMap(0);
        for(Map.Entry<Object, Object> e : adv.entrySet()) {
            LogsManager.get().warning("HERE-cYcle-1");
            this.toEarn.add(new MinecraftKey(e.getKey()));
        }
        for(Object key : keys) this.toRemove.add(new MinecraftKey(key));
        LogsManager.get().warning("HERE7");
    }

    /*public PacketPlayOutAdvancements(boolean clearCurrent, List<Advancement> toEarn, Set<MinecraftKey> toRemove, Map<MinecraftKey, AdvancementProgress> toSetProgress) {
        Collection<Object> adv = new ArrayList<>();
        Set<Object> toremove = new HashSet<>();
        Map<Object, Object> toset = new HashMap<>();
        for(Advancement advancement : toEarn) adv.add(AlMinecraftReflection.getMinecraftAdvancement(advancement));
        for(MinecraftKey key : toRemove) toremove.add(key.createOriginal());
        for(Map.Entry<MinecraftKey, AdvancementProgress> e : toSetProgress.entrySet()) {
            toset.put(e.getKey().createOriginal(), AlMinecraftReflection.getMinecraftAdvancementProgress(e.getValue()));
        }
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, clearCurrent, adv, toremove, toset));
        this.clearCurrent = clearCurrent;
        this.toEarn = toEarn;
        this.toRemove = toRemove;
        this.toSetProgress = toSetProgress;
    }*/

    public boolean isClearCurrent() {
        return clearCurrent;
    }

    public void setClearCurrent(boolean clearCurrent) {
        this.packetData.writeBoolean(0, clearCurrent);
        this.clearCurrent = clearCurrent;
    }

    public List<MinecraftKey> getToEarn() {
        return toEarn;
    }

    public void setToEarn(List<MinecraftKey> toEarn) {
        //Collection<Object> adv = new ArrayList<>();
        //for(Advancement advancement : toEarn) adv.add(AlMinecraftReflection.getMinecraftAdvancement(advancement));
        //this.packetData.writeCollection(0, adv);
        this.toEarn = toEarn;
    }

    public Set<MinecraftKey> getToRemove() {
        return toRemove;
    }

    public void setToRemove(Set<MinecraftKey> toRemove) {
        Set<Object> toremove = new HashSet<>();
        for(MinecraftKey key : toRemove) toremove.add(key.createOriginal());
        this.packetData.writeSet(0, toremove);
        this.toRemove = toRemove;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.ADVANCEMENTS;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }
}
