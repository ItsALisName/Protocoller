package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_5.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.MainHand;
import net.alis.protocoller.samples.entity.player.ChatVisibility;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInSettings implements PlayInPacket {

    private final PacketDataContainer packetData;
    private String language;
    private int viewDistance;
    private ChatVisibility chatVisibility;
    private boolean chatColors;
    private int modelBitMask;
    private @Nullable MainHand mainArm;
    private @Nullable boolean filterText;
    private @Nullable boolean decodedBoolean;

    private final PacketBuilder creator = PacketBuilder.get(getPacketType());

    public PacketPlayInSettings(@NotNull PacketDataContainer packetData) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                this.packetData = packetData;
                this.language = packetData.readString(0);
                this.viewDistance = packetData.readInt(0);
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(1);
                if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
                    this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getMainHandEnum()).ordinal());
                } else {
                    this.mainArm = MainHand.RIGHT;
                }
                this.filterText = false;
                this.decodedBoolean = false;
                break;
            }
            case 1: {
                this.packetData = packetData;
                this.language = packetData.readString(0);
                this.viewDistance = packetData.readInt(1);
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(2);
                this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getMainHandEnum()).ordinal());
                this.filterText = packetData.readBoolean(1);
                this.decodedBoolean = false;
                break;
            }
            case 2: {
                this.packetData = packetData;
                this.language = packetData.readString(0);
                this.viewDistance = packetData.readInt(0);
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(1);
                this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getMainHandEnum()).ordinal());
                this.filterText = packetData.readBoolean(1);
                this.decodedBoolean = packetData.readBoolean(2);
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
    }

    public PacketPlayInSettings(String language, int viewDistance, ChatVisibility chatVisibility, boolean chatColors, int modelBitMask, MainHand mainArm, boolean filterText, boolean decodedBoolean) {
        Utils.checkClassSupportability(getPacketType().getPacketClass(), getPacketType().getPacketName(), true);
        switch (creator.getPacketLevel().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
                    params = new IndexedParam[]{
                            new IndexedParam<>(language, 0),
                            new IndexedParam<>(viewDistance, 0),
                            new IndexedParam<>(chatVisibility.original(), 0),
                            new IndexedParam<>(chatColors, 0),
                            new IndexedParam<>(modelBitMask, 0),
                            new IndexedParam<>(mainArm.original(), 0)
                    };
                } else {
                    params = new IndexedParam[]{
                            new IndexedParam<>(language, 0),
                            new IndexedParam<>(viewDistance, 0),
                            new IndexedParam<>(chatVisibility.original(), 0),
                            new IndexedParam<>(chatColors, 0),
                            new IndexedParam<>(modelBitMask, 0)
                    };
                }
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, language, viewDistance, chatVisibility.original(), chatColors, modelBitMask, mainArm.original(), filterText));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, language, viewDistance, chatVisibility.original(), chatColors, modelBitMask, mainArm.original(), filterText, decodedBoolean));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.language = language;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.chatColors = chatColors;
        this.modelBitMask = modelBitMask;
        this.mainArm = mainArm;
        this.filterText = filterText;
        this.decodedBoolean = decodedBoolean;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.packetData.writeString(0, language);
        this.language = language;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        if(creator.getPacketLevel().getLevel() == 1) {
            this.packetData.writeInt(1, viewDistance);
        } else {
            this.packetData.writeInt(0, viewDistance);
        }
        this.viewDistance = viewDistance;
    }

    public ChatVisibility getChatVisibility() {
        return chatVisibility;
    }

    public void setChatVisibility(ChatVisibility chatVisibility) {
        this.packetData.writeEnumConstant(0, chatVisibility.original());
        this.chatVisibility = chatVisibility;
    }

    public boolean isChatColors() {
        return chatColors;
    }

    public void setChatColors(boolean chatColors) {
        this.packetData.writeBoolean(0, chatColors);
        this.chatColors = chatColors;
    }

    public int getModelBitMask() {
        return modelBitMask;
    }

    public void setModelBitMask(int modelBitMask) {
        if(creator.getPacketLevel().getLevel() == 1) {
            this.packetData.writeInt(2, modelBitMask);
        } else {
            this.packetData.writeInt(1, modelBitMask);
        }
        this.modelBitMask = modelBitMask;
    }

    @Nullable
    public MainHand getMainArm() {
        return mainArm;
    }

    public void setMainArm(@Nullable MainHand mainArm) {
        if(creator.getPacketLevel().getLevel() >= 1) {
            this.packetData.writeEnumConstant(0, mainArm.original());
        }
        this.mainArm = mainArm;
    }

    public boolean isFilterText() {
        return filterText;
    }

    public void setFilterText(boolean filterText) {
        if(creator.getPacketLevel().getLevel() >= 1) {
            this.packetData.writeBoolean(1, filterText);
        }
        this.filterText = filterText;
    }

    public boolean isDecodedBoolean() {
        return decodedBoolean;
    }

    public void setDecodedBoolean(boolean decodedBoolean) {
        if(creator.getPacketLevel().getLevel() >= 2) {
            this.packetData.writeBoolean(2, decodedBoolean);
        }
        this.decodedBoolean = decodedBoolean;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SETTINGS;
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
