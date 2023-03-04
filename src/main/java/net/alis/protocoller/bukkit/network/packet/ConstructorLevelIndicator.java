package net.alis.protocoller.bukkit.network.packet;

import net.alis.protocoller.bukkit.data.ClassesContainer;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public enum ConstructorLevelIndicator {

    PacketStatusInPing_0((byte) 0),
    PacketStatusInPing_1((byte) 1, long.class),

    PacketStatusOutPong_1((byte) 1, long.class),

    PacketStatusOutServerInfo_1((byte) 1, ClassesContainer.INSTANCE.getServerPingClass()),
    PacketLoginInCustomPayload_0((byte) 0),

    PacketLoginInCustomPayload_1((byte) 1, int.class, ClassesContainer.INSTANCE.getPacketDataSerializerClass()),

    PacketLoginInEncryptionBegin_0((byte) 0),
    PacketLoginInEncryptionBegin_1((byte) 1, SecretKey.class, PublicKey.class, byte[].class),

    PacketLoginInStart_1((byte) 1, ClassesContainer.INSTANCE.getGameProfileClass()),
    PacketLoginInStart_2((byte) 2, String.class, Optional.class),

    PacketLoginOutCustomPayload_0((byte) 0),
    PacketLoginOutCustomPayload_1((byte) 1, int.class, ClassesContainer.INSTANCE.getMinecraftKeyClass(), ClassesContainer.INSTANCE.getPacketDataSerializerClass()),

    PacketLoginOutDisconnect_1((byte) 1, ClassesContainer.INSTANCE.getIChatBaseComponentClass()),

    PacketLoginOutEncryptionBegin_1((byte) 1, String.class, PublicKey.class, byte[].class),
    PacketLoginOutEncryptionBegin_2((byte) 2, String.class, byte[].class, byte[].class),

    PacketLoginOutSetCompression_1((byte) 1, int.class),

    PacketLoginOutSuccess_1((byte) 1, ClassesContainer.INSTANCE.getGameProfileClass()),

    PacketHandshakeInSetProtocol_0((byte) 0),
    PacketHandshakeInSetProtocol_1((byte) 1, String.class, int.class, ClassesContainer.INSTANCE.getProtocolEnum()),

    ClientboundAddVibrationSignalPacket_1((byte) 1, ClassesContainer.INSTANCE.getVibrationPathClass()),
    ClientboundClearTitlesPacket_1((byte) 1, boolean.class),
    ClientboundInitializeBorderPacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundPingPacket_1((byte) 1, int.class),
    ClientboundPlayerCombatEndPacket_1((byte) 1, int.class, int.class),
    ClientboundPlayerCombatKillPacket_1((byte) 1, int.class, int.class, ClassesContainer.INSTANCE.getIChatBaseComponentClass()),
    ClientboundSetActionBarTextPacket_1((byte) 1, ClassesContainer.INSTANCE.getIChatBaseComponentClass()),
    ClientboundSetBorderCenterPacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundSetBorderLerpSizePacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundSetBorderSizePacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDelayPacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundSetBorderWarningDistancePacket_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftWorldBorderClass()),
    ClientboundSetSubtitleTextPacket_1((byte) 1, ClassesContainer.INSTANCE.getIChatBaseComponentClass()),
    ClientboundSetTitleTextPacket_1((byte) 1, ClassesContainer.INSTANCE.getIChatBaseComponentClass()),
    ClientboundSetTitlesAnimationPacket_1((byte) 1, int.class, int.class, int.class),
    ClientboundBlockChangedAckPacket_1((byte) 1, int.class),
    ClientboundPlayerInfoRemovePacket_1((byte) 1, List.class),
    ClientboundSetSimulationDistancePacket_1((byte) 1, int.class),
    ServerboundChatAckPacket_1((byte) 1, int.class),
    ServerboundPongPacket_1((byte) 1, int.class),

    PacketPlayInAbilities_1((byte) 1, ClassesContainer.INSTANCE.getPlayerAbilitiesClass()),
    PacketPlayInBoatMove_1((byte) 1, boolean.class, boolean.class),
    PacketPlayInSpectate_1((byte) 1, UUID.class),
    PacketPlayInEntityAction_0((byte) 0),
    PacketPlayInEntityAction_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftEntityClass(), ClassesContainer.INSTANCE.getPlayerActionEnum(), int.class),
    PacketPlayInSteerVehicle_0((byte) 0),
    PacketPlayInSteerVehicle_1((byte) 1, float.class, float.class, boolean.class, boolean.class),
    PacketPlayInTileNBTQuery_0((byte) 0),
    PacketPlayInTileNBTQuery_1((byte) 1, int.class, ClassesContainer.INSTANCE.getBlockPositionClass()),
    PacketPlayInUpdateSign_0((byte) 0),
    PacketPlayInUpdateSign_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), String.class, String.class, String.class, String.class),
    PacketPlayInArmAnimation_0((byte) 0),
    PacketPlayInArmAnimation_1((byte) 1, ClassesContainer.INSTANCE.getHandEnum()),
    PacketPlayInAdvancements_0((byte) 0),
    PacketPlayInAdvancements_1((byte) 1, ClassesContainer.INSTANCE.getAdvancementsStatusEnum(), ClassesContainer.INSTANCE.getMinecraftKeyClass()),
    PacketPlayInAutoRecipe_0((byte) 0),
    PacketPlayInAutoRecipe_1((byte) 1, int.class, ClassesContainer.INSTANCE.getIRecipeClass(), boolean.class),
    PacketPlayInBEdit_0((byte) 0),
    PacketPlayInBEdit_1((byte) 1, int.class, List.class, Optional.class),
    PacketPlayInBeacon_0((byte) 0),
    PacketPlayInBeacon_1((byte) 1, int.class, int.class),
    PacketPlayInBeacon_2((byte) 2, Optional.class, Optional.class),
    PacketPlayInBlockDig_0((byte) 0),
    PacketPlayInBlockDig_1((byte) 1, ClassesContainer.INSTANCE.getPlayerDigTypeEnum(), ClassesContainer.INSTANCE.getBlockPositionClass(), ClassesContainer.INSTANCE.getDirectionEnum()),
    PacketPlayInBlockDig_2((byte) 2, ClassesContainer.INSTANCE.getPlayerDigTypeEnum(), ClassesContainer.INSTANCE.getBlockPositionClass(), ClassesContainer.INSTANCE.getDirectionEnum(), int.class),
    PacketPlayInBlockPlace_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), int.class, float.class, float.class, float.class),
    PacketPlayInBlockPlace_2((byte) 2, ClassesContainer.INSTANCE.getHandEnum()),
    PacketPlayInChat_1((byte) 1, String.class),
    PacketPlayInUseItem_0((byte) 0),
    PacketPlayInUseItem_1((byte) 1, ClassesContainer.INSTANCE.getHandEnum(), ClassesContainer.INSTANCE.getMovingObjectPositionBlockClass()),
    PacketPlayInUseItem_2((byte) 2, ClassesContainer.INSTANCE.getHandEnum(), ClassesContainer.INSTANCE.getMovingObjectPositionBlockClass(), int.class),
    PacketPlayInSetCreativeSlot_0((byte) 0),
    PacketPlayInSetCreativeSlot_1((byte) 1, int.class, ClassesContainer.INSTANCE.getMinecraftItemStackClass()),
    PacketPlayInVehicleMove_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftEntityClass()),
    PacketPlayInSetJigsaw_0((byte) 0),
    PacketPlayInSetJigsaw_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), ClassesContainer.INSTANCE.getMinecraftKeyClass(), ClassesContainer.INSTANCE.getMinecraftKeyClass(), ClassesContainer.INSTANCE.getMinecraftKeyClass(), String.class, ClassesContainer.INSTANCE.getTileEntityJigsawJointypeEnum()),
    PacketPlayInStruct_0((byte) 0),
    PacketPlayInStruct_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), ClassesContainer.INSTANCE.getTileEntityStructureUpdateType(), ClassesContainer.INSTANCE.getBlockPropertyStructureModeEnum(), String.class, ClassesContainer.INSTANCE.getBlockPositionClass(), ClassesContainer.INSTANCE.getBaseBlockPositionClass(), ClassesContainer.INSTANCE.getBlockMirrorEnum(), ClassesContainer.INSTANCE.getBlockRotationEnum(), String.class, boolean.class, boolean.class, boolean.class, float.class, long.class),
    PacketPlayInUseEntity_0((byte) 0),
    PacketPlayInUseEntity_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftEntityClass()),
    PacketPlayInUseEntity_2((byte) 2, int.class, boolean.class, ClassesContainer.INSTANCE.getEntityUseActionEnum()),
    PacketPlayInClientCommand_1((byte) 1, ClassesContainer.INSTANCE.getClientCommandEnum()),
    PacketPlayInCloseWindow_1((byte) 1, int.class),
    PacketPlayInCustomPayload_0((byte) 0),
    PacketPlayInCustomPayload_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftKeyClass(), ClassesContainer.INSTANCE.getPacketDataSerializerClass()),
    PacketPlayInDifficultyChange_1((byte) 1, ClassesContainer.INSTANCE.getDifficultyEnum()),
    PacketPlayInDifficultyLock_0((byte) 0),
    PacketPlayInDifficultyLock_1((byte) 1, boolean.class),
    PacketPlayInEnchantItem_0((byte) 0),
    PacketPlayInEnchantItem_1((byte) 1, int.class, int.class),
    PacketPlayInEntityNBTQuery_0((byte) 0),
    PacketPlayInJigsawGenerate_0((byte) 0),
    PacketPlayInJigsawGenerate_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), int.class, boolean.class),
    PacketPlayInEntityNBTQuery_1((byte) 1, int.class, int.class),
    PacketPlayInLook_1((byte) 1, float.class, float.class, boolean.class),
    PacketPlayInPosition_1((byte) 1, double.class, double.class, double.class, boolean.class),
    PacketPlayInSetCommandBlock_0((byte) 0),
    PacketPlayInSetCommandBlock_1((byte) 1, ClassesContainer.INSTANCE.getBlockPositionClass(), String.class, ClassesContainer.INSTANCE.getTileEntityCommandTypeEnum(), boolean.class, boolean.class, boolean.class),
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
    PacketPlayInResourcePackStatus_1((byte) 1, ClassesContainer.INSTANCE.getResourcePackStatusEnum()),
    PacketPlayInSetCommandMinecart_0((byte) 0),
    PacketPlayInSetCommandMinecart_1((byte) 1, int.class, String.class, boolean.class),
    PacketPlayInSettings_0((byte) 0),
    PacketPlayInSettings_1((byte) 1, String.class, int.class, ClassesContainer.INSTANCE.getChatVisibilityEnum(), boolean.class, int.class, ClassesContainer.INSTANCE.getMainHandEnum(), boolean.class),
    PacketPlayInSettings_2((byte) 2, String.class, int.class, ClassesContainer.INSTANCE.getChatVisibilityEnum(), boolean.class, int.class, ClassesContainer.INSTANCE.getMainHandEnum(), boolean.class, boolean.class),
    PacketPlayInItemName_1((byte) 1, String.class),
    PacketPlayInTabComplete_1((byte) 1, String.class),
    PacketPlayInTabComplete_2((byte) 2, int.class, String.class),
    PacketPlayInTeleportAccept_0((byte) 0),
    PacketPlayInTeleportAccept_1((byte) 1, int.class),
    PacketPlayInTrSel_0((byte) 0),
    PacketPlayInTrSel_1((byte) 1, int.class),
    PacketPlayOutStopSound_1((byte) 1, ClassesContainer.INSTANCE.getMinecraftKeyClass(), ClassesContainer.INSTANCE.getSoundCategoryEnum()),
    PacketPlayOutCloseWindow_1((byte) 1, int.class),
    PacketPlayOutPosition_1((byte) 1, double.class, double.class, double.class, float.class, float.class, Set.class),
    PacketPlayOutPosition_2((byte) 2, double.class, double.class, double.class, float.class, float.class, Set.class, int.class),
    PacketPlayOutPosition_3((byte) 3, double.class, double.class, double.class, float.class, float.class, Set.class, int.class, boolean.class),

    PacketPlayOutCollect_1((byte) 1, int.class, int.class),
    PacketPlayOutCollect_2((byte) 2, int.class, int.class, int.class),
    PacketPlayOutExperience_1((byte) 1, float.class, int.class, int.class),
    PacketPlayOutHeldItemSlot_1((byte) 1, int.class),
    PacketPlayOutKeepAlive_1((byte) 1, int.class),
    PacketPlayOutKeepAlive_2((byte) 2, long.class),
    PacketPlayOutOpenWindowHorse_1((byte) 1, int.class, int.class, int.class),
    PacketPlayOutUnloadChunk_1((byte) 1, int.class, int.class),
    PacketPlayOutUpdateHealth_1((byte) 1, float.class, int.class, float.class),
    PacketPlayOutUpdateTime_1((byte) 1, long.class, long.class, boolean.class),
    PacketPlayOutViewCentre_1((byte) 1, int.class, int.class),
    PacketPlayOutViewDistance_1((byte) 1, int.class),
    PacketPlayOutWindowData_1((byte) 1, int.class, int.class, int.class);



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
