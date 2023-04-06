package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.samples.boss.BarColor;
import net.alis.protocoller.samples.boss.BarStyle;
import net.alis.protocoller.util.annotations.AddedSince;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static net.alis.protocoller.plugin.enums.Version.v1_9;

@AddedSince(v1_9)
public class PacketPlayOutBoss implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private Action action;
    private UUID uuid;
    private @Nullable BarColor color;
    private @Nullable BarStyle style;

    public PacketPlayOutBoss(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(GlobalProvider.instance().getServer().isLegacy()) {
            this.action = Action.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBossActionEnum()).ordinal());
            this.color = BarColor.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBarColorEnum()).ordinal());
            this.style = BarStyle.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getBarStyleEnum()).ordinal());
        } else {
            this.action = Action.fromModernPacket(packetData.readObject(0, ClassesContainer.get().getBossActionInterface()));
        }
        this.uuid = packetData.readUUID(0);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        if(GlobalProvider.instance().getServer().isLegacy()) {
            this.packetData.writeEnumConstant(0, action.original());
        }
        this.action = action;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.packetData.writeUUID(0, uuid);
        this.uuid = uuid;
    }

    @Nullable
    public BarColor getColor() {
        return color;
    }

    public void setColor(@Nullable BarColor color) {
        if(GlobalProvider.instance().getServer().isLegacy()) this.packetData.writeEnumConstant(0, color.original());
        this.color = color;
    }

    @Nullable
    public BarStyle getStyle() {
        return style;
    }

    public void setStyle(@Nullable BarStyle style) {
        if(GlobalProvider.instance().getServer().isLegacy()) this.packetData.writeEnumConstant(0, style.original());
        this.style = style;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.BOSS;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum Action {
        ADD(0),
        REMOVE(1),
        UPDATE_PCT(2),
        UPDATE_NAME(3),
        UPDATE_STYLE(4),
        UPDATE_PROPERTIES(5);

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Contract(pure = true)
        public static @Nullable Action getById(int id) {
            for(Action action : values()) {
                if(action.id == id) return action;
            }
            return null;
        }

        public static Action fromModernPacket(Object action) {
            return Action.getById(((Enum<?>) BaseReflection.callMethod(action, BaseReflection.getMethod(action.getClass(), 0, ClassesContainer.get().getBossActionEnum()), false)).ordinal());
        }

        public @NotNull Enum<?> original() {
            return BaseReflection.readEnumValue((Class<? extends Enum<?>>) ClassesContainer.get().getBossActionEnum(), this.id);
        }

    }

}
