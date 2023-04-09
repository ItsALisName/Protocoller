package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.*;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.MinecraftEncryption;
import net.alis.protocoller.samples.network.chat.LastSeenMessages;
import net.alis.protocoller.samples.network.chat.MessageSignature;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class PacketPlayInChat implements PlayInPacket {

    private PacketDataContainer packetData;
    private String message;
    private @NotOnAllVersions Instant timestamp;
    private @NotOnAllVersions long salt;
    private @NotOnAllVersions MessageSignature signature;
    private @NotOnAllVersions MinecraftEncryption.SignatureData data;
    private @NotOnAllVersions LastSeenMessages.Updater update;
    private @NotOnAllVersions boolean flag;

    private final PacketBuilder builder = PacketBuilder.get(getPacketType());

    public PacketPlayInChat(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.message = packetData.readString(0);
        if(GlobalProvider.get().getServer().getVersion().equals(Version.v1_19)) {
            this.timestamp = packetData.readObject(0, Instant.class);
            this.data = new MinecraftEncryption.SignatureData(packetData.readObject(0, ClassAccessor.get().getMinecraftEncryptionSignatureDataClass()));
            this.flag = packetData.readBoolean(0);
        } else if(GlobalProvider.get().getServer().getVersion().equals(Version.v1_19_1n2)) {
            this.timestamp = packetData.readObject(0, Instant.class);
            this.salt = packetData.readLong(0);
            this.signature = new MessageSignature((Object) packetData.readObject(0, ClassAccessor.get().getMessageSignatureClass()));
            this.flag = packetData.readBoolean(0);
            this.update = new LastSeenMessages.Updater(packetData.readObject(0, ClassAccessor.get().getLastSeenMessagesUpdaterClass()));
        } else if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.timestamp = packetData.readObject(0, Instant.class);
            this.salt = packetData.readLong(0);
            Object msgSignature = packetData.readObject(0, ClassAccessor.get().getMessageSignatureClass());
            if(msgSignature != null){
                this.signature = new MessageSignature(msgSignature);
            }
            this.update = new LastSeenMessages.Updater(packetData.readObject(0, ClassAccessor.get().getLastSeenMessagesUpdaterClass()));
        }
    }

    //For 1.19.1 and 1.19.2
    public PacketPlayInChat(String message, Instant timestamp, long salt, @Nullable MessageSignature signature, boolean flag, LastSeenMessages.Updater update) {
        switch (builder.getConstructorIndicator().getLevel()) {
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message, signature != null ? signature.createOriginal() : null, flag));
                this.signature = signature;
                this.flag = flag;
                break;
            }
            case 3: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message, timestamp, salt, signature != null ? signature.createOriginal() : null, flag, update.createOriginal()));
                this.timestamp = timestamp;
                this.salt = salt;
                this.signature = signature;
                this.flag = flag;
                this.update = update;
                break;
            }
            case 4: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, message, timestamp, salt, signature != null ? signature.createOriginal() : null, update.createOriginal()));
                this.timestamp = timestamp;
                this.salt = salt;
                this.signature = signature;
                this.update = update;
                break;
            }
        }
        this.message = message;
    }

    //For 1.18.2 and lower
    public PacketPlayInChat(String message) {
        this(message, null, 0, null, false, null);
    }

    //For 1.19
    public PacketPlayInChat(String message, MessageSignature signature, boolean flag) {
        this(message, null, 0, signature, flag, null);
    }

    // For 1.19.3 and highest
    public PacketPlayInChat(String message, Instant timestamp, long salt, @Nullable MessageSignature signature, LastSeenMessages.Updater update) {
        this(message, timestamp, salt, signature, false, update);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(message, this.timestamp, this.salt, this.signature, this.flag, this.update).packetData;
        } else {
            this.packetData.writeString(0, message);
        }
    }

    @NotOnAllVersions
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotOnAllVersions Instant timestamp) {
        if (builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(this.message, timestamp, this.salt, this.signature, this.flag, this.update).packetData;
        }
    }

    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        if (builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(this.message, this.timestamp, salt, this.signature, this.flag, this.update).packetData;
        }
    }

    public MinecraftEncryption.SignatureData getSignatureData() {
        return this.data;
    }

    @NotOnAllVersions
    public MessageSignature getSignature() {
        return signature;
    }

    public void setSignature(@NotOnAllVersions MessageSignature signature) {
        if (builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(this.message, this.timestamp, this.salt, signature, this.flag, this.update).packetData;
        }
    }

    @NotOnAllVersions
    public LastSeenMessages.Updater getUpdate() {
        return update;
    }

    public void setUpdate(@NotOnAllVersions LastSeenMessages.Updater update) {
        if (builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(this.message, this.timestamp, this.salt, this.signature, this.flag, update).packetData;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        if (builder.getConstructorIndicator().getLevel() > 1) {
            this.packetData = new PacketPlayInChat(this.message, this.timestamp, this.salt, this.signature, flag, this.update).packetData;
        }
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.CHAT;
    }

    @Override
    public PacketDataContainer getData() {
        return this.packetData;
    }

    @Override
    public Object getRawPacket() {
        return this.packetData.getRawPacket();
    }
}
