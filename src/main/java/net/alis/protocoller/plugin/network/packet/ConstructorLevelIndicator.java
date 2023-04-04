package net.alis.protocoller.plugin.network.packet;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;

public enum ConstructorLevelIndicator {

    PacketStatusInPing_0((byte) 0),
    PacketStatusInPing_1((byte) 1, long.class),

    PacketStatusOutPong_1((byte) 1, long.class),

    PacketStatusOutServerInfo_1((byte) 1, ClassesContainer.get().getServerPingClass()),
    PacketLoginInCustomPayload_0((byte) 0),

    PacketLoginInCustomPayload_1((byte) 1, int.class, ClassesContainer.get().getPacketDataSerializerClass()),

    PacketLoginInEncryptionBegin_0((byte) 0),
    PacketLoginInEncryptionBegin_1((byte) 1, SecretKey.class, PublicKey.class, byte[].class),

    PacketLoginInStart_1((byte) 1, ClassesContainer.get().getGameProfileClass()),
    PacketLoginInStart_2((byte) 2, String.class, Optional.class),

    PacketLoginOutCustomPayload_0((byte) 0),
    PacketLoginOutCustomPayload_1((byte) 1, int.class, ClassesContainer.get().getMinecraftKeyClass(), ClassesContainer.get().getPacketDataSerializerClass()),

    PacketLoginOutDisconnect_1((byte) 1, ClassesContainer.get().getIChatBaseComponentClass()),

    PacketLoginOutEncryptionBegin_1((byte) 1, String.class, PublicKey.class, byte[].class),
    PacketLoginOutEncryptionBegin_2((byte) 2, String.class, byte[].class, byte[].class),

    PacketLoginOutSetCompression_1((byte) 1, int.class),

    PacketLoginOutSuccess_1((byte) 1, ClassesContainer.get().getGameProfileClass()),

    PacketHandshakeInSetProtocol_0((byte) 0),
    PacketHandshakeInSetProtocol_1((byte) 1, String.class, int.class, ClassesContainer.get().getProtocolEnum()),

    ClientboundAddVibrationSignalPacket_1((byte) 1, ClassesContainer.get().getVibrationPathClass()),
    ClientboundClearTitlesPacket_1((byte) 1, boolean.class),
    ClientboundInitializeBorderPacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundPingPacket_1((byte) 1, int.class),
    ClientboundPlayerCombatEndPacket_1((byte) 1, int.class, int.class),
    ClientboundPlayerCombatKillPacket_1((byte) 1, int.class, int.class, ClassesContainer.get().getIChatBaseComponentClass()),
    ClientboundSetActionBarTextPacket_1((byte) 1, ClassesContainer.get().getIChatBaseComponentClass()),
    ClientboundSetBorderCenterPacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderLerpSizePacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderSizePacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDelayPacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDistancePacket_1((byte) 1, ClassesContainer.get().getMinecraftWorldBorderClass()),
    ClientboundSetSubtitleTextPacket_1((byte) 1, ClassesContainer.get().getIChatBaseComponentClass()),
    ClientboundSetTitleTextPacket_1((byte) 1, ClassesContainer.get().getIChatBaseComponentClass()),
    ClientboundSetTitlesAnimationPacket_1((byte) 1, int.class, int.class, int.class),
    ClientboundBlockChangedAckPacket_1((byte) 1, int.class),
    ClientboundPlayerInfoRemovePacket_1((byte) 1, List.class),
    ClientboundSetSimulationDistancePacket_1((byte) 1, int.class),
    ServerboundChatAckPacket_1((byte) 1, int.class),
    ServerboundPongPacket_1((byte) 1, int.class),
    ClientboundLevelChunkPacketData_1((byte) 1, ClassesContainer.get().getMinecraftChunkClass()),
    ClientboundLevelChunkWithLightPacket_1((byte) 1, ClassesContainer.get().getMinecraftChunkClass(), ClassesContainer.get().getLightEngineClass(), BitSet.class, BitSet.class, boolean.class),

    PacketPlayInAbilities_1((byte) 1, ClassesContainer.get().getPlayerAbilitiesClass()),
    PacketPlayInBoatMove_1((byte) 1, boolean.class, boolean.class),
    PacketPlayInSpectate_1((byte) 1, UUID.class),
    PacketPlayInEntityAction_0((byte) 0),
    PacketPlayInEntityAction_1((byte) 1, ClassesContainer.get().getMinecraftEntityClass(), ClassesContainer.get().getPlayerActionEnum(), int.class),
    PacketPlayInSteerVehicle_0((byte) 0),
    PacketPlayInSteerVehicle_1((byte) 1, float.class, float.class, boolean.class, boolean.class),
    PacketPlayInTileNBTQuery_0((byte) 0),
    PacketPlayInTileNBTQuery_1((byte) 1, int.class, ClassesContainer.get().getBlockPositionClass()),
    PacketPlayInUpdateSign_0((byte) 0),
    PacketPlayInUpdateSign_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), String.class, String.class, String.class, String.class),
    PacketPlayInArmAnimation_0((byte) 0),
    PacketPlayInArmAnimation_1((byte) 1, ClassesContainer.get().getHandEnum()),
    PacketPlayInAdvancements_0((byte) 0),
    PacketPlayInAdvancements_1((byte) 1, ClassesContainer.get().getAdvancementsStatusEnum(), ClassesContainer.get().getMinecraftKeyClass()),
    PacketPlayInAutoRecipe_0((byte) 0),
    PacketPlayInAutoRecipe_1((byte) 1, int.class, ClassesContainer.get().getIRecipeClass(), boolean.class),
    PacketPlayInBEdit_0((byte) 0),
    PacketPlayInBEdit_1((byte) 1, int.class, List.class, Optional.class),
    PacketPlayInBeacon_0((byte) 0),
    PacketPlayInBeacon_1((byte) 1, int.class, int.class),
    PacketPlayInBeacon_2((byte) 2, Optional.class, Optional.class),
    PacketPlayInBlockDig_0((byte) 0),
    PacketPlayInBlockDig_1((byte) 1, ClassesContainer.get().getPlayerDigTypeEnum(), ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getDirectionEnum()),
    PacketPlayInBlockDig_2((byte) 2, ClassesContainer.get().getPlayerDigTypeEnum(), ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getDirectionEnum(), int.class),
    PacketPlayInBlockPlace_1((byte) 1, ClassesContainer.get().getMinecraftItemStackClass()),
    PacketPlayInBlockPlace_2((byte) 2, ClassesContainer.get().getHandEnum()),
    PacketPlayInBlockPlace_3((byte) 3, ClassesContainer.get().getHandEnum(), int.class),
    PacketPlayInRecipeDisplayed_1((byte) 1, ClassesContainer.get().getIRecipeClass()),
    PacketPlayInChat_1((byte) 1, String.class),
    PacketPlayInChat_2((byte) 2, String.class, ClassesContainer.get().getMessageSignatureClass(), boolean.class),
    PacketPlayInChat_3((byte) 3, String.class, Instant.class, long.class, ClassesContainer.get().getMessageSignatureClass(), boolean.class, ClassesContainer.get().getLastSeenMessagesUpdaterClass()),
    PacketPlayInChat_4((byte) 4, String.class, Instant.class, long.class, ClassesContainer.get().getMessageSignatureClass(), ClassesContainer.get().getLastSeenMessagesUpdaterClass()),
    PacketPlayInUseItem_0((byte) 0),
    PacketPlayInUseItem_1((byte) 1, ClassesContainer.get().getHandEnum(), ClassesContainer.get().getMovingObjectPositionBlockClass()),
    PacketPlayInUseItem_2((byte) 2, ClassesContainer.get().getHandEnum(), ClassesContainer.get().getMovingObjectPositionBlockClass(), int.class),
    PacketPlayInSetCreativeSlot_0((byte) 0),
    PacketPlayInSetCreativeSlot_1((byte) 1, int.class, ClassesContainer.get().getMinecraftItemStackClass()),
    PacketPlayInVehicleMove_1((byte) 1, ClassesContainer.get().getMinecraftEntityClass()),
    PacketPlayInSetJigsaw_0((byte) 0),
    PacketPlayInSetJigsaw_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getMinecraftKeyClass(), ClassesContainer.get().getMinecraftKeyClass(), ClassesContainer.get().getMinecraftKeyClass(), String.class, ClassesContainer.get().getTileEntityJigsawJointypeEnum()),
    PacketPlayInStruct_0((byte) 0),
    PacketPlayInStruct_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getTileEntityStructureUpdateType(), ClassesContainer.get().getBlockPropertyStructureModeEnum(), String.class, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getBaseBlockPositionClass(), ClassesContainer.get().getBlockMirrorEnum(), ClassesContainer.get().getBlockRotationEnum(), String.class, boolean.class, boolean.class, boolean.class, float.class, long.class),
    PacketPlayInUseEntity_0((byte) 0),
    PacketPlayInUseEntity_1((byte) 1, ClassesContainer.get().getMinecraftEntityClass()),
    PacketPlayInUseEntity_2((byte) 2, int.class, boolean.class, ClassesContainer.get().getEntityUseActionEnum()),
    PacketPlayInClientCommand_1((byte) 1, ClassesContainer.get().getClientCommandEnum()),
    PacketPlayInCloseWindow_1((byte) 1, int.class),
    PacketPlayInCustomPayload_0((byte) 0),
    PacketPlayInCustomPayload_1((byte) 1, ClassesContainer.get().getMinecraftKeyClass(), ClassesContainer.get().getPacketDataSerializerClass()),
    PacketPlayInDifficultyChange_1((byte) 1, ClassesContainer.get().getDifficultyEnum()),
    PacketPlayInDifficultyLock_0((byte) 0),
    PacketPlayInDifficultyLock_1((byte) 1, boolean.class),
    PacketPlayInEnchantItem_0((byte) 0),
    PacketPlayInEnchantItem_1((byte) 1, int.class, int.class),
    PacketPlayInEntityNBTQuery_0((byte) 0),
    PacketPlayInJigsawGenerate_0((byte) 0),
    PacketPlayInJigsawGenerate_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), int.class, boolean.class),
    PacketPlayInEntityNBTQuery_1((byte) 1, int.class, int.class),
    PacketPlayInLook_1((byte) 1, float.class, float.class, boolean.class),
    PacketPlayInPosition_1((byte) 1, double.class, double.class, double.class, boolean.class),
    PacketPlayInSetCommandBlock_0((byte) 0),
    PacketPlayInSetCommandBlock_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), String.class, ClassesContainer.get().getTileEntityCommandTypeEnum(), boolean.class, boolean.class, boolean.class),
    PacketPlayInHeldItemSlot_0((byte) 0),
    PacketPlayInHeldItemSlot_1((byte) 1, int.class),
    PacketPlayInPositionLook_1((byte) 1, double.class, double.class, double.class, float.class, float.class, boolean.class),
    PacketPlayInFlying_0((byte) 0),
    PacketPlayInFlying_1((byte) 1, double.class, double.class, double.class, float.class, float.class, boolean.class, boolean.class, boolean.class),
    PacketPlayInTabComplete_0((byte) 0),
    PacketPlayInKeepAlive_0((byte) 0),
    PacketPlayInKeepAlive_1((byte) 1, long.class),
    PacketPlayInPickItem_0((byte) 0),
    PacketPlayInPickItem_1((byte) 1, int.class),
    PacketPlayInResourcePackStatus_0((byte) 0),
    PacketPlayInResourcePackStatus_1((byte) 1, ClassesContainer.get().getResourcePackStatusEnum()),
    PacketPlayInSetCommandMinecart_0((byte) 0),
    PacketPlayInSetCommandMinecart_1((byte) 1, int.class, String.class, boolean.class),
    PacketPlayInRecipeSettings_0((byte) 0),
    PacketPlayInRecipeSettings_1((byte) 1, ClassesContainer.get().getRecipeBookTypeEnum(), boolean.class, boolean.class),
    PacketPlayOutBlockAction_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getMinecraftBlockClass(), int.class, int.class),
    PacketPlayInSettings_0((byte) 0),
    PacketPlayInSettings_1((byte) 1, String.class, int.class, ClassesContainer.get().getChatVisibilityEnum(), boolean.class, int.class, ClassesContainer.get().getMainHandEnum(), boolean.class),
    PacketPlayInSettings_2((byte) 2, String.class, int.class, ClassesContainer.get().getChatVisibilityEnum(), boolean.class, int.class, ClassesContainer.get().getMainHandEnum(), boolean.class, boolean.class),
    PacketPlayInItemName_1((byte) 1, String.class),
    PacketPlayInTabComplete_1((byte) 1, String.class),
    PacketPlayInTabComplete_2((byte) 2, int.class, String.class),
    PacketPlayInTeleportAccept_0((byte) 0),
    PacketPlayInTeleportAccept_1((byte) 1, int.class),
    PacketPlayInTrSel_0((byte) 0),
    PacketPlayInTrSel_1((byte) 1, int.class),
    PacketPlayOutCommands_1((byte) 1, ClassesContainer.get().getRootCommandNodeClass()),
    PacketPlayOutBed_1((byte) 1, ClassesContainer.get().getEntityHumanClass(), ClassesContainer.get().getBlockPositionClass()),
    PacketPlayOutBlockBreakAnimation_1((byte) 1, int.class, ClassesContainer.get().getBlockPositionClass(), int.class),
    PacketPlayOutBlockBreak_1((byte) 1, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getIBlockDataClass(), ClassesContainer.get().getPlayerDigTypeEnum(), boolean.class, String.class),
    PacketPlayOutBlockChange_1((byte) 1, ClassesContainer.get().getMinecraftWorldClass(), ClassesContainer.get().getBlockPositionClass()),
    PacketPlayOutBlockChange_2((byte) 2, ClassesContainer.get().getIBlockAccessClass(), ClassesContainer.get().getBlockPositionClass()),
    PacketPlayOutBlockChange_3((byte) 3, ClassesContainer.get().getBlockPositionClass(), ClassesContainer.get().getIBlockDataClass()),
    PacketPlayOutStopSound_1((byte) 1, ClassesContainer.get().getMinecraftKeyClass(), ClassesContainer.get().getSoundCategoryEnum()),
    PacketPlayOutCloseWindow_1((byte) 1, int.class),
    PacketPlayOutChat_1((byte) 1, ClassesContainer.get().getIChatBaseComponentClass(), int.class),
    PacketPlayOutChat_2((byte) 2, ClassesContainer.get().getIChatBaseComponentClass(), ClassesContainer.get().getChatMessageTypeEnum()),
    PacketPlayOutChat_3((byte) 3, ClassesContainer.get().getIChatBaseComponentClass(), ClassesContainer.get().getChatMessageTypeEnum(), UUID.class),
    PacketPlayOutPosition_1((byte) 1, double.class, double.class, double.class, float.class, float.class, Set.class),
    PacketPlayOutPosition_2((byte) 2, double.class, double.class, double.class, float.class, float.class, Set.class, int.class),
    PacketPlayOutPosition_3((byte) 3, double.class, double.class, double.class, float.class, float.class, Set.class, int.class, boolean.class),
    PacketPlayOutCamera_1((byte) 1, ClassesContainer.get().getMinecraftEntityClass()),
    PacketPlayOutBoss_1((byte) 1, ClassesContainer.get().getBossActionEnum(), ClassesContainer.get().getBossBattleClass()),
    PacketPlayOutBoss_2((byte) 2, UUID.class, ClassesContainer.get().getBossActionInterface()),
    PacketPlayOutAbilities_1((byte) 1, ClassesContainer.get().getPlayerAbilitiesClass()),
    PacketPlayOutCollect_1((byte) 1, int.class, int.class),
    PacketPlayOutCollect_2((byte) 2, int.class, int.class, int.class),
    PacketPlayOutExperience_1((byte) 1, float.class, int.class, int.class),
    PacketPlayOutHeldItemSlot_1((byte) 1, int.class),
    PacketPlayOutAutoRecipe_1((byte) 1, int.class, ClassesContainer.get().getIRecipeClass()),
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
    PacketPlayOutAnimation_1((byte) 1, ClassesContainer.get().getMinecraftEntityClass(), int.class),
    PacketPlayOutAttachEntity_1((byte) 1, int.class, ClassesContainer.get().getMinecraftEntityClass(), ClassesContainer.get().getMinecraftEntityClass()),
    PacketPlayOutAttachEntity_2((byte) 2, ClassesContainer.get().getMinecraftEntityClass(), ClassesContainer.get().getMinecraftEntityClass()),
    PacketPlayInWindowClick_0((byte) 0),
    PacketPlayInWindowClick_1((byte) 1, int.class, int.class, int.class, int.class, ClassesContainer.get().getInventoryClickTypeEnum(), ClassesContainer.get().getMinecraftItemStackClass(), FastUtilLegacyAdapter.Classes.Int2ObjectMap);



    private final Class<?>[] types;
    private final byte level;
    ConstructorLevelIndicator(byte level, Class<?>... classes) {
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
