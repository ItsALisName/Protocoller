package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.effect.MobEffectList;
import net.alis.protocoller.samples.effect.MobEffects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PacketPlayInBeacon implements PlayInPacket {

    private final PacketDataContainer packetData;
    private MobEffectList primaryEffect;
    private @Nullable MobEffectList secondaryEffect;

    private int primaryId;
    private int secondaryId;

    public PacketPlayInBeacon(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
            this.primaryEffect = new MobEffectList(((Optional<Object>)packetData.readOptional(0)).get());
            this.secondaryEffect = new MobEffectList(((Optional<Object>)packetData.readOptional(1)).get());
            this.primaryId = MobEffects.instance().idFromEffect(this.primaryEffect);
            this.secondaryId = MobEffects.instance().idFromEffect(this.secondaryEffect);
        } else {
            this.primaryId = packetData.readInt(0);
            this.secondaryId = packetData.readInt(1);
            try {
                this.primaryEffect = MobEffects.instance().effectFromId(this.primaryId).getEntry();
            } catch (NullPointerException e) {
                this.primaryEffect = null;
            }
            try {
                this.secondaryEffect = MobEffects.instance().effectFromId(this.secondaryId).getEntry();
            } catch (NullPointerException e) {
                this.secondaryEffect = null;
            }
        }
    }

    public PacketPlayInBeacon(MobEffectList primaryEffect, MobEffectList secondaryEffect) {
        PacketBuilder builder = PacketBuilder.get(getPacketType());
        int primaryId = MobEffects.instance().idFromEffect(primaryEffect);
        int secondaryId = MobEffects.instance().idFromEffect(secondaryEffect);
        switch (builder.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params = {
                    new IndexedParam<>(primaryId, 0),
                    new IndexedParam<>(secondaryId, 1)
                };
                this.packetData = new PacketDataSerializer(builder.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, primaryId, secondaryId));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(builder.buildPacket(null, Optional.of(primaryEffect.createOriginal()), Optional.of(secondaryEffect.createOriginal())));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.primaryEffect = primaryEffect;
        this.secondaryEffect = secondaryEffect;
    }

    public MobEffectList getPrimaryEffect() {
        return primaryEffect;
    }

    public void setPrimaryEffect(MobEffectList primaryEffect) {
        this.primaryId = MobEffects.instance().idFromEffect(primaryEffect);
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
            this.packetData.writeOptional(0, Optional.of(primaryEffect.createOriginal()));
        } else {
            this.packetData.writeInt(0, this.primaryId);
        }
        this.primaryEffect = primaryEffect;
    }

    public MobEffectList getSecondaryEffect() {
        return secondaryEffect;
    }

    public void setSecondaryEffect(MobEffectList secondaryEffect) {
        this.secondaryId = MobEffects.instance().idFromEffect(secondaryEffect);
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19)) {
            this.packetData.writeOptional(1, Optional.of(primaryEffect.createOriginal()));
        } else {
            this.packetData.writeInt(1, this.secondaryId);
        }
        this.secondaryEffect = secondaryEffect;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public int getSecondaryId() {
        return secondaryId;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.BEACON;
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
