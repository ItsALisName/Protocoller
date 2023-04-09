package net.alis.protocoller.plugin.memory;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketLevel;
import net.alis.protocoller.plugin.v0_0_3.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;

import java.util.HashSet;
import java.util.Set;

public class PacketBuilders {

    private final Set<PacketBuilder> packetBuilders;

    private PacketBuilders() {
        this.packetBuilders = new HashSet<>();
    }

    public Set<PacketBuilder> getPacketCreators() {
        return packetBuilders;
    }

    private void setup() {
        long start = System.currentTimeMillis();
        this.addAny(PacketType.Play.Server.ANIMATION, PacketLevel.PacketPlayOutAnimation_1);
        this.addAny(PacketType.Play.Server.STOP_SOUND, PacketLevel.PacketPlayOutStopSound_1);
        this.addAny(PacketType.Status.Server.SERVER_INFO, PacketLevel.PacketStatusOutServerInfo_1);
        this.addAny(PacketType.Login.Server.DISCONNECT, PacketLevel.PacketLoginOutDisconnect_1);
        this.addAny(PacketType.Login.Server.SET_COMPRESSION, PacketLevel.PacketLoginOutSetCompression_1);
        this.addAny(PacketType.Login.Server.SUCCESS, PacketLevel.PacketLoginOutSuccess_1);
        this.addAny(PacketType.Play.Server.ADD_VIBRATION_SIGNAL_PACKET, PacketLevel.ClientboundAddVibrationSignalPacket_1);
        this.addAny(PacketType.Play.Server.CLEAR_TITLES_PACKET, PacketLevel.ClientboundClearTitlesPacket_1);
        this.addAny(PacketType.Play.Server.INITIALIZE_BORDER_PACKET, PacketLevel.ClientboundInitializeBorderPacket_1);
        this.addAny(PacketType.Play.Server.PING_PACKET, PacketLevel.ClientboundPingPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_COMBAT_KILL_PACKET, PacketLevel.ClientboundPlayerCombatKillPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_COMBAT_END_PACKET, PacketLevel.ClientboundPlayerCombatEndPacket_1);
        this.addAny(PacketType.Play.Server.SET_ACTIONBAR_TEXT_PACKET, PacketLevel.ClientboundSetActionBarTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_CENTER_PACKET, PacketLevel.ClientboundSetBorderCenterPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_LERP_SIZE_PACKET, PacketLevel.ClientboundSetBorderLerpSizePacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_SIZE_PACKET, PacketLevel.ClientboundSetBorderSizePacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_WARNING_DELAY_PACKET, PacketLevel.ClientboundSetBorderWarningDelayPacket_1);
        this.addAny(PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE_PACKET, PacketLevel.ClientboundSetBorderWarningDistancePacket_1);
        this.addAny(PacketType.Play.Server.SET_SUBTITLE_TEXT_PACKET, PacketLevel.ClientboundSetSubtitleTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_TITLE_TEXT_PACKET, PacketLevel.ClientboundSetTitleTextPacket_1);
        this.addAny(PacketType.Play.Server.SET_TITLES_ANIMATION_PACKET, PacketLevel.ClientboundSetTitlesAnimationPacket_1);
        this.addAny(PacketType.Play.Client.ABILITIES, PacketLevel.PacketPlayInAbilities_1);
        this.addAny(PacketType.Play.Client.CLIENT_COMMAND, PacketLevel.PacketPlayInClientCommand_1);
        this.addAny(PacketType.Play.Client.CLOSE_WINDOW, PacketLevel.PacketPlayInCloseWindow_1);
        this.addAny(PacketType.Play.Client.DIFFICULTY_CHANGE, PacketLevel.PacketPlayInDifficultyChange_1);
        this.addAny(PacketType.Play.Client.LOOK, PacketLevel.PacketPlayInLook_1);
        this.addAny(PacketType.Play.Client.POSITION, PacketLevel.PacketPlayInPosition_1);
        this.addAny(PacketType.Play.Client.POSITION_LOOK, PacketLevel.PacketPlayInPositionLook_1);
        this.addAny(PacketType.Play.Server.CLOSE_WINDOW, PacketLevel.PacketPlayOutCloseWindow_1);
        this.addAny(PacketType.Play.Server.EXPERIENCE, PacketLevel.PacketPlayOutExperience_1);
        this.addAny(PacketType.Play.Server.HELD_ITEM_SLOT, PacketLevel.PacketPlayOutHeldItemSlot_1);
        this.addAny(PacketType.Play.Server.UNLOAD_CHUNK, PacketLevel.PacketPlayOutUnloadChunk_1);
        this.addAny(PacketType.Play.Server.OPEN_WINDOW_HORSE, PacketLevel.PacketPlayOutOpenWindowHorse_1);
        this.addAny(PacketType.Play.Server.UPDATE_HEALTH, PacketLevel.PacketPlayOutUpdateHealth_1);
        this.addAny(PacketType.Play.Server.UPDATE_TIME, PacketLevel.PacketPlayOutUpdateTime_1);
        this.addAny(PacketType.Play.Server.VIEW_CENTRE, PacketLevel.PacketPlayOutViewCentre_1);
        this.addAny(PacketType.Play.Server.VIEW_DISTANCE, PacketLevel.PacketPlayOutViewDistance_1);
        this.addAny(PacketType.Play.Server.WINDOW_DATA, PacketLevel.PacketPlayOutWindowData_1);
        this.addAny(PacketType.Play.Server.BLOCK_CHANGED_ACK_PACKET, PacketLevel.ClientboundBlockChangedAckPacket_1);
        this.addAny(PacketType.Play.Server.PLAYER_INFO_REMOVE_PACKET, PacketLevel.ClientboundPlayerInfoRemovePacket_1);
        this.addAny(PacketType.Play.Server.SET_SIMULATION_DISTANCE_PACKET, PacketLevel.ClientboundSetSimulationDistancePacket_1);
        this.addAny(PacketType.Play.Client.CHAT_ACK_PACKET, PacketLevel.ServerboundChatAckPacket_1);
        this.addAny(PacketType.Play.Client.PONG_PACKET, PacketLevel.ServerboundPongPacket_1);
        this.addAny(PacketType.Play.Client.ITEM_NAME, PacketLevel.PacketPlayInItemName_1);
        this.addAny(PacketType.Play.Client.SPECTATE, PacketLevel.PacketPlayInSpectate_1);
        this.addAny(PacketType.Play.Server.ABILITIES, PacketLevel.PacketPlayOutAbilities_1);
        this.addAny(PacketType.Play.Server.BLOCK_ACTION, PacketLevel.PacketPlayOutBlockAction_1);
        this.addAny(PacketType.Play.Server.BED, PacketLevel.PacketPlayOutBed_1);
        this.addAny(PacketType.Play.Server.BLOCK_BREAK_ANIMATION, PacketLevel.PacketPlayOutBlockBreakAnimation_1);
        this.addAny(PacketType.Play.Server.CAMERA, PacketLevel.PacketPlayOutCamera_1);
        this.addAny(PacketType.Play.Server.LEVEL_CHUNK_PACKET_DATA, PacketLevel.ClientboundLevelChunkPacketData_1);
        this.addAny(PacketType.Play.Server.LEVEL_CHUNK_WITH_LIGHT_PACKET, PacketLevel.ClientboundLevelChunkWithLightPacket_1);
        this.addAny(PacketType.Play.Server.CHUNK_BIOMES_PACKET, PacketLevel.ClientboundChunkBiomesPacket_1);
        this.addAny(PacketType.Play.Server.CUSTOM_CHAT_COMPLETIONS_PACKET, PacketLevel.ClientboundCustomChatCompletionsPacket_1);
        this.addAny(PacketType.Play.Server.DAMAGE_EVENT_PACKET, PacketLevel.ClientboundDamageEventPacket_1);
        this.addAny(PacketType.Play.Server.DISGUISED_CHAT_PACKET, PacketLevel.ClientboundDisguisedChatPacket_1);
        this.addAny(PacketType.Play.Server.HURT_ANIMATION_PACKET, PacketLevel.ClientboundHurtAnimationPacket_1);

        this.add(PacketType.Play.Server.PLAYER_CHAT_PACKET, PacketLevel.ClientboundPlayerChatPacket_1, Version.v1_19);
        this.add(PacketType.Play.Server.PLAYER_CHAT_PACKET, PacketLevel.ClientboundPlayerChatPacket_2, Version.v1_19_1n2);
        this.add(PacketType.Play.Server.PLAYER_CHAT_PACKET, PacketLevel.ClientboundPlayerChatPacket_3, Version.v1_19_3);
        this.add(PacketType.Play.Server.ATTACH_ENTITY, PacketLevel.PacketPlayOutAttachEntity_1, Version.v1_8);
        this.add(PacketType.Play.Server.ATTACH_ENTITY, PacketLevel.PacketPlayOutAttachEntity_2, Version.v1_9);
        this.add(PacketType.Play.Client.ADVANCEMENTS, PacketLevel.PacketPlayInAdvancements_0, Version.v1_12);
        this.add(PacketType.Play.Client.ADVANCEMENTS, PacketLevel.PacketPlayInAdvancements_1, Version.v1_17);
        this.add(PacketType.Login.Client.CUSTOM_PAYLOAD, PacketLevel.PacketLoginInCustomPayload_0, Version.v1_13);
        this.add(PacketType.Login.Client.CUSTOM_PAYLOAD, PacketLevel.PacketLoginInCustomPayload_1, Version.v1_17);
        this.add(PacketType.Handshake.Client.SET_PROTOCOL, PacketLevel.PacketHandshakeInSetProtocol_0, Version.v1_8);
        this.add(PacketType.Handshake.Client.SET_PROTOCOL, PacketLevel.PacketHandshakeInSetProtocol_1, Version.v1_17);
        this.add(PacketType.Status.Client.PING, PacketLevel.PacketStatusInPing_0, Version.v1_8);
        this.add(PacketType.Status.Client.PING, PacketLevel.PacketStatusInPing_1, Version.v1_17);
        this.add(PacketType.Login.Client.ENCRYPTION_BEGIN, PacketLevel.PacketLoginInEncryptionBegin_0, Version.v1_8);
        this.add(PacketType.Login.Client.ENCRYPTION_BEGIN, PacketLevel.PacketLoginInEncryptionBegin_1, Version.v1_17);
        this.add(PacketType.Login.Server.CUSTOM_PAYLOAD, PacketLevel.PacketLoginOutCustomPayload_0, Version.v1_8);
        this.add(PacketType.Login.Server.CUSTOM_PAYLOAD, PacketLevel.PacketLoginOutCustomPayload_1, Version.v1_17);
        this.add(PacketType.Play.Client.AUTO_RECIPE, PacketLevel.PacketPlayInAutoRecipe_0, Version.v1_8);
        this.add(PacketType.Play.Client.AUTO_RECIPE, PacketLevel.PacketPlayInAutoRecipe_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEDIT, PacketLevel.PacketPlayInBEdit_0, Version.v1_8);
        this.add(PacketType.Play.Client.BEDIT, PacketLevel.PacketPlayInBEdit_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEACON, PacketLevel.PacketPlayInBeacon_0, Version.v1_8);
        this.add(PacketType.Play.Client.BEACON, PacketLevel.PacketPlayInBeacon_1, Version.v1_17);
        this.add(PacketType.Play.Client.BEACON, PacketLevel.PacketPlayInBeacon_2, Version.v1_19);
        this.add(PacketType.Play.Client.BLOCK_DIG, PacketLevel.PacketPlayInBlockDig_0, Version.v1_8);
        this.add(PacketType.Play.Client.BLOCK_DIG, PacketLevel.PacketPlayInBlockDig_1, Version.v1_17);
        this.add(PacketType.Play.Client.BLOCK_DIG, PacketLevel.PacketPlayInBlockDig_2, Version.v1_19);
        this.add(PacketType.Play.Client.CUSTOM_PAYLOAD, PacketLevel.PacketPlayInCustomPayload_0, Version.v1_8);
        this.add(PacketType.Play.Client.CUSTOM_PAYLOAD, PacketLevel.PacketPlayInCustomPayload_1, Version.v1_17);
        this.add(PacketType.Play.Client.DIFFICULTY_LOCK, PacketLevel.PacketPlayInDifficultyLock_0, Version.v1_8);
        this.add(PacketType.Play.Client.DIFFICULTY_LOCK, PacketLevel.PacketPlayInDifficultyLock_1, Version.v1_17);
        this.add(PacketType.Play.Server.AUTO_RECIPE, PacketLevel.PacketPlayOutAutoRecipe_1, Version.v1_13);
        this.add(PacketType.Play.Client.ENCHANT_ITEM, PacketLevel.PacketPlayInEnchantItem_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENCHANT_ITEM, PacketLevel.PacketPlayInEnchantItem_1, Version.v1_17);
        this.add(PacketType.Play.Client.ENTITY_ACTION, PacketLevel.PacketPlayInEntityAction_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENTITY_ACTION, PacketLevel.PacketPlayInEntityAction_1, Version.v1_17);
        this.add(PacketType.Play.Client.ENTITY_NBT_QUERY, PacketLevel.PacketPlayInEntityNBTQuery_0, Version.v1_8);
        this.add(PacketType.Play.Client.ENTITY_NBT_QUERY, PacketLevel.PacketPlayInEntityNBTQuery_1, Version.v1_17);
        this.add(PacketType.Play.Client.FLYING, PacketLevel.PacketPlayInFlying_0, Version.v1_8);
        this.add(PacketType.Play.Client.FLYING, PacketLevel.PacketPlayInFlying_1, Version.v1_17);
        this.add(PacketType.Play.Client.TELEPORT_ACCEPT, PacketLevel.PacketPlayInTeleportAccept_0, Version.v1_8);
        this.add(PacketType.Play.Client.TELEPORT_ACCEPT, PacketLevel.PacketPlayInTeleportAccept_1, Version.v1_17);
        this.add(PacketType.Play.Client.TR_SEL, PacketLevel.PacketPlayInTrSel_0, Version.v1_8);
        this.add(PacketType.Play.Client.TR_SEL, PacketLevel.PacketPlayInTrSel_1, Version.v1_17);
        this.add(PacketType.Play.Client.TAB_COMPLETE, PacketLevel.PacketPlayInTabComplete_1, Version.v1_8);
        this.add(PacketType.Play.Client.TAB_COMPLETE, PacketLevel.PacketPlayInTabComplete_0, Version.v1_9);
        this.add(PacketType.Play.Client.TAB_COMPLETE, PacketLevel.PacketPlayInTabComplete_2, Version.v1_17);
        this.add(PacketType.Play.Client.BLOCK_PLACE, PacketLevel.PacketPlayInBlockPlace_1, Version.v1_8);
        this.add(PacketType.Play.Client.BLOCK_PLACE, PacketLevel.PacketPlayInBlockPlace_2, Version.v1_9);
        this.add(PacketType.Play.Client.BLOCK_PLACE, PacketLevel.PacketPlayInBlockPlace_3, Version.v1_19);
        this.add(PacketType.Play.Client.ARM_ANIMATION, PacketLevel.PacketPlayInArmAnimation_0, Version.v1_8);
        this.add(PacketType.Play.Client.ARM_ANIMATION, PacketLevel.PacketPlayInArmAnimation_1, Version.v1_9);
        this.add(PacketType.Play.Server.COLLECT, PacketLevel.PacketPlayOutCollect_1, Version.v1_8);
        this.add(PacketType.Play.Server.COLLECT, PacketLevel.PacketPlayOutCollect_2, Version.v1_11);
        this.add(PacketType.Play.Server.POSITION, PacketLevel.PacketPlayOutPosition_1, Version.v1_8);
        this.add(PacketType.Play.Server.POSITION, PacketLevel.PacketPlayOutPosition_2, Version.v1_9);
        this.add(PacketType.Play.Server.POSITION, PacketLevel.PacketPlayOutPosition_3, Version.v1_17);
        this.add(PacketType.Play.Server.KEEP_ALIVE, PacketLevel.PacketPlayOutKeepAlive_1, Version.v1_8);
        this.add(PacketType.Play.Server.KEEP_ALIVE, PacketLevel.PacketPlayOutKeepAlive_2, Version.v1_13);
        this.add(PacketType.Login.Server.ENCRYPTION_BEGIN, PacketLevel.PacketLoginOutEncryptionBegin_1, Version.v1_8);
        this.add(PacketType.Login.Server.ENCRYPTION_BEGIN, PacketLevel.PacketLoginOutEncryptionBegin_2, Version.v1_16_4n5);
        this.add(PacketType.Login.Client.START, PacketLevel.PacketLoginInStart_1, Version.v1_8);
        this.add(PacketType.Login.Client.START, PacketLevel.PacketLoginInStart_2, Version.v1_19);
        this.add(PacketType.Play.Client.BOAT_MOVE, PacketLevel.PacketPlayInBoatMove_1, Version.v1_8);
        this.add(PacketType.Play.Client.JIGSAW_GENERATE, PacketLevel.PacketPlayInJigsawGenerate_0, Version.v1_8);
        this.add(PacketType.Play.Client.JIGSAW_GENERATE, PacketLevel.PacketPlayInJigsawGenerate_1, Version.v1_17);
        this.add(PacketType.Play.Client.KEEP_ALIVE, PacketLevel.PacketPlayInKeepAlive_0, Version.v1_8);
        this.add(PacketType.Play.Client.KEEP_ALIVE, PacketLevel.PacketPlayInKeepAlive_1, Version.v1_17);
        this.add(PacketType.Play.Client.PICK_ITEM, PacketLevel.PacketPlayInPickItem_0, Version.v1_13);
        this.add(PacketType.Play.Client.PICK_ITEM, PacketLevel.PacketPlayInPickItem_1, Version.v1_17);
        this.add(PacketType.Play.Client.RESOURCEPACK_STATUS, PacketLevel.PacketPlayInResourcePackStatus_0, Version.v1_8);
        this.add(PacketType.Play.Client.RESOURCEPACK_STATUS, PacketLevel.PacketPlayInResourcePackStatus_1, Version.v1_11);
        this.add(PacketType.Play.Client.SET_COMMAND_MINECART, PacketLevel.PacketPlayInSetCommandMinecart_0, Version.v1_13);
        this.add(PacketType.Play.Client.SET_COMMAND_MINECART, PacketLevel.PacketPlayInSetCommandMinecart_1, Version.v1_17);
        this.add(PacketType.Play.Client.SETTINGS, PacketLevel.PacketPlayInSettings_0, Version.v1_8);
        this.add(PacketType.Play.Client.SETTINGS, PacketLevel.PacketPlayInSettings_1, Version.v1_17);
        this.add(PacketType.Play.Client.SETTINGS, PacketLevel.PacketPlayInSettings_2, Version.v1_18_n1);
        this.add(PacketType.Play.Client.STEER_VEHICLE, PacketLevel.PacketPlayInSteerVehicle_0, Version.v1_8);
        this.add(PacketType.Play.Client.STEER_VEHICLE, PacketLevel.PacketPlayInSteerVehicle_1, Version.v1_17);
        this.add(PacketType.Play.Client.TILE_NBT_QUERY, PacketLevel.PacketPlayInTileNBTQuery_0, Version.v1_8);
        this.add(PacketType.Play.Client.TILE_NBT_QUERY, PacketLevel.PacketPlayInTileNBTQuery_1, Version.v1_17);
        this.add(PacketType.Play.Client.UPDATE_SIGN, PacketLevel.PacketPlayInUpdateSign_0, Version.v1_8);
        this.add(PacketType.Play.Client.UPDATE_SIGN, PacketLevel.PacketPlayInUpdateSign_1, Version.v1_17);
        this.add(PacketType.Play.Client.HELD_ITEM_SLOT, PacketLevel.PacketPlayInHeldItemSlot_0, Version.v1_8);
        this.add(PacketType.Play.Client.HELD_ITEM_SLOT, PacketLevel.PacketPlayInHeldItemSlot_1, Version.v1_17);
        this.add(PacketType.Play.Client.SET_COMMAND_BLOCK, PacketLevel.PacketPlayInSetCommandBlock_0, Version.v1_13);
        this.add(PacketType.Play.Client.SET_COMMAND_BLOCK, PacketLevel.PacketPlayInSetCommandBlock_1, Version.v1_17);
        this.add(PacketType.Play.Client.SET_CREATIVE_SLOT, PacketLevel.PacketPlayInSetCreativeSlot_0, Version.v1_8);
        this.add(PacketType.Play.Client.SET_CREATIVE_SLOT, PacketLevel.PacketPlayInSetCreativeSlot_1, Version.v1_17);
        this.add(PacketType.Play.Client.SET_JIGSAW, PacketLevel.PacketPlayInSetJigsaw_0, Version.v1_14);
        this.add(PacketType.Play.Client.SET_JIGSAW, PacketLevel.PacketPlayInSetJigsaw_1, Version.v1_17);
        this.add(PacketType.Play.Client.STRUCT, PacketLevel.PacketPlayInStruct_0, Version.v1_13);
        this.add(PacketType.Play.Client.STRUCT, PacketLevel.PacketPlayInStruct_1, Version.v1_17);
        this.add(PacketType.Play.Client.USE_ENTITY, PacketLevel.PacketPlayInUseEntity_0, Version.v1_8);
        this.add(PacketType.Play.Client.USE_ENTITY, PacketLevel.PacketPlayInUseEntity_1, Version.v1_9);
        this.add(PacketType.Play.Client.USE_ENTITY, PacketLevel.PacketPlayInUseEntity_0, Version.v1_16);
        this.add(PacketType.Play.Client.USE_ENTITY, PacketLevel.PacketPlayInUseEntity_2, Version.v1_17);
        this.add(PacketType.Play.Client.USE_ITEM, PacketLevel.PacketPlayInUseItem_0, Version.v1_9);
        this.add(PacketType.Play.Client.USE_ITEM, PacketLevel.PacketPlayInUseItem_1, Version.v1_17);
        this.add(PacketType.Play.Client.USE_ITEM, PacketLevel.PacketPlayInUseItem_2, Version.v1_19);
        this.add(PacketType.Play.Client.VEHICLE_MOVE, PacketLevel.PacketPlayInVehicleMove_1, Version.v1_9);
        this.add(PacketType.Play.Client.WINDOW_CLICK, PacketLevel.PacketPlayInWindowClick_0, Version.v1_8);
        this.add(PacketType.Play.Client.WINDOW_CLICK, PacketLevel.PacketPlayInWindowClick_1, Version.v1_17);
        this.add(PacketType.Play.Server.ADVANCEMENTS, PacketLevel.PacketPlayOutAdvancements_1, Version.v1_13);
        this.add(PacketType.Play.Client.RECIPE_DISPLAYED, PacketLevel.PacketPlayInRecipeDisplayed_1, Version.v1_12);
        this.add(PacketType.Play.Client.RECIPE_SETTINGS, PacketLevel.PacketPlayInRecipeSettings_0, Version.v1_16_4n5);
        this.add(PacketType.Play.Client.RECIPE_SETTINGS, PacketLevel.PacketPlayInRecipeSettings_1, Version.v1_17);
        this.add(PacketType.Play.Server.BLOCK_BREAK, PacketLevel.PacketPlayOutBlockBreak_1, Version.v1_15);
        this.add(PacketType.Play.Server.BLOCK_CHANGE, PacketLevel.PacketPlayOutBlockChange_1, Version.v1_8);
        this.add(PacketType.Play.Server.BLOCK_CHANGE, PacketLevel.PacketPlayOutBlockChange_2, Version.v1_13);
        this.add(PacketType.Play.Server.BLOCK_CHANGE, PacketLevel.PacketPlayOutBlockChange_3, Version.v1_16_4n5);
        this.add(PacketType.Play.Server.BOSS, PacketLevel.PacketPlayOutBoss_1, Version.v1_9);
        this.add(PacketType.Play.Server.BOSS, PacketLevel.PacketPlayOutBoss_2, Version.v1_17);
        this.add(PacketType.Play.Server.CHAT, PacketLevel.PacketPlayOutChat_1, Version.v1_8);
        this.add(PacketType.Play.Server.CHAT, PacketLevel.PacketPlayOutChat_2, Version.v1_12);
        this.add(PacketType.Play.Server.CHAT, PacketLevel.PacketPlayOutChat_3, Version.v1_16);
        this.add(PacketType.Play.Server.COMMANDS, PacketLevel.PacketPlayOutCommands_1, Version.v1_13);
        this.add(PacketType.Play.Client.CHAT, PacketLevel.PacketPlayInChat_1, Version.v1_8);
        this.add(PacketType.Play.Client.CHAT, PacketLevel.PacketPlayInChat_2, Version.v1_19);
        this.add(PacketType.Play.Client.CHAT, PacketLevel.PacketPlayInChat_3, Version.v1_19_1n2);
        this.add(PacketType.Play.Client.CHAT, PacketLevel.PacketPlayInChat_4, Version.v1_19_3);
        this.add(PacketType.Play.Server.DELETE_CHAT_PACKET, PacketLevel.ClientboundDeleteChatPacket_1, Version.v1_19_1n2);
        this.add(PacketType.Play.Server.DELETE_CHAT_PACKET, PacketLevel.ClientboundDeleteChatPacket_2, Version.v1_19_3);
        LogsManager.get().getLogger().info("[*] Packet creators loaded in " + (System.currentTimeMillis() - start) + "ms.");
    }

    void add(MinecraftPacketType type, PacketLevel indicator, Version version) {
        PacketBuilder creator = new PacketBuilder(type, indicator, version);
        for(PacketBuilder c : this.packetBuilders) {
            if(c.equals(creator) && c.getVersion().lessThan(version)) {
                if(version.lessThanOrEqualTo(GlobalProvider.get().getServer().getVersion())){
                    this.packetBuilders.remove(c);
                    this.packetBuilders.add(creator);
                    return;
                }
                return;
            }
        }
        if(version.lessThanOrEqualTo(GlobalProvider.get().getServer().getVersion())) this.packetBuilders.add(creator);
    }

    void addAny(MinecraftPacketType type, PacketLevel indicator) {
        this.packetBuilders.add(new PacketBuilder(type, indicator, Version.ANY));
    }

    private static PacketBuilders INSTANCE;
    public static void init() {
        INSTANCE = new PacketBuilders();
        INSTANCE.setup();
    }

    public static PacketBuilders get() {
        return INSTANCE;
    }
}
