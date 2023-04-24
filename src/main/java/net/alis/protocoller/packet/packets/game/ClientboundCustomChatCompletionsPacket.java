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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClientboundCustomChatCompletionsPacket implements PlayOutPacket {

    private PacketDataContainer packetData;
    private Action action;
    private List<String> list;

    public ClientboundCustomChatCompletionsPacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.action = Action.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getChatCompletionsActionEnum()).ordinal());
        this.list = (List<String>) packetData.readList(0);
    }

    public ClientboundCustomChatCompletionsPacket(@NotNull Action action, List<String> list) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, action.original(), list));
        this.action = action;
        this.list = list;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.packetData = new ClientboundCustomChatCompletionsPacket(action, this.list).packetData;
        this.action = action;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.packetData = new ClientboundCustomChatCompletionsPacket(this.action, list).packetData;
        this.list = list;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.CUSTOM_CHAT_COMPLETIONS_PACKET;
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

        ADD(0),
        REMOVE(1),
        SET(2);

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Nullable
        public static Action getById(int id) {
            for(Action action : values()) {
                if(action.id == id) return action;
            }
            return null;
        }

        public Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getChatCompletionsActionEnum(), this.id);
        }

    }

}
