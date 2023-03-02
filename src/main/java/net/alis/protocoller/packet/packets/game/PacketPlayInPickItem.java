package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.util.annotations.AddedSince;

import static net.alis.protocoller.bukkit.enums.Version.v1_13;

@AddedSince(v1_13)
public class PacketPlayInPickItem implements Packet {

    private final PacketDataSerializer packetData;
    private int slot;

    public PacketPlayInPickItem(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.slot = packetData.readInt(0);
    }

    public PacketPlayInPickItem(int slot) {
        PacketCreator creator = PacketCreator.get(getPacketType());
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                this.packetData = new PacketDataSerializer(creator.create(new IndexedParam[]{new IndexedParam<>(slot, 0)}));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, slot));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.packetData.writeInt(0, slot);
        this.slot = slot;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.PICK_ITEM;
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
