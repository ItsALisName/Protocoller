package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PacketPlayOutPosition implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private final PacketBuilder converter = PacketBuilder.get(getPacketType());
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Set<PlayerTeleportFlags> flags;
    private int teleportId;
    private boolean shouldDismount;

    public PacketPlayOutPosition(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.x = packetData.readDouble(0);
        this.y = packetData.readDouble(1);
        this.z = packetData.readDouble(2);
        this.yaw = packetData.readFloat(0);
        this.pitch = packetData.readFloat(1);
        this.flags = new HashSet<>();
        Set<?> s = packetData.readObject(0, Set.class);
        for(Object o : s) {
            this.flags.add(PlayerTeleportFlags.getById(((Enum<?>)o).ordinal()));
        }
        switch (converter.getPacketLevel().getLevel()) {
            case 1: {
                this.teleportId = 0; this.shouldDismount = false; break;
            }
            case 2: {
                this.teleportId = packetData.readInt(0); this.shouldDismount = false; break;
            }
            case 3: {
                this.teleportId = packetData.readInt(0); this.shouldDismount = packetData.readBoolean(0); break;
            }
        }
    }

    public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, @NotNull Set<PlayerTeleportFlags> flags, int teleportId, boolean shouldDismount) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        ArrayList<Object> newFlags = new ArrayList<>();
        for(PlayerTeleportFlags teleportFlags : flags) {
            newFlags.add(teleportFlags.original());
        }
        switch (converter.getPacketLevel().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, x, y, z, yaw, pitch, newFlags));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, x, y, z, yaw, pitch, newFlags, teleportId));
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(converter.buildPacket(null, x, y, z, yaw, pitch, newFlags, teleportId, shouldDismount));
                break;
            }
            default: {
                this.packetData = null; break;
            }
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportId = teleportId;
        this.shouldDismount = shouldDismount;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.packetData.writeDouble(0, x);
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.packetData.writeDouble(1, x);
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.packetData.writeDouble(2, x);
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.packetData.writeFloat(0, yaw);
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.packetData.writeFloat(1, yaw);
        this.pitch = pitch;
    }

    public Set<PlayerTeleportFlags> getFlags() {
        return flags;
    }

    public void setFlags(Set<PlayerTeleportFlags> flags) {
        ArrayList<Object> newFlags = new ArrayList<>();
        for(PlayerTeleportFlags teleportFlags : this.flags) {
            newFlags.add(teleportFlags.original());
        }
        this.packetData.writeObject(0, newFlags);
        this.flags = flags;
    }

    public int getTeleportId() {
        return teleportId;
    }

    public void setTeleportId(int teleportId) {
        if(converter.getPacketLevel().getLevel() >= 2) {
            this.packetData.writeInt(0, teleportId);
        }
        this.teleportId = teleportId;
    }

    public boolean isShouldDismount() {
        return shouldDismount;
    }

    public void setShouldDismount(boolean shouldDismount) {
        if(converter.getPacketLevel().getLevel() >= 3) {
            this.packetData.writeBoolean(0, shouldDismount);
        }
        this.shouldDismount = shouldDismount;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.POSITION;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum PlayerTeleportFlags {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        private final int shift;

        PlayerTeleportFlags(int shift) {
            this.shift = shift;
        }

        @Contract(pure = true)
        public static @Nullable PlayerTeleportFlags getById(int id) {
            for(PlayerTeleportFlags flag : values()) {
                if(flag.shift == id) return flag;
            }
            return null;
        }

        public int getShift() {
            return shift;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getPlayerTeleportFlagsEnum(), this.ordinal());
        }
    }
}
