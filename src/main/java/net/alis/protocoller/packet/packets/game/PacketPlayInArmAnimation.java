package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.Hand;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInArmAnimation implements PlayInPacket {

    private final PacketDataContainer packetData;
    private Hand hand;

    public PacketPlayInArmAnimation(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.hand = Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getHandEnum()).ordinal());
    }

    public PacketPlayInArmAnimation(Hand hand) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketBuilder converter = PacketBuilder.get(getPacketType());
        switch (converter.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {new IndexedParam<>(hand.original(), 0)};
                this.packetData = new PacketDataSerializer(converter.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, hand.original()));
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

    public void setHand(@NotNull Hand hand) {
        this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.ARM_ANIMATION;
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
