package net.alis.protocoller.bukkit.data;

import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.ConstructorLevelIndicator;
import net.alis.protocoller.bukkit.network.packet.PacketCreator;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;

import java.util.HashSet;
import java.util.Set;

public class PacketCreators {

    public static PacketCreators INSTANCE;
    public static void init() {
        INSTANCE = new PacketCreators();
        INSTANCE.setup();
    }

    private final Set<PacketCreator> packetCreators;

    private PacketCreators() {
        this.packetCreators = new HashSet<>();
    }

    public Set<PacketCreator> getPacketCreators() {
        return packetCreators;
    }

    private void setup() {
        this.addAny(PacketType.Play.Server.STOP_SOUND, ConstructorLevelIndicator.PacketPlayOutStopSound_1);
        this.addAny(PacketType.Status.Server.SERVER_INFO, ConstructorLevelIndicator.PacketStatusOutServerInfo_1);
        this.addAny(PacketType.Login.Server.DISCONNECT, ConstructorLevelIndicator.PacketLoginOutDisconnect_1);
        this.addAny(PacketType.Login.Server.SET_COMPRESSION, ConstructorLevelIndicator.PacketLoginOutSetCompression_1);
        this.addAny(PacketType.Login.Server.SUCCESS, ConstructorLevelIndicator.PacketLoginOutSuccess_1);
        this.addAny(PacketType.Play.Server.ADD_VIBRATION_SIGNAL_PACKET, ConstructorLevelIndicator.ClientboundAddVibrationSignalPacket_1);
        this.addAny(PacketType.Play.Server.CLEAR_TITLES_PACKET, ConstructorLevelIndicator.ClientboundClearTitlesPacket_1);
        this.addAny(PacketType.Play.Server.INITIALIZE_BORDER_PACKET, ConstructorLevelIndicator.ClientboundInitializeBorderPacket_1);
        this.addAny(PacketType.Play.Server.PING_PACKET, ConstructorLevelIndicator.ClientboundPingPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_COMBAT_KILL_PACKET, ConstructorLevelIndicator.ClientboundPlayerCombatKillPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_COMBAT_END_PACKET, ConstructorLevelIndicator.ClientboundPlayerCombatEndPacket_1);
        this.addAny(PacketType.Play.Server.SET_ACTIONBAR_TEXT_PACKET, ConstructorLevelIndicator.ClientboundSetActionBarTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_CENTER_PACKET, ConstructorLevelIndicator.ClientboundSetBorderCenterPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_LERP_SIZE_PACKET, ConstructorLevelIndicator.ClientboundSetBorderLerpSizePacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_SIZE_PACKET, ConstructorLevelIndicator.ClientboundSetBorderSizePacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_WARNING_DELAY_PACKET, ConstructorLevelIndicator.ClientboundSetBorderWarningDelayPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE_PACKET, ConstructorLevelIndicator.ClientboundSetBorderWarningDistancePacket_1);
        this.addAny(PacketType.Play.Server.SET_SUBTITLE_TEXT_PACKET, ConstructorLevelIndicator.ClientboundSetSubtitleTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_TITLE_TEXT_PACKET, ConstructorLevelIndicator.ClientboundSetTitleTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_TITLES_ANIMATION_PACKET, ConstructorLevelIndicator.ClientboundSetTitlesAnimationPacket_1);
        this.addAny(PacketType.Play.Client.ABILITIES, ConstructorLevelIndicator.PacketPlayInAbilities_1);
        this.addAny(PacketType.Play.Client.CHAT, ConstructorLevelIndicator.PacketPlayInChat_1);
        this.addAny(PacketType.Play.Client.CLIENT_COMMAND, ConstructorLevelIndicator.PacketPlayInClientCommand_1);
        this.addAny(PacketType.Play.Client.CLOSE_WINDOW, ConstructorLevelIndicator.PacketPlayInCloseWindow_1);
        this.addAny(PacketType.Play.Client.DIFFICULTY_CHANGE, ConstructorLevelIndicator.PacketPlayInDifficultyChange_1);
        this.addAny(PacketType.Play.Client.LOOK, ConstructorLevelIndicator.PacketPlayInLook_1);
        this.addAny(PacketType.Play.Client.POSITION, ConstructorLevelIndicator.PacketPlayInPosition_1);
        this.addAny(PacketType.Play.Client.POSITION_LOOK, ConstructorLevelIndicator.PacketPlayInPositionLook_1);
        this.addAny(PacketType.Play.Server.CLOSE_WINDOW, ConstructorLevelIndicator.PacketPlayOutCloseWindow_1);
        this.addAny(PacketType.Play.Server.EXPERIENCE, ConstructorLevelIndicator.PacketPlayOutExperience_1);
        this.addAny(PacketType.Play.Server.HELD_ITEM_SLOT, ConstructorLevelIndicator.PacketPlayOutHeldItemSlot_1);
        this.addAny(PacketType.Play.Server.UNLOAD_CHUNK, ConstructorLevelIndicator.PacketPlayOutUnloadChunk_1);
        this.addAny(PacketType.Play.Server.OPEN_WINDOW_HORSE, ConstructorLevelIndicator.PacketPlayOutOpenWindowHorse_1);
        this.addAny(PacketType.Play.Server.UPDATE_HEALTH, ConstructorLevelIndicator.PacketPlayOutUpdateHealth_1);
        this.addAny(PacketType.Play.Server.UPDATE_TIME, ConstructorLevelIndicator.PacketPlayOutUpdateTime_1);
        this.addAny(PacketType.Play.Server.VIEW_CENTRE, ConstructorLevelIndicator.PacketPlayOutViewCentre_1);
        this.addAny(PacketType.Play.Server.VIEW_DISTANCE, ConstructorLevelIndicator.PacketPlayOutViewDistance_1);
        this.addAny(PacketType.Play.Server.WINDOW_DATA, ConstructorLevelIndicator.PacketPlayOutWindowData_1);
        this.addAny(PacketType.Play.Server.BLOCK_CHANGED_ACK_PACKET, ConstructorLevelIndicator.ClientboundBlockChangedAckPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_INFO_REMOVE_PACKET, ConstructorLevelIndicator.ClientboundPlayerInfoRemovePacket_1);
        this.addAny(PacketType.Play.Server.SET_SIMULATION_DISTANCE_PACKET, ConstructorLevelIndicator.ClientboundSetSimulationDistancePacket_1);
        this.addAny(PacketType.Play.Client.CHAT_ACK_PACKET, ConstructorLevelIndicator.ServerboundChatAckPacket_1);
        this.addAny(PacketType.Play.Client.PONG_PACKET, ConstructorLevelIndicator.ServerboundPongPacket_1);
        this.addAny(PacketType.Play.Client.ITEM_NAME, ConstructorLevelIndicator.PacketPlayInItemName_1);
        this.addAny(PacketType.Play.Client.SPECTATE, ConstructorLevelIndicator.PacketPlayInSpectate_1);

        this.add(PacketType.Play.Client.ADVANCEMENTS, ConstructorLevelIndicator.PacketPlayInAdvancements_0, Version.v1_12);
        this.add(PacketType.Play.Client.ADVANCEMENTS, ConstructorLevelIndicator.PacketPlayInAdvancements_1, Version.v1_17);
        this.add(PacketType.Login.Client.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketLoginInCustomPayload_0, Version.v1_13);
        this.add(PacketType.Login.Client.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketLoginInCustomPayload_1, Version.v1_17);
        this.add(PacketType.Handshake.Client.SET_PROTOCOL, ConstructorLevelIndicator.PacketHandshakeInSetProtocol_0, Version.v1_8);
        this.add(PacketType.Handshake.Client.SET_PROTOCOL, ConstructorLevelIndicator.PacketHandshakeInSetProtocol_1, Version.v1_17);
        this.add(PacketType.Status.Client.PING, ConstructorLevelIndicator.PacketStatusInPing_0, Version.v1_8);
        this.add(PacketType.Status.Client.PING, ConstructorLevelIndicator.PacketStatusInPing_1, Version.v1_17);
        this.add(PacketType.Login.Client.ENCRYPTION_BEGIN, ConstructorLevelIndicator.PacketLoginInEncryptionBegin_0, Version.v1_8);
        this.add(PacketType.Login.Client.ENCRYPTION_BEGIN, ConstructorLevelIndicator.PacketLoginInEncryptionBegin_1, Version.v1_17);
        this.add(PacketType.Login.Server.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketLoginOutCustomPayload_0, Version.v1_8);
        this.add(PacketType.Login.Server.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketLoginOutCustomPayload_1, Version.v1_17);
        this.add(PacketType.Play.Client.AUTO_RECIPE, ConstructorLevelIndicator.PacketPlayInAutoRecipe_0, Version.v1_8);
        this.add(PacketType.Play.Client.AUTO_RECIPE, ConstructorLevelIndicator.PacketPlayInAutoRecipe_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEDIT, ConstructorLevelIndicator.PacketPlayInBEdit_0, Version.v1_8);
        this.add(PacketType.Play.Client.BEDIT, ConstructorLevelIndicator.PacketPlayInBEdit_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEACON, ConstructorLevelIndicator.PacketPlayInBeacon_0, Version.v1_8);
        this.add(PacketType.Play.Client.BEACON, ConstructorLevelIndicator.PacketPlayInBeacon_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEACON, ConstructorLevelIndicator.PacketPlayInBeacon_2, Version.v1_19);
        this.add(PacketType.Play.Client.BLOCK_DIG, ConstructorLevelIndicator.PacketPlayInBlockDig_0, Version.v1_8);
        this.add(PacketType.Play.Client.BLOCK_DIG, ConstructorLevelIndicator.PacketPlayInBlockDig_1, Version.v1_17);
        this.add(PacketType.Play.Client.BLOCK_DIG, ConstructorLevelIndicator.PacketPlayInBlockDig_2, Version.v1_19);
        this.add(PacketType.Play.Client.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketPlayInCustomPayload_0, Version.v1_8);
        this.add(PacketType.Play.Client.CUSTOM_PAYLOAD, ConstructorLevelIndicator.PacketPlayInCustomPayload_1, Version.v1_17);
        this.add(PacketType.Play.Client.DIFFICULTY_LOCK, ConstructorLevelIndicator.PacketPlayInDifficultyLock_0, Version.v1_8);
        this.add(PacketType.Play.Client.DIFFICULTY_LOCK, ConstructorLevelIndicator.PacketPlayInDifficultyLock_1, Version.v1_17);
        this.add(PacketType.Play.Client.ENCHANT_ITEM, ConstructorLevelIndicator.PacketPlayInEnchantItem_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENCHANT_ITEM, ConstructorLevelIndicator.PacketPlayInEnchantItem_1, Version.v1_17);
        this.add(PacketType.Play.Client.ENTITY_ACTION, ConstructorLevelIndicator.PacketPlayInEntityAction_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENTITY_ACTION, ConstructorLevelIndicator.PacketPlayInEntityAction_1, Version.v1_17);
        this.add(PacketType.Play.Client.ENTITY_NBT_QUERY, ConstructorLevelIndicator.PacketPlayInEntityNBTQuery_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENTITY_NBT_QUERY, ConstructorLevelIndicator.PacketPlayInEntityNBTQuery_1, Version.v1_17);
        this.add(PacketType.Play.Client.FLYING, ConstructorLevelIndicator.PacketPlayInFlying_0, Version.v1_8);
        this.add(PacketType.Play.Client.FLYING, ConstructorLevelIndicator.PacketPlayInFlying_1, Version.v1_17);
        this.add(PacketType.Play.Client.TELEPORT_ACCEPT, ConstructorLevelIndicator.PacketPlayInTeleportAccept_0, Version.v1_8);
        this.add(PacketType.Play.Client.TELEPORT_ACCEPT, ConstructorLevelIndicator.PacketPlayInTeleportAccept_1, Version.v1_17);
        this.add(PacketType.Play.Client.TR_SEL, ConstructorLevelIndicator.PacketPlayInTrSel_0, Version.v1_8);
        this.add(PacketType.Play.Client.TR_SEL, ConstructorLevelIndicator.PacketPlayInTrSel_1, Version.v1_17);
        this.add(PacketType.Play.Client.TAB_COMPLETE, ConstructorLevelIndicator.PacketPlayInTabComplete_1, Version.v1_8);
        this.add(PacketType.Play.Client.TAB_COMPLETE, ConstructorLevelIndicator.PacketPlayInTabComplete_0, Version.v1_9);
        this.add(PacketType.Play.Client.TAB_COMPLETE, ConstructorLevelIndicator.PacketPlayInTabComplete_2, Version.v1_17);
        this.add(PacketType.Play.Client.BLOCK_PLACE, ConstructorLevelIndicator.PacketPlayInBlockPlace_1, Version.v1_8);
        this.add(PacketType.Play.Client.BLOCK_PLACE, ConstructorLevelIndicator.PacketPlayInBlockPlace_2, Version.v1_9);
        this.add(PacketType.Play.Client.ARM_ANIMATION, ConstructorLevelIndicator.PacketPlayInArmAnimation_0, Version.v1_8);
        this.add(PacketType.Play.Client.ARM_ANIMATION, ConstructorLevelIndicator.PacketPlayInArmAnimation_1, Version.v1_9);
        this.add(PacketType.Play.Server.COLLECT, ConstructorLevelIndicator.PacketPlayOutCollect_1, Version.v1_8);
        this.add(PacketType.Play.Server.COLLECT, ConstructorLevelIndicator.PacketPlayOutCollect_2, Version.v1_11);
        this.add(PacketType.Play.Server.POSITION, ConstructorLevelIndicator.PacketPlayOutPosition_1, Version.v1_8);
        this.add(PacketType.Play.Server.POSITION, ConstructorLevelIndicator.PacketPlayOutPosition_2, Version.v1_9);
        this.add(PacketType.Play.Server.POSITION, ConstructorLevelIndicator.PacketPlayOutPosition_3, Version.v1_17);
        this.add(PacketType.Play.Server.KEEP_ALIVE, ConstructorLevelIndicator.PacketPlayOutKeepAlive_1, Version.v1_8);
        this.add(PacketType.Play.Server.KEEP_ALIVE, ConstructorLevelIndicator.PacketPlayOutKeepAlive_2, Version.v1_13);
        this.add(PacketType.Login.Server.ENCRYPTION_BEGIN, ConstructorLevelIndicator.PacketLoginOutEncryptionBegin_1, Version.v1_8);
        this.add(PacketType.Login.Server.ENCRYPTION_BEGIN, ConstructorLevelIndicator.PacketLoginOutEncryptionBegin_2, Version.v1_16_4n5);
        this.add(PacketType.Login.Client.START, ConstructorLevelIndicator.PacketLoginInStart_1, Version.v1_8);
        this.add(PacketType.Login.Client.START, ConstructorLevelIndicator.PacketLoginInStart_2, Version.v1_19);
        this.add(PacketType.Play.Client.BOAT_MOVE, ConstructorLevelIndicator.PacketPlayInBoatMove_1, Version.v1_8);
        this.add(PacketType.Play.Client.JIGSAW_GENERATE, ConstructorLevelIndicator.PacketPlayInJigsawGenerate_0, Version.v1_8);
        this.add(PacketType.Play.Client.JIGSAW_GENERATE, ConstructorLevelIndicator.PacketPlayInJigsawGenerate_1, Version.v1_17);
        this.add(PacketType.Play.Client.KEEP_ALIVE, ConstructorLevelIndicator.PacketPlayInKeepAlive_0, Version.v1_8);
        this.add(PacketType.Play.Client.KEEP_ALIVE, ConstructorLevelIndicator.PacketPlayInKeepAlive_1, Version.v1_17);
        this.add(PacketType.Play.Client.PICK_ITEM, ConstructorLevelIndicator.PacketPlayInPickItem_0, Version.v1_13);
        this.add(PacketType.Play.Client.PICK_ITEM, ConstructorLevelIndicator.PacketPlayInPickItem_1, Version.v1_17);
        this.add(PacketType.Play.Client.RESOURCEPACK_STATUS, ConstructorLevelIndicator.PacketPlayInResourcePackStatus_0, Version.v1_8);
        this.add(PacketType.Play.Client.RESOURCEPACK_STATUS, ConstructorLevelIndicator.PacketPlayInResourcePackStatus_1, Version.v1_11);
        this.add(PacketType.Play.Client.SET_COMMAND_MINECART, ConstructorLevelIndicator.PacketPlayInSetCommandMinecart_0, Version.v1_13);
        this.add(PacketType.Play.Client.SET_COMMAND_MINECART, ConstructorLevelIndicator.PacketPlayInSetCommandMinecart_1, Version.v1_17);
        this.add(PacketType.Play.Client.SETTINGS, ConstructorLevelIndicator.PacketPlayInSettings_0, Version.v1_8);
        this.add(PacketType.Play.Client.SETTINGS, ConstructorLevelIndicator.PacketPlayInSettings_1, Version.v1_17);
        this.add(PacketType.Play.Client.SETTINGS, ConstructorLevelIndicator.PacketPlayInSettings_2, Version.v1_18_n1);
        this.add(PacketType.Play.Client.STEER_VEHICLE, ConstructorLevelIndicator.PacketPlayInSteerVehicle_0, Version.v1_8);
        this.add(PacketType.Play.Client.STEER_VEHICLE, ConstructorLevelIndicator.PacketPlayInSteerVehicle_1, Version.v1_17);
        this.add(PacketType.Play.Client.TILE_NBT_QUERY, ConstructorLevelIndicator.PacketPlayInTileNBTQuery_0, Version.v1_8);
        this.add(PacketType.Play.Client.TILE_NBT_QUERY, ConstructorLevelIndicator.PacketPlayInTileNBTQuery_1, Version.v1_17);
        this.add(PacketType.Play.Client.UPDATE_SIGN, ConstructorLevelIndicator.PacketPlayInUpdateSign_0, Version.v1_8);
        this.add(PacketType.Play.Client.UPDATE_SIGN, ConstructorLevelIndicator.PacketPlayInUpdateSign_1, Version.v1_17);
    }

    void add(MinecraftPacketType type, ConstructorLevelIndicator indicator, Version version) {
        PacketCreator creator = new PacketCreator(type, indicator, version);
        if(this.packetCreators.contains(creator)) {
            for(PacketCreator c : this.packetCreators) {
                if(!c.equals(creator)) continue;
                if(c.getVersion().lessThan(version) && version.greaterThanOrEqualTo(InitialData.INSTANCE.getPreVersion())) {
                    this.packetCreators.remove(c);
                    this.packetCreators.add(creator);
                }
                break;
            }
        } else {
            this.packetCreators.add(creator);
        }
    }

    void addAny(MinecraftPacketType type, ConstructorLevelIndicator indicator) {
        this.packetCreators.add(new PacketCreator(type, indicator, Version.ANY));
    }
}
