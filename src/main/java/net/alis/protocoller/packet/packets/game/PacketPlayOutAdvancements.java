package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;

import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.advancements.Advancement;
import net.alis.protocoller.samples.advancements.AdvancementProgress;
import net.alis.protocoller.samples.resources.MinecraftKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PacketPlayOutAdvancements implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private boolean reset;
    private List<MinecraftKey> added;
    private Set<MinecraftKey> removed;
    private Map<MinecraftKey, AdvancementProgress> progress;

    public PacketPlayOutAdvancements(PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.reset = packetData.readBoolean(0);
        this.added = new ArrayList<>();
        this.removed = new HashSet<>();
        this.progress = new HashMap<>();
        Map<Object, Object> add = packetData.readMap(0);
        Set<Object> rem = packetData.readSet(0);
        Map<Object, Object> prog = packetData.readMap(1);
        if(add != null && add.size() > 0) {
            for(Map.Entry<Object, Object> e : add.entrySet()) {
                this.added.add(new MinecraftKey(e.getKey()));
            }
        }
        if(rem != null && rem.size() > 0) {
            for(Object mkey : rem){
                this.removed.add(new MinecraftKey(mkey));
            }
        }
        if(prog != null && prog.size() > 0) {
            for(Map.Entry<Object, Object> e : prog.entrySet()) {
                this.progress.put(new MinecraftKey(e.getKey()), new AdvancementProgress(e.getValue()));
            }
        }
    }

    public PacketPlayOutAdvancements(boolean reset, Collection<Advancement> toAdd, Set<MinecraftKey> toRemove, Map<MinecraftKey, AdvancementProgress> toSetProgress) {
        this.added = new ArrayList<>();
        Collection<Object> adv = new ArrayList<>();
        for(Advancement a : toAdd) {
            adv.add(a.createOriginal());
            if(a.getId() != null) this.added.add(a.getId());
        }
        Set<Object> rem = new HashSet<>();
        for(MinecraftKey k : toRemove) rem.add(k.createOriginal());
        Map<Object, Object> prog = new HashMap<>();
        for(Map.Entry<MinecraftKey, AdvancementProgress> e : toSetProgress.entrySet()) {
            prog.put(e.getKey().createOriginal(), e.getValue().createOriginal());
        }
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, rem, adv, rem, prog));
        this.reset = reset;
        this.removed = toRemove;
        this.progress = toSetProgress;

    }

    public List<MinecraftKey> getAdded() {
        return added;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.packetData.writeBoolean(0, reset);
        this.reset = reset;
    }

    public Set<MinecraftKey> getRemoved() {
        return removed;
    }

    public void setRemoved(@NotNull Set<MinecraftKey> removed) {
        Set<Object> r = new HashSet<>();
        for(MinecraftKey k : removed) r.add(k.createOriginal());
        this.packetData.writeSet(0, r);
        this.removed = removed;
    }

    public Map<MinecraftKey, AdvancementProgress> getProgress() {
        return progress;
    }

    public void setProgress(@NotNull Map<MinecraftKey, AdvancementProgress> progress) {
        Map<Object, Object> prog = new HashMap<>();
        for(Map.Entry<MinecraftKey, AdvancementProgress> e : progress.entrySet()) {
            prog.put(e.getKey().createOriginal(), e.getValue().createOriginal());
        }
        this.progress = progress;
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
