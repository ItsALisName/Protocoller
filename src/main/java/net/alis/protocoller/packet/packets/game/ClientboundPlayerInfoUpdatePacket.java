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
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.network.chat.RemoteChatSessionData;
import net.alis.protocoller.samples.server.world.GameMode;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClientboundPlayerInfoUpdatePacket implements PlayOutPacket {

    private final PacketDataContainer packetData;
    private EnumSet<Action> actions;
    private List<Entry> entries;

    public ClientboundPlayerInfoUpdatePacket(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), getPacketType());
        this.packetData = packetData;
        this.actions = EnumSet.noneOf(Action.class);
        for(Enum<?> origAct : (EnumSet<?>) packetData.readObject(0, EnumSet.class)) {
            this.actions.add(Action.getById(origAct.ordinal()));
        }
        this.entries = new ArrayList<>();
        for(Object ent : packetData.readList(0)) this.entries.add(new Entry(ent));
    }

    public ClientboundPlayerInfoUpdatePacket(@NotNull EnumSet<Action> actions, List<Entry> entries) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        EnumSet enumSet = EnumSet.noneOf((Class) ClassAccessor.get().getPlayerInfoUpdateActionEnum());
        for(Action action : actions) enumSet.add(action.original());
        Collection<Object> objects = new ArrayList<>();
        for(Entry e : entries) objects.add(e.createOriginal());
        this.packetData = new PacketDataSerializer(PacketBuilder.get(getPacketType()).buildPacket(null, enumSet, objects));
        this.actions = actions;
        this.entries = entries;
    }

    public EnumSet<Action> getActions() {
        return actions;
    }

    public void setActions(EnumSet<Action> actions) {
        this.actions = actions;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Server.PLAYER_INFO_UPDATE_PACKET;
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

        ADD_PLAYER,
        INITIALIZE_CHAT,
        UPDATE_GAME_MODE,
        UPDATE_LISTED,
        UPDATE_LATENCY,
        UPDATE_DISPLAY_NAME;

        public static @Nullable Action getById(int id) {
            for(Action action : values()) if(action.ordinal() == id) return action;
            return null;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getPlayerInfoUpdateActionEnum(), this.ordinal());
        }

    }

    public static class Entry implements ObjectSample {

        private UUID profileId;
        private GameProfile profile;
        private boolean listed;
        private int latency;
        private GameMode gameMode;
        @Nullable ChatComponent displayName;
        @Nullable RemoteChatSessionData chatSession;

        public Entry(Object entry) {
            AccessedObject object = new AccessedObject(entry);
            this.profileId = object.readField(0, UUID.class);
            this.profile = new GameProfile(object.readField(0, ClassAccessor.get().getGameProfileClass()));
            this.listed = object.readField(0, Boolean.TYPE);
            this.latency = object.readField(0, Integer.TYPE);
            this.gameMode = GameMode.getById(((Enum<?>)object.readField(0, ClassAccessor.get().getGamemodeEnum())).ordinal());
            Object dName = object.readField(0, ChatComponent.clazz());
            if(dName != null) this.displayName = new ChatComponent(ChatSerializer.fromComponent(dName));
            Object cSession = object.readField(0, ClassAccessor.get().getRemoteChatSessionDataClass());
            if(cSession != null) this.chatSession = new RemoteChatSessionData(cSession);
        }

        public Entry(UUID profileId, GameProfile profile, boolean listed, int latency, GameMode gameMode, @Nullable ChatComponent displayName, @Nullable RemoteChatSessionData chatSession) {
            this.profileId = profileId;
            this.profile = profile;
            this.listed = listed;
            this.latency = latency;
            this.gameMode = gameMode;
            this.displayName = displayName;
            this.chatSession = chatSession;
        }

        public UUID getProfileId() {
            return profileId;
        }

        public void setProfileId(UUID profileId) {
            this.profileId = profileId;
        }

        public GameProfile getProfile() {
            return profile;
        }

        public void setProfile(GameProfile profile) {
            this.profile = profile;
        }

        public boolean isListed() {
            return listed;
        }

        public void setListed(boolean listed) {
            this.listed = listed;
        }

        public int getLatency() {
            return latency;
        }

        public void setLatency(int latency) {
            this.latency = latency;
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        public void setGameMode(GameMode gameMode) {
            this.gameMode = gameMode;
        }

        public @Nullable ChatComponent getDisplayName() {
            return displayName;
        }

        public void setDisplayName(@Nullable ChatComponent displayName) {
            this.displayName = displayName;
        }

        public @Nullable RemoteChatSessionData getChatSession() {
            return chatSession;
        }

        public void setChatSession(@Nullable RemoteChatSessionData chatSession) {
            this.chatSession = chatSession;
        }


        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassAccessor.get().getPlayerInfoUpdateEntryClass(), false, UUID.class, ClassAccessor.get().getGameProfileClass(), Boolean.TYPE, Integer.TYPE, ClassAccessor.get().getGamemodeEnum(), ChatComponent.clazz(), ClassAccessor.get().getRemoteChatSessionDataClass()),
                    this.profileId, this.profile.createOriginal(), this.listed, this.latency, this.gameMode.original(), this.displayName != null ? this.displayName.asIChatBaseComponent() : null, this.chatSession != null ? this.chatSession.createOriginal() : null
            );
        }

    }
}
