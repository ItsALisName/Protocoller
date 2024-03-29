package net.alis.protocoller.plugin.memory;

import lombok.Getter;
import lombok.Setter;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.util.reflection.Reflect;

import java.lang.reflect.Array;

public class ClassAccessor {

    private @Setter @Getter Class<?>
        chunkBiomeDataClass, chatBoundNetworkClass, chatHexColorClass, clickActionEnum, chatClickableClass, chatHoverableClass, chatHoverActionEnumClass,
        iChatBaseComponentClass, suggestionClass, suggestionsClass, stringRangeClass, stringReaderClass, remoteConnectionThreadClass,
        chatSerializerClass, argumentTypeClass, argumentBuilderClass, literalArgumentBuilderClass, requiredArgumentBuilderClass,
        commandContextClass, commandContextBuilderClass, parsedArgumentClass, parsedCommandNodeClass, suggestionContextClass,
        integerSuggestionClass, suggestionProviderClass, suggestionsBuilderClass, argumentCommandNodeClass, commandNodeClass,
        literalCommandNodeClass, rootCommandNodeClass, ambiguityConsumerClass, commandClass, commandDispatcherClass,literalMessageClass,
        parseResultsClass, redirectModifierClass, resultConsumerClass, singleRedirectModifierClass, kyoriComponentClass,
        kyoriGsonComponentSerializerClass, chatMessageTypeEnum, playerConnectionClass, networkManagerClass, minecraftServerClass,
        serverConnectionClass, craftServerClass, dedicatedServerClass, packetDataSerializerClass, gameProfileClass, propertyClass,
        propertyMapClass, minecraftKeyClass, craftWorldClass, minecraftWorldClass, minecraftWorldServerClass, craftWorldBorderClass,
        minecraftWorldBorderClass, entityPlayerClass, serverPingClass, vibrationPathClass, playerAbilitiesClass, iRecipeClass, directionEnum,
        playerDigTypeEnum, blockPositionClass, minecraftItemStackClass, handEnum, clientCommandEnum, difficultyEnum, playerActionEnum,
        minecraftEntityClass, protocolEnum, advancementsStatusEnum, craftChatMessageClass, serverDataClass, serverPingPlayerSampleClass,
        iChatMutableComponent, soundCategoryEnum, attributeBaseClass, attributeModifierClass, operationClass, factorCalculationDataClass,
        mobEffectClass, mobEffectListClass, mobEffectInfoEnum, playerTeleportFlagsEnum, craftItemStackClass, craftPlayerClass, resourcePackStatusEnum,
        chatVisibilityEnum, mainHandEnum, nbtTagCompoundClass, vector3dClass, axisAlignedClass, craftEntityClass, tileEntityCommandTypeEnum,
        tileEntityJigsawJointypeEnum, tileEntityStructureUpdateType, blockPropertyStructureModeEnum, blockMirrorEnum, pointOEnum, pointSEnum, blockRotationEnum,
        baseBlockPositionClass, entityUseActionEnum, movingObjectPositionBlockClass, inventoryClickTypeEnum, craftAdvancementClass, minecraftAdvancementClass,
        advancementFrameTypeEnum, advancementDisplayClass, chatFormatEnum, craftAdvancementProgress, minecraftAdvancementProgress,
        advancementPlayerDataClass, attributeOperationEnum, resourceKeyClass, instantMobEffectClass, mobEffectAbsorptionClass, mobEffectAttackDamageClass,
        mobEffectHealthBoostClass, attributeRangedClass, recipeBookTypeEnum, minecraftBlockClass, craftBlockClass, craftMagicNumbersClass, iBlockDataClass,
        minecraftItemClass, entityTypesClass, entityHumanClass, craftChunkClass, minecraftChunkClass, iChunkAccessClass, iBlockAccessClass,
        bossActionEnum, bossActionInterface, barColorEnum, barStyleEnum, bossBattleClass, outCommandClassB, outCommandInterfaceE, outCommandClassA,
        messageSignatureClass, lastSeenMessagesClass, messageSignatureStorageClass, minecraftEncryptionSignatureDataClass, lastSeenMessagesCacheClass,
        lastSeenMessagesUpdaterClass, chunkCoordIntPairClass, tileEntityTypesClass, blockEntityInfoClass, nbtTagEndClass, nbtTagByteClass, nbtTagShortClass, nbtTagIntClass,
        nbtTagLongClass, nbtTagFloatClass, nbtTagDoubleClass, nbtTagByteArrayClass, nbtTagStringClass, nbtTagListClass, nbtTagIntArrayClass, nbtTagLongArrayClass,
        lightEngineClass, playerChatMessageClass, signedMessageLinkClass, chatMessageContentClass, signedMessageBodyClass, filterMaskTypeEnum, filterMaskClass,
        minecraftPacketClass, protocolDirectionEnum, remoteStatusReplyClass, remoteStatusListenerClass, remoteControlListenerClass, chatCompletionsActionEnum,
        chatModifierClass, chatSenderClass, signedMessageBodyPackedClass, playerInfoUpdateActionEnum, playerInfoUpdateEntryClass, remoteChatSessionDataClass,
        profilePublicKeyClass, profilePublicKeyDataClass, remoteChatSessionClass, gamemodeEnum, worldDataClass, worldDataMutable, worldDataServerClass, spigotTickLimiterClass,
        worldSettingsClass, chunkSectionClass, chunkProviderServer, iLightAccessClass, commandListenerWrapperClass, vector2FClass, iCommandListenerClass,
        outgoingChatMessageClass, outgoingChatMessageDisguisedClass, outgoingChatMessagePlayerClass, chatBoundClass, signedMessageHeaderClass, chatDecorationClass, chatDecorationParameterEnum,
        titleActionEnum, craftCommandMapClass, simpleHelpMapClass, customFunctionClass, cacheableFunctionClass, customFunctionEntryInterface, customFunctionCommandEntryClass, advancementRewardsClass,
        criterionClass, criterionInstanceClass, criterionProgressClass;
    ClassAccessor() {
        setCriterionProgressClass(Reflect.getNMSClass("CriterionProgress", "net.minecraft.advancements.CriterionProgress", true));
        setCriterionInstanceClass(Reflect.getNMSClass("CriterionInstance", "net.minecraft.advancements.CriterionInstance", true));
        setCriterionClass(Reflect.getNMSClass("Criterion", "net.minecraft.advancements.Criterion", true));
        setAdvancementRewardsClass(Reflect.getNMSClass("AdvancementRewards", "net.minecraft.advancements.AdvancementRewards", true));
        setCustomFunctionCommandEntryClass(Reflect.getNMSClass("CustomFunction$b", "net.minecraft.commands.CustomFunction$b", true));
        setCustomFunctionEntryInterface(Reflect.getNMSClass("CustomFunction$c", "net.minecraft.commands.CustomFunction$c", true));
        setCacheableFunctionClass(Reflect.getNMSClass("CustomFunction$a", "net.minecraft.commands.CustomFunction$a", true));
        setCustomFunctionClass(Reflect.getNMSClass("CustomFunction", "net.minecraft.commands.CustomFunction", true));
        setSimpleHelpMapClass(Reflect.getCraftBukkitClass("help.SimpleHelpMap", true));
        setCraftCommandMapClass(Reflect.getCraftBukkitClass("command.CraftCommandMap", true));
        setTitleActionEnum(Reflect.getLegacyNMSClass("EnumTitleAction", true));
        if(titleActionEnum == null) {
            setTitleActionEnum(Reflect.getLegacyNMSClass("PacketPlayOutTitle$EnumTitleAction", true));
        }
        setChatDecorationParameterEnum(Reflect.getNMSClass("ChatDecoration$a", "net.minecraft.network.chat.ChatDecoration$a", true));
        setChatDecorationClass(Reflect.getNMSClass("ChatDecoration", "net.minecraft.network.chat.ChatDecoration", true));
        setSignedMessageHeaderClass(Reflect.getNMSClass("SignedMessageHeader", "net.minecraft.network.chat.SignedMessageHeader", true));
        setChatBoundClass(Reflect.getNMSClass("ChatMessageType$a", "net.minecraft.network.chat.ChatMessageType$a", true));
        setOutgoingChatMessagePlayerClass(Reflect.getNMSClass("OutgoingPlayerChatMessage$b", "net.minecraft.network.chat.OutgoingPlayerChatMessage$b", true));
        setOutgoingChatMessageDisguisedClass(Reflect.getNMSClass("OutgoingPlayerChatMessage$a", "net.minecraft.network.chat.OutgoingPlayerChatMessage$a", true));
        setOutgoingChatMessageClass(Reflect.getNMSClass("OutgoingPlayerChatMessage", "net.minecraft.network.chat.OutgoingPlayerChatMessage", true));
        setICommandListenerClass(Reflect.getNMSClass("ICommandListener", "net.minecraft.commands.ICommandListener", true));
        setVector2FClass(Reflect.getNMSClass("Vec2F", "net.minecraft.world.phys.Vec2F", true));
        setCommandListenerWrapperClass(Reflect.getNMSClass("CommandListenerWrapper", "net.minecraft.commands.CommandListenerWrapper", true));
        setILightAccessClass(Reflect.getNMSClass("ILightAccess", "net.minecraft.world.level.chunk.ILightAccess", true));
        setChunkProviderServer(Reflect.getNMSClass("ChunkProviderServer", "net.minecraft.server.level.ChunkProviderServer", true));
        setChunkSectionClass(Reflect.getNMSClass("ChunkSection", "net.minecraft.world.level.chunk.ChunkSection", true));
        setWorldSettingsClass(Reflect.getNMSClass("WorldSettings", "net.minecraft.world.level.WorldSettings", true));
        setSpigotTickLimiterClass(Reflect.getClass("org.spigotmc.TickLimiter", true));
        setWorldDataClass(Reflect.getNMSClass("WorldData", "net.minecraft.world.level.storage.WorldData", true));
        setWorldDataMutable(Reflect.getNMSClass("WorldDataMutable", "net.minecraft.world.level.storage.WorldDataMutable", true));
        setWorldDataServerClass(Reflect.getNMSClass("WorldDataServer", "net.minecraft.world.level.storage.WorldDataServer", true));
        setGamemodeEnum(Reflect.getNMSClass("EnumGamemode", "net.minecraft.world.level.EnumGamemode", true));
        setRemoteChatSessionClass(Reflect.getNMSClass("RemoteChatSession", "net.minecraft.network.chat.RemoteChatSession", true));
        setProfilePublicKeyDataClass(Reflect.getNMSClass("ProfilePublicKey$a", "net.minecraft.world.entity.player.ProfilePublicKey$a", true));
        setProfilePublicKeyClass(Reflect.getNMSClass("ProfilePublicKey", "net.minecraft.world.entity.player.ProfilePublicKey", true));
        setRemoteChatSessionDataClass(Reflect.getNMSClass("RemoteChatSession$a", "net.minecraft.network.chat.RemoteChatSession$a", true));
        setPlayerInfoUpdateEntryClass(Reflect.getClass("net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$b", true));
        setPlayerInfoUpdateActionEnum(Reflect.getClass("net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$a", true));
        setSignedMessageBodyPackedClass(Reflect.getNMSClass("SignedMessageBody$a", "net.minecraft.network.chat.SignedMessageBody$a", true));
        setChatSenderClass(Reflect.getNMSClass("ChatSender", "net.minecraft.network.chat.ChatSender", true));
        setChatModifierClass(Reflect.getNMSClass("ChatModifier", "net.minecraft.network.chat.ChatModifier", true));
        setChatHoverActionEnumClass(Reflect.getNMSClass("ChatHoverable$EnumHoverAction", "net.minecraft.network.chat.ChatHoverable$EnumHoverAction", true));
        setChatHoverableClass(Reflect.getNMSClass("ChatHoverable", "net.minecraft.network.chat.ChatHoverable", true));
        setClickActionEnum(Reflect.getNMSClass("ChatClickable$EnumClickAction", "net.minecraft.network.chat.ChatClickable$EnumClickAction", true));
        setChatClickableClass(Reflect.getNMSClass("ChatClickable", "net.minecraft.network.chat.ChatClickable", true));
        setChatHexColorClass(Reflect.getNMSClass("ChatHexColor", "net.minecraft.network.chat.ChatHexColor", true));
        setChatBoundNetworkClass(Reflect.getNMSClass("ChatMessageType$b", "net.minecraft.network.chat.ChatMessageType$b", true));
        setChatCompletionsActionEnum(Reflect.getNMSClass("ClientboundCustomChatCompletionsPacket$Action", "net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket$Action", true));
        setChunkBiomeDataClass(Reflect.getNMSClass("ClientboundChunksBiomesPacket$a", "net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket$a", true));
        setRemoteControlListenerClass(Reflect.getNMSClass("RemoteControlListener", "net.minecraft.server.rcon.thread.RemoteControlListener", true));
        setRemoteStatusListenerClass(Reflect.getNMSClass("RemoteStatusListener", "net.minecraft.server.rcon.thread.RemoteStatusListener", true));
        setRemoteStatusReplyClass(Reflect.getNMSClass("RemoteStatusReply", "net.minecraft.server.rcon.RemoteStatusReply", true));
        setRemoteConnectionThreadClass(Reflect.getNMSClass("RemoteConnectionThread", "net.minecraft.server.rcon.thread.RemoteConnectionThread", true));
        setProtocolDirectionEnum(Reflect.getNMSClass("EnumProtocolDirection", "net.minecraft.network.protocol.EnumProtocolDirection", true));
        setMinecraftPacketClass(Reflect.getNMSClass("Packet", "net.minecraft.network.protocol.Packet", true));
        setFilterMaskClass(Reflect.getNMSClass("FilterMask", "net.minecraft.network.chat.FilterMask", true));
        setFilterMaskTypeEnum(Reflect.getNMSClass("FilterMask$a", "net.minecraft.network.chat.FilterMask$a", true));
        setSignedMessageBodyClass(Reflect.getNMSClass("SignedMessageBody", "net.minecraft.network.chat.SignedMessageBody", true));
        setChatMessageContentClass(Reflect.getNMSClass("ChatMessageContent", "net.minecraft.network.chat.ChatMessageContent", true));
        setSignedMessageLinkClass(Reflect.getNMSClass("SignedMessageLink", "net.minecraft.network.chat.SignedMessageLink", true));
        setPlayerChatMessageClass(Reflect.getNMSClass("PlayerChatMessage", "net.minecraft.network.chat.PlayerChatMessage", true));
        setLightEngineClass(Reflect.getNMSClass("LightEngine", "net.minecraft.world.level.lighting.LightEngine", true));
        setNbtTagEndClass(Reflect.getNMSClass("NBTTagEnd", "net.minecraft.nbt.NBTTagEnd", true));
        setNbtTagByteClass(Reflect.getNMSClass("NBTTagByte", "net.minecraft.nbt.NBTTagByte", true));
        setNbtTagShortClass(Reflect.getNMSClass("NBTTagShort", "net.minecraft.nbt.NBTTagShort", true));
        setNbtTagIntClass(Reflect.getNMSClass("NBTTagInt", "net.minecraft.nbt.NBTTagInt", true));
        setNbtTagLongClass(Reflect.getNMSClass("NBTTagLong", "net.minecraft.nbt.NBTTagLong", true));
        setNbtTagFloatClass(Reflect.getNMSClass("NBTTagFloat", "net.minecraft.nbt.NBTTagFloat", true));
        setNbtTagDoubleClass(Reflect.getNMSClass("NBTTagDouble", "net.minecraft.nbt.NBTTagDouble", true));
        setNbtTagByteArrayClass(Reflect.getNMSClass("NBTTagByteArray", "net.minecraft.nbt.NBTTagByteArray", true));
        setNbtTagStringClass(Reflect.getNMSClass("NBTTagString", "net.minecraft.nbt.NBTTagString", true));
        setNbtTagListClass(Reflect.getNMSClass("NBTTagList", "net.minecraft.nbt.NBTTagList", true));
        setNbtTagIntArrayClass(Reflect.getNMSClass("NBTTagIntArray", "net.minecraft.nbt.NBTTagIntArray", true));
        setNbtTagLongArrayClass(Reflect.getNMSClass("NBTTagLongArray", "net.minecraft.nbt.NBTTagLongArray", true));
        setBlockEntityInfoClass(Reflect.getClass("net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$a", true));
        setTileEntityTypesClass(Reflect.getNMSClass("TileEntityTypes", "net.minecraft.world.level.block.entity.TileEntityTypes", true));
        setChunkCoordIntPairClass(Reflect.getNMSClass("ChunkCoordIntPair", "net.minecraft.world.level.ChunkCoordIntPair", true));
        setLastSeenMessagesCacheClass(Reflect.getNMSClass("LastSeenMessages$a", "net.minecraft.network.chat.LastSeenMessages$a", true));
        setLastSeenMessagesUpdaterClass(Reflect.getNMSClass("LastSeenMessages$b", "net.minecraft.network.chat.LastSeenMessages$b", true));
        setLastSeenMessagesClass(Reflect.getNMSClass("LastSeenMessages", "net.minecraft.network.chat.LastSeenMessages", true));
        setMessageSignatureClass(Reflect.getNMSClass("MessageSignature", "net.minecraft.network.chat.MessageSignature", true));
        setSuggestionClass(Reflect.getClass("com.mojang.brigadier.suggestion.Suggestion", true));
        setSuggestionsClass(Reflect.getClass("com.mojang.brigadier.suggestion.Suggestions", true));
        setStringRangeClass(Reflect.getClass("com.mojang.brigadier.context.StringRange", true));
        setIChatBaseComponentClass(Reflect.getNMSClass("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent", true));
        setStringReaderClass(Reflect.getClass("com.mojang.brigadier.StringReader", true));
        setArgumentTypeClass(Reflect.getClass("com.mojang.brigadier.arguments.ArgumentType", true));
        setArgumentBuilderClass(Reflect.getClass("com.mojang.brigadier.builder.ArgumentBuilder", true));
        setLiteralArgumentBuilderClass(Reflect.getClass("com.mojang.brigadier.builder.LiteralArgumentBuilder", true));
        setRequiredArgumentBuilderClass(Reflect.getClass("com.mojang.brigadier.builder.RequiredArgumentBuilder", true));
        setCommandContextClass(Reflect.getClass("com.mojang.brigadier.context.CommandContext", true));
        setChatSerializerClass(Reflect.getSubClass(getIChatBaseComponentClass(), "ChatSerializer", true));
        setCommandContextBuilderClass(Reflect.getClass("com.mojang.brigadier.context.CommandContextBuilder", true));
        setParsedArgumentClass(Reflect.getClass("com.mojang.brigadier.context.ParsedArgument", true));
        setParsedCommandNodeClass(Reflect.getClass("com.mojang.brigadier.context.ParsedCommandNode", true));
        setSuggestionContextClass(Reflect.getClass("com.mojang.brigadier.context.SuggestionContext", true));
        setIntegerSuggestionClass(Reflect.getClass("com.mojang.brigadier.suggestion.IntegerSuggestion", true));
        setSuggestionProviderClass(Reflect.getClass("com.mojang.brigadier.suggestion.SuggestionProvider", true));
        setSuggestionsBuilderClass(Reflect.getClass("com.mojang.brigadier.suggestion.SuggestionsBuilder", true));
        setArgumentCommandNodeClass(Reflect.getClass("com.mojang.brigadier.tree.ArgumentCommandNode", true));
        setLiteralCommandNodeClass(Reflect.getClass("com.mojang.brigadier.tree.LiteralCommandNode", true));
        setRootCommandNodeClass(Reflect.getClass("com.mojang.brigadier.tree.RootCommandNode", true));
        setAmbiguityConsumerClass(Reflect.getClass("com.mojang.brigadier.AmbiguityConsumer", true));
        setCommandClass(Reflect.getClass("com.mojang.brigadier.Command", true));
        setCommandDispatcherClass(Reflect.getClass("com.mojang.brigadier.CommandDispatcher", true));
        setLiteralMessageClass(Reflect.getClass("com.mojang.brigadier.LiteralMessage", true));
        setParseResultsClass(Reflect.getClass("com.mojang.brigadier.ParseResults", true));
        setRedirectModifierClass(Reflect.getClass("com.mojang.brigadier.RedirectModifier", true));
        setResultConsumerClass(Reflect.getClass("com.mojang.brigadier.ResultConsumer", true));
        setSingleRedirectModifierClass(Reflect.getClass("com.mojang.brigadier.SingleRedirectModifier", true));
        setCommandNodeClass(Reflect.getClass("com.mojang.brigadier.tree.CommandNode", true));
        setKyoriComponentClass(Reflect.getClass("net.kyori.adventure.text.Component", true));
        setKyoriGsonComponentSerializerClass(Reflect.getClass("net.kyori.adventure.text.serializer.gson.GsonComponentSerializer", true));
        setCraftServerClass(Reflect.getCraftBukkitClass("CraftServer", true));
        setChatMessageTypeEnum(Reflect.getNMSClass("ChatMessageType", "net.minecraft.network.chat.ChatMessageType", true));
        setPlayerConnectionClass(Reflect.getNMSClass("PlayerConnection", "net.minecraft.server.network.PlayerConnection", true));
        setNetworkManagerClass(Reflect.getNMSClass("NetworkManager", "net.minecraft.network.NetworkManager", true));
        setMinecraftServerClass(Reflect.getNMSClass("MinecraftServer", "net.minecraft.server.MinecraftServer", true));
        setServerConnectionClass(Reflect.getNMSClass("ServerConnection", "net.minecraft.server.network.ServerConnection", true));
        setDedicatedServerClass(Reflect.getNMSClass("DedicatedServer", "net.minecraft.server.dedicated.DedicatedServer", true));
        setPacketDataSerializerClass(Reflect.getNMSClass("PacketDataContainer", "net.minecraft.network.PacketDataContainer", true));
        setGameProfileClass(Reflect.getClass("com.mojang.authlib.GameProfile", true));
        setPropertyClass(Reflect.getClass("com.mojang.authlib.properties.Property", true));
        setPropertyMapClass(Reflect.getClass("com.mojang.authlib.properties.PropertyMap", true));
        setMinecraftKeyClass(Reflect.getNMSClass("MinecraftKey", "net.minecraft.resources.MinecraftKey", true));
        setCraftWorldClass(Reflect.getCraftBukkitClass("CraftWorld", true));
        setMinecraftWorldClass(Reflect.getNMSClass("World", "net.minecraft.world.level.World", true));
        setMinecraftWorldServerClass(Reflect.getNMSClass("WorldServer", "net.minecraft.server.level.WorldServer", true));
        setCraftWorldBorderClass(Reflect.getCraftBukkitClass("CraftWorldBorder", true));
        setMinecraftWorldBorderClass(Reflect.getNMSClass("WorldBorder", "net.minecraft.world.level.border.WorldBorder", true));
        setEntityPlayerClass(Reflect.getNMSClass("EntityPlayer", "net.minecraft.server.level.EntityPlayer", true));
        setServerPingClass(Reflect.getNMSClass("ServerPing", "net.minecraft.network.protocol.status.ServerPing", true));
        setVibrationPathClass(Reflect.getClass("net.minecraft.world.level.gameevent.vibrations.VibrationPath", true));
        setPlayerAbilitiesClass(Reflect.getNMSClass("PlayerAbilities", "net.minecraft.world.entity.player.PlayerAbilities", true));
        setIRecipeClass(Reflect.getNMSClass("IRecipe", "net.minecraft.world.item.crafting.IRecipe", true));
        setDirectionEnum(Reflect.getNMSClass("EnumDirection", "net.minecraft.core.EnumDirection", true));
        setPlayerDigTypeEnum(Reflect.getNMSClass("PacketPlayInBlockDig$EnumPlayerDigType", "net.minecraft.network.protocol.game.PacketPlayInBlockDig$EnumPlayerDigType", true));
        setBlockPositionClass(Reflect.getNMSClass("BlockPosition", "net.minecraft.core.BlockPosition", true));
        setMinecraftItemStackClass(Reflect.getNMSClass("ItemStack", "net.minecraft.world.item.ItemStack", true));
        setHandEnum(Reflect.getNMSClass("EnumHand", "net.minecraft.world.EnumHand", true));
        setClientCommandEnum(Reflect.getNMSClass("EnumClientCommand", "net.minecraft.network.protocol.game.PacketPlayInClientCommand$EnumClientCommand", true));
        setDifficultyEnum(Reflect.getNMSClass("EnumDifficulty", "net.minecraft.world.EnumDifficulty", true));
        setMinecraftEntityClass(Reflect.getNMSClass("Entity", "net.minecraft.world.entity.Entity", true));
        setProtocolEnum(Reflect.getNMSClass("EnumProtocol", "net.minecraft.network.EnumProtocol", true));
        setAdvancementsStatusEnum(Reflect.getNMSClass("PacketPlayInAdvancements$Status", "net.minecraft.network.protocol.game.PacketPlayInAdvancements$Status", true));
        setCraftChatMessageClass(Reflect.getCraftBukkitClass("util.CraftChatMessage", true));
        setServerPingPlayerSampleClass(Reflect.getNMSClass("ServerPing$ServerPingPlayerSample", "net.minecraft.network.protocol.status.ServerPing$ServerPingPlayerSample", true));
        setServerDataClass(Reflect.getNMSClass("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData", true));
        setIChatMutableComponent(Reflect.getNMSClass("IChatMutableComponent", "net.minecraft.network.chat.IChatMutableComponent", true));
        setSoundCategoryEnum(Reflect.getNMSClass("SoundCategory", "net.minecraft.sounds.SoundCategory", true));
        setAttributeBaseClass(Reflect.getNMSClass("AttributeBase", "net.minecraft.world.entity.ai.attributes.AttributeBase", true));
        setAttributeModifierClass(Reflect.getNMSClass("AttributeModifier", "net.minecraft.world.entity.ai.attributes.AttributeModifier", true));
        setOperationClass(Reflect.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation", true));
        setMobEffectClass(Reflect.getNMSClass("MobEffect", "net.minecraft.world.effect.MobEffect", true));
        setFactorCalculationDataClass(Reflect.getNMSClass("MobEffect$Loot", "net.minecraft.world.effect.MobEffect$Loot", true));
        setMobEffectListClass(Reflect.getNMSClass("MobEffectList", "net.minecraft.world.effect.MobEffectList", true));
        setMobEffectInfoEnum(Reflect.getNMSClass("MobEffectInfo", "net.minecraft.world.effect.MobEffectInfo", true));
        setPlayerTeleportFlagsEnum(Reflect.getNMSClass("PacketPlayOutPosition$EnumPlayerTeleportFlags", "net.minecraft.network.protocol.game.PacketPlayOutPosition$EnumPlayerTeleportFlags", true));
        setCraftItemStackClass(Reflect.getCraftBukkitClass("inventory.CraftItemStack", true));
        setCraftPlayerClass(Reflect.getCraftBukkitClass("entity.CraftPlayer", true));
        setResourcePackStatusEnum(Reflect.getNMSClass("PacketPlayInResourcePackStatus$EnumResourcePackStatus", "net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus$EnumResourcePackStatus", true));
        if(ApproximateData.get().getPreVersion().greaterThanOrEqualTo(Version.v1_11) && ApproximateData.get().getPreVersion().lessThan(Version.v1_14)) {
            setChatVisibilityEnum(Reflect.getNMSClass("EntityHuman$EnumChatVisibility", "null", true));
        } else {
            setChatVisibilityEnum(Reflect.getNMSClass("EnumChatVisibility", "net.minecraft.world.entity.player.EnumChatVisibility", true));
        }

        setMainHandEnum(Reflect.getNMSClass("EnumMainHand", "net.minecraft.world.entity.EnumMainHand", true));
        setNbtTagCompoundClass(Reflect.getNMSClass("NBTTagCompound", "net.minecraft.nbt.NBTTagCompound", true));
        setVector3dClass(Reflect.getNMSClass("Vec3D", "net.minecraft.world.phys.Vec3D", true));
        setAxisAlignedClass(Reflect.getNMSClass("AxisAlignedBB", "net.minecraft.world.phys.AxisAlignedBB", true));
        setCraftEntityClass(Reflect.getCraftBukkitClass("entity.CraftEntity", true));
        setPlayerActionEnum(Reflect.getNMSClass("PacketPlayInEntityAction$EnumPlayerAction", " net.minecraft.network.protocol.game.PacketPlayInEntityAction$EnumPlayerAction", true));
        if(this.playerActionEnum == null) {
            setPlayerActionEnum(Reflect.getLegacyNMSClass("EnumPlayerAction", true));
        }
        setTileEntityCommandTypeEnum(Reflect.getNMSClass("TileEntityCommand$Type", "net.minecraft.world.level.block.entity.TileEntityCommand$Type", true));
        setTileEntityJigsawJointypeEnum(Reflect.getNMSClass("TileEntityJigsaw$JointType", "net.minecraft.world.level.block.entity.TileEntityJigsaw$JointType", true));
        setTileEntityStructureUpdateType(Reflect.getNMSClass("TileEntityStructure$UpdateType", "net.minecraft.world.level.block.entity.TileEntityStructure$UpdateType", true));
        setBlockPropertyStructureModeEnum(Reflect.getNMSClass("BlockPropertyStructureMode", "net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode", true));
        setBlockMirrorEnum(Reflect.getNMSClass("EnumBlockMirror", "net.minecraft.world.level.block.EnumBlockMirror", true));
        setBlockRotationEnum(Reflect.getNMSClass("EnumBlockRotation", "net.minecraft.world.level.block.EnumBlockRotation", true));
        setPointOEnum(Reflect.getNMSClass("PointGroupO", "com.mojang.math.PointGroupO", true));
        setPointSEnum(Reflect.getNMSClass("PointGroupS", "com.mojang.math.PointGroupS", true));
        setBaseBlockPositionClass(Reflect.getNMSClass("BaseBlockPosition", "net.minecraft.core.BaseBlockPosition", true));
        setEntityUseActionEnum(Reflect.getNMSClass("PacketPlayInUseEntity$EnumEntityUseAction", "net.minecraft.network.protocol.game.PacketPlayInUseEntity$EnumEntityUseAction", true));
        if(this.entityUseActionEnum == null) {
            setEntityUseActionEnum(Reflect.getLegacyNMSClass("EnumEntityUseAction", true));
        }
        setMovingObjectPositionBlockClass(Reflect.getNMSClass("MovingObjectPositionBlock", "net.minecraft.world.phys.MovingObjectPositionBlock", true));
        setInventoryClickTypeEnum(Reflect.getNMSClass("InventoryClickType", "net.minecraft.world.inventory.InventoryClickType", true));
        setCraftAdvancementClass(Reflect.getCraftBukkitClass("advancement.CraftAdvancement", true));
        setMinecraftAdvancementClass(Reflect.getNMSClass("Advancement", "net.minecraft.advancements.Advancement", true));
        setAdvancementFrameTypeEnum(Reflect.getNMSClass("AdvancementFrameType", "net.minecraft.advancements.AdvancementFrameType", true));
        setAdvancementDisplayClass(Reflect.getNMSClass("AdvancementDisplay", "net.minecraft.advancements.AdvancementDisplay", true));
        setChatFormatEnum(Reflect.getNMSClass("EnumChatFormat", "net.minecraft.EnumChatFormat", true));
        setCraftAdvancementProgress(Reflect.getCraftBukkitClass("advancement.CraftAdvancementProgress", true));
        setMinecraftAdvancementProgress(Reflect.getNMSClass("AdvancementProgress", "net.minecraft.advancements.AdvancementProgress", true));
        setAdvancementPlayerDataClass(Reflect.getNMSClass("AdvancementDataPlayer", "net.minecraft.server.AdvancementDataPlayer", true));
        setAttributeOperationEnum(Reflect.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation", true));
        setResourceKeyClass(Reflect.getNMSClass("ResourceKey", "net.minecraft.resources.ResourceKey", true));
        setInstantMobEffectClass(Reflect.getNMSClass("InstantMobEffect", "net.minecraft.world.effect.InstantMobEffect", true));
        setMobEffectAbsorptionClass(Reflect.getNMSClass("MobEffectAbsorption", "net.minecraft.world.effect.MobEffectAbsorption", true));
        setMobEffectAttackDamageClass(Reflect.getNMSClass("MobEffectAttackDamage", "net.minecraft.world.effect.MobEffectAttackDamage", true));
        setMobEffectHealthBoostClass(Reflect.getNMSClass("MobEffectHealthBoost", "net.minecraft.world.effect.MobEffectHealthBoost", true));
        setAttributeRangedClass(Reflect.getNMSClass("AttributeRanged", "net.minecraft.world.entity.ai.attributes.AttributeRanged", true));
        setRecipeBookTypeEnum(Reflect.getNMSClass("RecipeBookType", "net.minecraft.world.inventory.RecipeBookType", true));
        setMinecraftBlockClass(Reflect.getNMSClass("Block", "net.minecraft.world.level.block.Block", true));
        setCraftBlockClass(Reflect.getCraftBukkitClass("block.CraftBlock", true));
        setCraftMagicNumbersClass(Reflect.getCraftBukkitClass("util.CraftMagicNumbers", true));
        setIBlockDataClass(Reflect.getNMSClass("IBlockData", "net.minecraft.world.level.block.state.IBlockData", true));
        setMinecraftItemClass(Reflect.getNMSClass("Item", "net.minecraft.world.item.Item", true));
        setEntityTypesClass(Reflect.getNMSClass("EntityTypes", "net.minecraft.world.entity.EntityTypes", true));
        setEntityHumanClass(Reflect.getNMSClass("EntityHuman", "net.minecraft.world.entity.player.EntityHuman", true));
        setCraftChunkClass(Reflect.getCraftBukkitClass("CraftChunk", true));
        setMinecraftChunkClass(Reflect.getNMSClass("Chunk", "net.minecraft.world.level.chunk.Chunk", true));
        setIChunkAccessClass(Reflect.getNMSClass("IChunkAccess", "net.minecraft.world.level.chunk.IChunkAccess", true));
        setIBlockAccessClass(Reflect.getNMSClass("IBlockAccess", "net.minecraft.world.level.IBlockAccess", true));
        setBossActionEnum(Reflect.getNMSClass("PacketPlayOutBoss$Action", "net.minecraft.network.protocol.game.PacketPlayOutBoss$d", true));
        setBossActionInterface(Reflect.getClass("net.minecraft.network.protocol.game.PacketPlayOutBoss$Action", true));
        setBarColorEnum(Reflect.getNMSClass("BossBattle$BarColor", "net.minecraft.world.BossBattle$BarColor", true));
        setBarStyleEnum(Reflect.getNMSClass("BossBattle$BarStyle", "net.minecraft.world.BossBattle$BarStyle", true));
        setBossBattleClass(Reflect.getNMSClass("BossBattle", "net.minecraft.world.BossBattle", true));
        setOutCommandClassA(Reflect.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$Updater", true));
        setOutCommandClassB(Reflect.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$b", true));
        setOutCommandInterfaceE(Reflect.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$e", true));
        setMessageSignatureStorageClass(Reflect.getClass("net.minecraft.network.chat.MessageSignature$a", true));
        setMinecraftEncryptionSignatureDataClass(Reflect.getClass("net.minecraft.util.MinecraftEncryption$b", true));
    }

    private static ClassAccessor INSTANCE;
    public static void init() {
        INSTANCE = new ClassAccessor();
    }
    public static ClassAccessor get() {
        return INSTANCE;
    }

    public static Class<?> arrayOfClass(Class<?> input) {
        return Array.newInstance(input, 1).getClass();
    }
}
