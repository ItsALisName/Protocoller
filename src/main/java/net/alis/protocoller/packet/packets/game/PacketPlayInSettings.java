package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataSerializer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.entity.MainHand;
import net.alis.protocoller.parent.entity.player.ChatVisibility;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

public class PacketPlayInSettings implements Packet {

    private final PacketDataSerializer packetData;
    private String language;
    private int viewDistance;
    private ChatVisibility chatVisibility;
    private boolean chatColors;
    private int modelBitMask;
    private @NotOnAllVersions MainHand mainArm;
    private @NotOnAllVersions boolean filterText;
    private @NotOnAllVersions boolean decodedBoolean;

    private final PacketCreator creator = PacketCreator.get(getPacketType());

    public PacketPlayInSettings(PacketDataSerializer packetData) {
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                this.packetData = packetData;
                this.language = packetData.readString(0);
                this.viewDistance = packetData.readInt(0);
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(1);
                if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
                    this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getMainHandEnum()).ordinal());
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
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(2);
                this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getMainHandEnum()).ordinal());
                this.filterText = packetData.readBoolean(1);
                this.decodedBoolean = false;
                break;
            }
            case 2: {
                this.packetData = packetData;
                this.language = packetData.readString(0);
                this.viewDistance = packetData.readInt(0);
                this.chatVisibility = ChatVisibility.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getChatVisibilityEnum()).ordinal());
                this.chatColors = packetData.readBoolean(0);
                this.modelBitMask = packetData.readInt(1);
                this.mainArm = MainHand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getMainHandEnum()).ordinal());
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
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
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
                this.packetData = new PacketDataSerializer(creator.create(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.create(null, language, viewDistance, chatVisibility.original(), chatColors, modelBitMask, mainArm.original(), filterText));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.create(null, language, viewDistance, chatVisibility.original(), chatColors, modelBitMask, mainArm.original(), filterText, decodedBoolean));
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
        if(creator.getConstructorIndicator().getLevel() == 1) {
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
        if(creator.getConstructorIndicator().getLevel() == 1) {
            this.packetData.writeInt(2, modelBitMask);
        } else {
            this.packetData.writeInt(1, modelBitMask);
        }
        this.modelBitMask = modelBitMask;
    }

    @NotOnAllVersions
    public MainHand getMainArm() {
        return mainArm;
    }

    public void setMainArm(@NotOnAllVersions MainHand mainArm) {
        if(creator.getConstructorIndicator().getLevel() >= 1) {
            this.packetData.writeEnumConstant(0, mainArm.original());
        }
        this.mainArm = mainArm;
    }

    public boolean isFilterText() {
        return filterText;
    }

    public void setFilterText(boolean filterText) {
        if(creator.getConstructorIndicator().getLevel() >= 1) {
            this.packetData.writeBoolean(1, filterText);
        }
        this.filterText = filterText;
    }

    public boolean isDecodedBoolean() {
        return decodedBoolean;
    }

    public void setDecodedBoolean(boolean decodedBoolean) {
        if(creator.getConstructorIndicator().getLevel() >= 2) {
            this.packetData.writeBoolean(2, decodedBoolean);
        }
        this.decodedBoolean = decodedBoolean;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.SETTINGS;
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
