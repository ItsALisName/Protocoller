package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;

public class PacketPlayInArmAnimation implements Packet {

    private final PacketDataSerializer packetData;
    private Hand hand;

    public PacketPlayInArmAnimation(PacketDataSerializer packetData) {
        this.packetData = packetData;
        this.hand = Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getHandEnum()).ordinal());
    }

    public PacketPlayInArmAnimation(Hand hand) {
        PacketCreator converter = PacketCreator.get(getPacketType());
        switch (converter.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(hand.original(), 0)};
                this.packetData = new PacketDataSerializer(converter.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.create(null, hand.original()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ARM_ANIMATION;
    }

    @Override
    public PacketDataSerializer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
    }

    public enum Hand {
        MAIN_HAND(0), OFF_HAND(1);

        private final int id;

        Hand(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Hand getById(int id) {
            for(Hand s : Hand.values())
                if(s.id == id) return s;
            return null;
        }

        public Enum<?> original() {
            return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getHandEnum(), this.id);
        }
    }

}
