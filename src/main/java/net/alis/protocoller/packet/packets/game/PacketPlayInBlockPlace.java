package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.Hand;
import org.jetbrains.annotations.Nullable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInBlockPlace implements PlayInPacket {

    private final PacketDataContainer packetData;
    private Hand hand;

    private @Nullable int sequence;
    private @Nullable ItemStack itemStack;

    private final boolean legacyPacket = GlobalProvider.get().getServer().getVersion().lessThan(Version.v1_9);
    private final boolean modernPacket = GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19);

    public PacketPlayInBlockPlace(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(legacyPacket) {
            this.hand = Hand.MAIN_HAND;
            this.sequence = 0;
            this.itemStack = packetData.readMinecraftItemStack(0);
        } else {
            this.hand = Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getHandEnum()).ordinal());
            this.sequence = 0;
            if(modernPacket) {
                this.sequence = packetData.readInt(0);
            }
        }
    }

    private PacketPlayInBlockPlace(Hand hand, int sequence, @Nullable ItemStack itemStack) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        switch (builder.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, MinecraftReflection.getMinecraftItemStack(itemStack)));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, hand.original()));
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, hand.original(), sequence));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.hand = hand;
        this.sequence = sequence;
        this.itemStack = itemStack;
    }

    /**
     * For version less than 1.9
     */
    public PacketPlayInBlockPlace(@Nullable ItemStack itemStack) {
        this(null, -1, itemStack);
    }

    /**
     * For version bigger than 1.9 and lower than 1.19
     */
    public PacketPlayInBlockPlace(Hand hand) {
        this(hand, -1, null);
    }

    /**
     * For versions from 1.19
     */
    public PacketPlayInBlockPlace(Hand hand, int sequence) {
        this(hand, sequence, null);
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        if(modernPacket) this.packetData.writeInt(0, sequence);
        this.sequence = sequence;
    }

    @Nullable
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(@Nullable ItemStack itemStack) {
        if(legacyPacket) {
            this.packetData.writeMinecraftItemStack(0, itemStack);
        }
        this.itemStack = itemStack;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.BLOCK_PLACE;
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
