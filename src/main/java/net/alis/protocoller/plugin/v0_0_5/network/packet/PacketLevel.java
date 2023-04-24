package net.alis.protocoller.plugin.v0_0_5.network.packet;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;
import net.alis.protocoller.plugin.v0_0_5.server.level.chunk.ProtocolChunk;
import net.alis.protocoller.plugin.v0_0_5.server.level.lighting.ProtocolLightEngine;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.network.chat.ChatComponent;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;

public enum PacketLevel {

    PacketStatusInPing_0((byte) 0),
    PacketStatusInPing_1((byte) 1, long.class),

    PacketStatusOutPong_1((byte) 1, long.class),

    PacketStatusOutServerInfo_1((byte) 1, ClassAccessor.get().getServerPingClass()),
    PacketLoginInCustomPayload_0((byte) 0),

    PacketLoginInCustomPayload_1((byte) 1, int.class, ClassAccessor.get().getPacketDataSerializerClass()),

    PacketLoginInEncryptionBegin_0((byte) 0),
    PacketLoginInEncryptionBegin_1((byte) 1, SecretKey.class, PublicKey.class, byte[].class),

    PacketLoginInStart_1((byte) 1, ClassAccessor.get().getGameProfileClass()),
    PacketLoginInStart_2((byte) 2, String.class, Optional.class),

    PacketLoginOutCustomPayload_0((byte) 0),
    PacketLoginOutCustomPayload_1((byte) 1, int.class, ClassAccessor.get().getMinecraftKeyClass(), ClassAccessor.get().getPacketDataSerializerClass()),

    PacketLoginOutDisconnect_1((byte) 1, ChatComponent.clazz()),

    PacketLoginOutEncryptionBegin_1((byte) 1, String.class, PublicKey.class, byte[].class),
    PacketLoginOutEncryptionBegin_2((byte) 2, String.class, byte[].class, byte[].class),

    PacketLoginOutSetCompression_1((byte) 1, int.class),

    PacketLoginOutSuccess_1((byte) 1, ClassAccessor.get().getGameProfileClass()),

    PacketHandshakeInSetProtocol_0((byte) 0),
    PacketHandshakeInSetProtocol_1((byte) 1, String.class, int.class, ClassAccessor.get().getProtocolEnum()),

    ClientboundChunkBiomesPacket_1((byte) 1, List.class),
    ClientboundAddVibrationSignalPacket_1((byte) 1, ClassAccessor.get().getVibrationPathClass()),
    ClientboundClearTitlesPacket_1((byte) 1, boolean.class),
    ClientboundInitializeBorderPacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundPlayerChatPacket_1((byte) 1, ChatComponent.clazz(), Optional.class, int.class, ClassAccessor.get().getChatSenderClass(), Instant.class, ClassAccessor.get().getMinecraftEncryptionSignatureDataClass()),
    ClientboundPlayerChatPacket_2((byte) 2, ClassAccessor.get().getPlayerChatMessageClass(), ClassAccessor.get().getChatBoundNetworkClass()),
    ClientboundPlayerChatPacket_3((byte) 3, UUID.class, int.class, ClassAccessor.get().getMessageSignatureClass(), ClassAccessor.get().getSignedMessageBodyPackedClass(), ChatComponent.clazz(), ClassAccessor.get().getFilterMaskClass(), ClassAccessor.get().getChatBoundNetworkClass()),
    ClientboundPingPacket_1((byte) 1, int.class),
    ClientboundPlayerCombatEndPacket_1((byte) 1, int.class, int.class),
    ClientboundPlayerCombatEnterPacket_1((byte) 1),
    PacketPlayOutTitle_1((byte) 1, net.alis.protocoller.packet.packets.game.PacketPlayOutTitle.Action.clazz(), ChatComponent.clazz(), int.class, int.class, int.class),
    ClientboundPlayerCombatKillPacket_1((byte) 1, int.class, int.class, ChatComponent.clazz()),
    ClientboundSetActionBarTextPacket_1((byte) 1, ChatComponent.clazz()),
    ClientboundSetBorderCenterPacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderLerpSizePacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderSizePacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDelayPacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDistancePacket_1((byte) 1, ClassAccessor.get().getMinecraftWorldBorderClass()),
    ClientboundSetSubtitleTextPacket_1((byte) 1, ChatComponent.clazz()),
    ClientboundSetTitleTextPacket_1((byte) 1, ChatComponent.clazz()),
    ClientboundSystemChatPacket_1((byte) 1, ChatComponent.clazz(), int.class),
    ClientboundSystemChatPacket_2((byte) 2, ChatComponent.clazz(), boolean.class),
    ClientboundSetTitlesAnimationPacket_1((byte) 1, int.class, int.class, int.class),
    ClientboundBlockChangedAckPacket_1((byte) 1, int.class),
    ClientboundUpdateEnabledFeaturesPacket_1((byte) 1, List.class),
    ClientboundPlayerInfoRemovePacket_1((byte) 1, List.class),
    ClientboundPlayerInfoUpdatePacket_1((byte) 1, EnumSet.class, Collection.class),
    ClientboundSetSimulationDistancePacket_1((byte) 1, int.class),
    ServerboundChatAckPacket_1((byte) 1, int.class),
    ServerboundPongPacket_1((byte) 1, int.class),
    ClientboundServerDataPacket_1((byte) 1, ChatComponent.clazz(), String.class, Boolean.TYPE),
    ClientboundServerDataPacket_2((byte) 2, ChatComponent.clazz(), String.class, Boolean.TYPE, Boolean.TYPE),
    ClientboundServerDataPacket_3((byte) 3, ChatComponent.clazz(), Optional.class, Boolean.TYPE),
    ClientboundLevelChunkPacketData_1((byte) 1, ProtocolChunk.clazz()),
    ClientboundLevelChunkWithLightPacket_1((byte) 1, ProtocolChunk.clazz(), ClassAccessor.get().getLightEngineClass(), BitSet.class, BitSet.class, boolean.class),
    ClientboundLightUpdatePacketData_1((byte) 1, ChunkCoordIntPair.clazz(), ProtocolLightEngine.clazz(), BitSet.class, BitSet.class, boolean.class),

    PacketPlayInAbilities_1((byte) 1, ClassAccessor.get().getPlayerAbilitiesClass()),
    PacketPlayInBoatMove_1((byte) 1, boolean.class, boolean.class),
    PacketPlayInSpectate_1((byte) 1, UUID.class),
    PacketPlayInEntityAction_0((byte) 0),
    PacketPlayInEntityAction_1((byte) 1, ClassAccessor.get().getMinecraftEntityClass(), ClassAccessor.get().getPlayerActionEnum(), int.class),
    PacketPlayInSteerVehicle_0((byte) 0),
    PacketPlayInSteerVehicle_1((byte) 1, float.class, float.class, boolean.class, boolean.class),
    PacketPlayInTileNBTQuery_0((byte) 0),
    PacketPlayInTileNBTQuery_1((byte) 1, int.class, ClassAccessor.get().getBlockPositionClass()),
    PacketPlayInUpdateSign_0((byte) 0),
    PacketPlayInUpdateSign_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), String.class, String.class, String.class, String.class),
    PacketPlayInArmAnimation_0((byte) 0),
    PacketPlayInArmAnimation_1((byte) 1, ClassAccessor.get().getHandEnum()),
    PacketPlayInAdvancements_0((byte) 0),
    PacketPlayInAdvancements_1((byte) 1, ClassAccessor.get().getAdvancementsStatusEnum(), ClassAccessor.get().getMinecraftKeyClass()),
    PacketPlayInAutoRecipe_0((byte) 0),
    PacketPlayInAutoRecipe_1((byte) 1, int.class, ClassAccessor.get().getIRecipeClass(), boolean.class),
    PacketPlayInBEdit_0((byte) 0),
    PacketPlayInBEdit_1((byte) 1, int.class, List.class, Optional.class),
    PacketPlayInBeacon_0((byte) 0),
    PacketPlayInBeacon_1((byte) 1, int.class, int.class),
    PacketPlayInBeacon_2((byte) 2, Optional.class, Optional.class),
    PacketPlayInBlockDig_0((byte) 0),
    PacketPlayInBlockDig_1((byte) 1, ClassAccessor.get().getPlayerDigTypeEnum(), ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getDirectionEnum()),
    PacketPlayInBlockDig_2((byte) 2, ClassAccessor.get().getPlayerDigTypeEnum(), ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getDirectionEnum(), int.class),
    PacketPlayInBlockPlace_1((byte) 1, ClassAccessor.get().getMinecraftItemStackClass()),
    PacketPlayInBlockPlace_2((byte) 2, ClassAccessor.get().getHandEnum()),
    PacketPlayInBlockPlace_3((byte) 3, ClassAccessor.get().getHandEnum(), int.class),
    PacketPlayInRecipeDisplayed_1((byte) 1, ClassAccessor.get().getIRecipeClass()),
    PacketPlayInChat_1((byte) 1, String.class),
    PacketPlayInChat_2((byte) 2, String.class, ClassAccessor.get().getMessageSignatureClass(), boolean.class),
    PacketPlayInChat_3((byte) 3, String.class, Instant.class, long.class, ClassAccessor.get().getMessageSignatureClass(), boolean.class, ClassAccessor.get().getLastSeenMessagesUpdaterClass()),
    PacketPlayInChat_4((byte) 4, String.class, Instant.class, long.class, ClassAccessor.get().getMessageSignatureClass(), ClassAccessor.get().getLastSeenMessagesUpdaterClass()),
    PacketPlayInUseItem_0((byte) 0),
    PacketPlayInUseItem_1((byte) 1, ClassAccessor.get().getHandEnum(), ClassAccessor.get().getMovingObjectPositionBlockClass()),
    PacketPlayInUseItem_2((byte) 2, ClassAccessor.get().getHandEnum(), ClassAccessor.get().getMovingObjectPositionBlockClass(), int.class),
    PacketPlayInSetCreativeSlot_0((byte) 0),
    PacketPlayInSetCreativeSlot_1((byte) 1, int.class, ClassAccessor.get().getMinecraftItemStackClass()),
    PacketPlayInVehicleMove_1((byte) 1, ClassAccessor.get().getMinecraftEntityClass()),
    PacketPlayInSetJigsaw_0((byte) 0),
    PacketPlayInSetJigsaw_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getMinecraftKeyClass(), ClassAccessor.get().getMinecraftKeyClass(), ClassAccessor.get().getMinecraftKeyClass(), String.class, ClassAccessor.get().getTileEntityJigsawJointypeEnum()),
    PacketPlayInStruct_0((byte) 0),
    PacketPlayInStruct_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getTileEntityStructureUpdateType(), ClassAccessor.get().getBlockPropertyStructureModeEnum(), String.class, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getBaseBlockPositionClass(), ClassAccessor.get().getBlockMirrorEnum(), ClassAccessor.get().getBlockRotationEnum(), String.class, boolean.class, boolean.class, boolean.class, float.class, long.class),
    PacketPlayInUseEntity_0((byte) 0),
    PacketPlayInUseEntity_1((byte) 1, ClassAccessor.get().getMinecraftEntityClass()),
    PacketPlayInUseEntity_2((byte) 2, int.class, boolean.class, ClassAccessor.get().getEntityUseActionEnum()),
    PacketPlayInClientCommand_1((byte) 1, ClassAccessor.get().getClientCommandEnum()),
    PacketPlayInCloseWindow_1((byte) 1, int.class),
    PacketPlayInCustomPayload_0((byte) 0),
    PacketPlayInCustomPayload_1((byte) 1, ClassAccessor.get().getMinecraftKeyClass(), ClassAccessor.get().getPacketDataSerializerClass()),
    PacketPlayInDifficultyChange_1((byte) 1, ClassAccessor.get().getDifficultyEnum()),
    PacketPlayInDifficultyLock_0((byte) 0),
    PacketPlayInDifficultyLock_1((byte) 1, boolean.class),
    PacketPlayInEnchantItem_0((byte) 0),
    PacketPlayInEnchantItem_1((byte) 1, int.class, int.class),
    PacketPlayInEntityNBTQuery_0((byte) 0),
    PacketPlayInJigsawGenerate_0((byte) 0),
    PacketPlayInJigsawGenerate_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), int.class, boolean.class),
    PacketPlayInEntityNBTQuery_1((byte) 1, int.class, int.class),
    PacketPlayInLook_1((byte) 1, float.class, float.class, boolean.class),
    PacketPlayInPosition_1((byte) 1, double.class, double.class, double.class, boolean.class),
    PacketPlayInSetCommandBlock_0((byte) 0),
    PacketPlayInSetCommandBlock_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), String.class, ClassAccessor.get().getTileEntityCommandTypeEnum(), boolean.class, boolean.class, boolean.class),
    PacketPlayInHeldItemSlot_0((byte) 0),
    PacketPlayInHeldItemSlot_1((byte) 1, int.class),
    PacketPlayInPositionLook_1((byte) 1, double.class, double.class, double.class, float.class, float.class, boolean.class),
    PacketPlayInFlying_0((byte) 0),
    PacketPlayInFlying_1((byte) 1, double.class, double.class, double.class, float.class, float.class, boolean.class, boolean.class, boolean.class),
    ClientboundCustomChatCompletionsPacket_1((byte) 1, ClassAccessor.get().getChatCompletionsActionEnum(), List.class),
    PacketPlayInTabComplete_0((byte) 0),
    ClientboundDamageEventPacket_1((byte) 1, int.class, int.class, int.class, int.class, Optional.class),
    ClientboundDeleteChatPacket_1((byte) 1, ClassAccessor.get().getMessageSignatureClass()),
    ClientboundDeleteChatPacket_2((byte) 2, ClassAccessor.get().getMessageSignatureStorageClass()),
    ClientboundDisguisedChatPacket_1((byte) 1, ChatComponent.clazz(), ClassAccessor.get().getChatBoundNetworkClass()),
    PacketPlayInKeepAlive_0((byte) 0),
    PacketPlayInKeepAlive_1((byte) 1, long.class),
    ClientboundHurtAnimationPacket_1((byte) 1, int.class, float.class),
    PacketPlayInPickItem_0((byte) 0),
    PacketPlayInPickItem_1((byte) 1, int.class),
    PacketPlayInResourcePackStatus_0((byte) 0),
    PacketPlayInResourcePackStatus_1((byte) 1, ClassAccessor.get().getResourcePackStatusEnum()),
    PacketPlayInSetCommandMinecart_0((byte) 0),
    PacketPlayInSetCommandMinecart_1((byte) 1, int.class, String.class, boolean.class),
    PacketPlayInRecipeSettings_0((byte) 0),
    PacketPlayInRecipeSettings_1((byte) 1, ClassAccessor.get().getRecipeBookTypeEnum(), boolean.class, boolean.class),
    PacketPlayOutBlockAction_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getMinecraftBlockClass(), int.class, int.class),
    PacketPlayInSettings_0((byte) 0),
    PacketPlayInSettings_1((byte) 1, String.class, int.class, ClassAccessor.get().getChatVisibilityEnum(), boolean.class, int.class, ClassAccessor.get().getMainHandEnum(), boolean.class),
    PacketPlayInSettings_2((byte) 2, String.class, int.class, ClassAccessor.get().getChatVisibilityEnum(), boolean.class, int.class, ClassAccessor.get().getMainHandEnum(), boolean.class, boolean.class),
    PacketPlayInItemName_1((byte) 1, String.class),
    PacketPlayInTabComplete_1((byte) 1, String.class),
    PacketPlayInTabComplete_2((byte) 2, int.class, String.class),
    PacketPlayInTeleportAccept_0((byte) 0),
    PacketPlayInTeleportAccept_1((byte) 1, int.class),
    PacketPlayInTrSel_0((byte) 0),
    PacketPlayInTrSel_1((byte) 1, int.class),
    PacketPlayOutCommands_1((byte) 1, ClassAccessor.get().getRootCommandNodeClass()),
    PacketPlayOutBed_1((byte) 1, ClassAccessor.get().getEntityHumanClass(), ClassAccessor.get().getBlockPositionClass()),
    PacketPlayOutBlockBreakAnimation_1((byte) 1, int.class, ClassAccessor.get().getBlockPositionClass(), int.class),
    PacketPlayOutBlockBreak_1((byte) 1, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getIBlockDataClass(), ClassAccessor.get().getPlayerDigTypeEnum(), boolean.class, String.class),
    PacketPlayOutBlockChange_1((byte) 1, ClassAccessor.get().getMinecraftWorldClass(), ClassAccessor.get().getBlockPositionClass()),
    PacketPlayOutBlockChange_2((byte) 2, ClassAccessor.get().getIBlockAccessClass(), ClassAccessor.get().getBlockPositionClass()),
    PacketPlayOutBlockChange_3((byte) 3, ClassAccessor.get().getBlockPositionClass(), ClassAccessor.get().getIBlockDataClass()),
    PacketPlayOutStopSound_1((byte) 1, ClassAccessor.get().getMinecraftKeyClass(), ClassAccessor.get().getSoundCategoryEnum()),
    PacketPlayOutCloseWindow_1((byte) 1, int.class),
    PacketPlayOutChat_1((byte) 1, ChatComponent.clazz(), int.class),
    PacketPlayOutChat_2((byte) 2, ChatComponent.clazz(), ClassAccessor.get().getChatMessageTypeEnum()),
    PacketPlayOutChat_3((byte) 3, ChatComponent.clazz(), ClassAccessor.get().getChatMessageTypeEnum(), UUID.class),
    PacketPlayOutPosition_1((byte) 1, double.class, double.class, double.class, float.class, float.class, Set.class),
    PacketPlayOutPosition_2((byte) 2, double.class, double.class, double.class, float.class, float.class, Set.class, int.class),
    PacketPlayOutPosition_3((byte) 3, double.class, double.class, double.class, float.class, float.class, Set.class, int.class, boolean.class),
    PacketPlayOutCamera_1((byte) 1, ClassAccessor.get().getMinecraftEntityClass()),
    PacketPlayOutBoss_1((byte) 1, ClassAccessor.get().getBossActionEnum(), ClassAccessor.get().getBossBattleClass()),
    PacketPlayOutBoss_2((byte) 2, UUID.class, ClassAccessor.get().getBossActionInterface()),
    PacketPlayOutAbilities_1((byte) 1, ClassAccessor.get().getPlayerAbilitiesClass()),
    PacketPlayOutCollect_1((byte) 1, int.class, int.class),
    PacketPlayOutCollect_2((byte) 2, int.class, int.class, int.class),
    PacketPlayOutExperience_1((byte) 1, float.class, int.class, int.class),
    PacketPlayOutHeldItemSlot_1((byte) 1, int.class),
    PacketPlayOutAutoRecipe_1((byte) 1, int.class, ClassAccessor.get().getIRecipeClass()),
    PacketPlayOutKeepAlive_1((byte) 1, int.class),
    PacketPlayOutKeepAlive_2((byte) 2, long.class),
    PacketPlayOutOpenWindowHorse_1((byte) 1, int.class, int.class, int.class),
    PacketPlayOutUnloadChunk_1((byte) 1, int.class, int.class),
    PacketPlayOutUpdateHealth_1((byte) 1, float.class, int.class, float.class),
    PacketPlayOutUpdateTime_1((byte) 1, long.class, long.class, boolean.class),
    PacketPlayOutViewCentre_1((byte) 1, int.class, int.class),
    PacketPlayOutViewDistance_1((byte) 1, int.class),
    PacketPlayOutWindowData_1((byte) 1, int.class, int.class, int.class),
    PacketPlayOutAdvancements_1((byte) 1, boolean.class, Collection.class, Set.class, Map.class),
    PacketPlayOutAnimation_1((byte) 1, ClassAccessor.get().getMinecraftEntityClass(), int.class),
    PacketPlayOutAttachEntity_1((byte) 1, int.class, ClassAccessor.get().getMinecraftEntityClass(), ClassAccessor.get().getMinecraftEntityClass()),
    PacketPlayOutAttachEntity_2((byte) 2, ClassAccessor.get().getMinecraftEntityClass(), ClassAccessor.get().getMinecraftEntityClass()),
    PacketPlayInWindowClick_0((byte) 0),
    PacketPlayInWindowClick_1((byte) 1, int.class, int.class, int.class, int.class, ClassAccessor.get().getInventoryClickTypeEnum(), ClassAccessor.get().getMinecraftItemStackClass(), FastUtilLegacyAdapter.Classes.Int2ObjectMap);



    private final Class<?>[] types;
    private final byte level;
    PacketLevel(byte level, Class<?>... classes) {
        this.level = level;
        this.types = classes;
    }

    public final byte getLevel() {
        return level;
    }

    public final Class<?>[] getTypes() {
        return types;
    }
}
