package net.alis.protocoller.packet.packets.game;

import com.google.common.annotations.VisibleForTesting;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static net.alis.protocoller.plugin.enums.Version.v1_13;

@AddedSince(v1_13)
@VisibleForTesting
public class PacketPlayOutAdvancements implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private boolean clearCurrent;
    private List<MinecraftKey> toEarn;
    private Set<MinecraftKey> toRemove;

    public PacketPlayOutAdvancements(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.toEarn = new ArrayList<>(); this.toRemove = new HashSet<>();
        this.clearCurrent = packetData.readBoolean(0);
        Set<Object> keys = packetData.readSet(0);
        Map<Object, Object> adv = packetData.readMap(0);
        for(Map.Entry<Object, Object> e : adv.entrySet()) {
            this.toEarn.add(new MinecraftKey(e.getKey()));
        }
        for(Object key : keys) this.toRemove.add(new MinecraftKey(key));
    }

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
        this.toEarn = toEarn;
    }

    public Set<MinecraftKey> getToRemove() {
        return toRemove;
    }

    public void setToRemove(@NotNull Set<MinecraftKey> toRemove) {
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
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
