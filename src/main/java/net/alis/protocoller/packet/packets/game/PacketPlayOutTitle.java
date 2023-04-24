package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayOutPacket;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.samples.TitleData;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutTitle implements PlayOutPacket {

    private final PacketDataContainer packetData;

    private Action action;
    private ChatComponent text;
    private int fadeInTicks;
    private int stayTicks;
    private int fadeOutTicks;

    public PacketPlayOutTitle(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), false);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.action = Action.getByName(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) Action.clazz()).name());
        this.text = packetData.readIChatBaseComponent(0);
        this.fadeInTicks = packetData.readInt(0);
        this.stayTicks = packetData.readInt(1);
        this.fadeOutTicks = packetData.readInt(2);
    }

    public PacketPlayOutTitle(TitleData title, Action action) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), false);
        if(action == Action.TITLE) {
            this.text = title.getTitle();
            this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, action.original(), title.getTitle().asIChatBaseComponent(), -1, -1, -1));
        } else if(action == Action.SUBTITLE) {
            this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, action.original(), title.getSubtitle().asIChatBaseComponent(), -1, -1, -1));
            this.text = title.getSubtitle();
        } else if(action == Action.TIMES) {
            this.text = null;
            this.fadeInTicks = title.getFadeInTicks();
            this.fadeOutTicks = title.getFadeOutTicks();
            this.stayTicks = title.getStayTicks();
            this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, action.original(), null, title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks()));
        } else {
            this.text = null;
            this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, action.original(), null, -1, -1, -1));
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.packetData.writeEnumConstant(0, action.original());
        this.action = action;
    }

    public ChatComponent getText() {
        return text;
    }

    public void setText(ChatComponent text) {
        this.packetData.writeIChatBaseComponent(0, text);
        this.text = text;
    }

    public int getFadeInTicks() {
        return fadeInTicks;
    }

    public void setFadeInTicks(int fadeInTicks) {
        this.packetData.writeInt(0, fadeInTicks);
        this.fadeInTicks = fadeInTicks;
    }

    public int getStayTicks() {
        return stayTicks;
    }

    public void setStayTicks(int stayTicks) {
        this.packetData.writeInt(1, stayTicks);
        this.stayTicks = stayTicks;
    }

    public int getFadeOutTicks() {
        return fadeOutTicks;
    }

    public void setFadeOutTicks(int fadeOutTicks) {
        this.packetData.writeInt(2, fadeOutTicks);
        this.fadeOutTicks = fadeOutTicks;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.TITLE;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum Action {

        TITLE, SUBTITLE, TIMES, CLEAR, RESET;

        public static @Nullable Action getByName(String name) {
            for(Action action : values()) if(action.name().equalsIgnoreCase(name)) return action;
            return null;
        }

        public Enum<?> original() {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            return Reflect.readEnumValue((Class<? extends Enum<?>>) clazz(), this.name());
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getTitleActionEnum();
        }

    }

}
