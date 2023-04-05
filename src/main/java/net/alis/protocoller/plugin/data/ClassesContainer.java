package net.alis.protocoller.plugin.data;

import lombok.Getter;
import lombok.Setter;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;

public class ClassesContainer {

    private @Setter @Getter Class<?>
        iChatBaseComponentClass, suggestionClass, suggestionsClass, stringRangeClass, stringReaderClass,
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
        lightEngineClass;
    ClassesContainer() {
        setLightEngineClass(BaseReflection.getNMSClass("LightEngine", "net.minecraft.world.level.lighting.LightEngine", true));
        setNbtTagEndClass(BaseReflection.getNMSClass("NBTTagEnd", "net.minecraft.nbt.NBTTagEnd", true));
        setNbtTagByteClass(BaseReflection.getNMSClass("NBTTagByte", "net.minecraft.nbt.NBTTagByte", true));
        setNbtTagShortClass(BaseReflection.getNMSClass("NBTTagShort", "net.minecraft.nbt.NBTTagShort", true));
        setNbtTagIntClass(BaseReflection.getNMSClass("NBTTagInt", "net.minecraft.nbt.NBTTagInt", true));
        setNbtTagLongClass(BaseReflection.getNMSClass("NBTTagLong", "net.minecraft.nbt.NBTTagLong", true));
        setNbtTagFloatClass(BaseReflection.getNMSClass("NBTTagFloat", "net.minecraft.nbt.NBTTagFloat", true));
        setNbtTagDoubleClass(BaseReflection.getNMSClass("NBTTagDouble", "net.minecraft.nbt.NBTTagDouble", true));
        setNbtTagByteArrayClass(BaseReflection.getNMSClass("NBTTagByteArray", "net.minecraft.nbt.NBTTagByteArray", true));
        setNbtTagStringClass(BaseReflection.getNMSClass("NBTTagString", "net.minecraft.nbt.NBTTagString", true));
        setNbtTagListClass(BaseReflection.getNMSClass("NBTTagList", "net.minecraft.nbt.NBTTagList", true));
        setNbtTagIntArrayClass(BaseReflection.getNMSClass("NBTTagIntArray", "net.minecraft.nbt.NBTTagIntArray", true));
        setNbtTagLongArrayClass(BaseReflection.getNMSClass("NBTTagLongArray", "net.minecraft.nbt.NBTTagLongArray", true));
        setBlockEntityInfoClass(BaseReflection.getClass("net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$a", true));
        setTileEntityTypesClass(BaseReflection.getNMSClass("TileEntityTypes", "net.minecraft.world.level.block.entity.TileEntityTypes", true));
        setChunkCoordIntPairClass(BaseReflection.getNMSClass("ChunkCoordIntPair", "net.minecraft.world.level.ChunkCoordIntPair", true));
        setLastSeenMessagesCacheClass(BaseReflection.getNMSClass("LastSeenMessages$a", "net.minecraft.network.chat.LastSeenMessages$a", true));
        setLastSeenMessagesUpdaterClass(BaseReflection.getNMSClass("LastSeenMessages$b", "net.minecraft.network.chat.LastSeenMessages$b", true));
        setLastSeenMessagesClass(BaseReflection.getNMSClass("LastSeenMessages", "net.minecraft.network.chat.LastSeenMessages", true));
        setMessageSignatureClass(BaseReflection.getNMSClass("MessageSignature", "net.minecraft.network.chat.MessageSignature", true));
        setSuggestionClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.Suggestion", true));
        setSuggestionsClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.Suggestions", true));
        setStringRangeClass(BaseReflection.getClass("com.mojang.brigadier.context.StringRange", true));
        setIChatBaseComponentClass(BaseReflection.getNMSClass("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent", true));
        setStringReaderClass(BaseReflection.getClass("com.mojang.brigadier.StringReader", true));
        setArgumentTypeClass(BaseReflection.getClass("com.mojang.brigadier.arguments.ArgumentType", true));
        setArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.ArgumentBuilder", true));
        setLiteralArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.LiteralArgumentBuilder", true));
        setRequiredArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.RequiredArgumentBuilder", true));
        setCommandContextClass(BaseReflection.getClass("com.mojang.brigadier.context.CommandContext", true));
        setChatSerializerClass(BaseReflection.getSubClass(getIChatBaseComponentClass(), "ChatSerializer", true));
        setCommandContextBuilderClass(BaseReflection.getClass("com.mojang.brigadier.context.CommandContextBuilder", true));
        setParsedArgumentClass(BaseReflection.getClass("com.mojang.brigadier.context.ParsedArgument", true));
        setParsedCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.context.ParsedCommandNode", true));
        setSuggestionContextClass(BaseReflection.getClass("com.mojang.brigadier.context.SuggestionContext", true));
        setIntegerSuggestionClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.IntegerSuggestion", true));
        setSuggestionProviderClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.SuggestionProvider", true));
        setSuggestionsBuilderClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.SuggestionsBuilder", true));
        setArgumentCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.ArgumentCommandNode", true));
        setLiteralCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.LiteralCommandNode", true));
        setRootCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.RootCommandNode", true));
        setAmbiguityConsumerClass(BaseReflection.getClass("com.mojang.brigadier.AmbiguityConsumer", true));
        setCommandClass(BaseReflection.getClass("com.mojang.brigadier.Command", true));
        setCommandDispatcherClass(BaseReflection.getClass("com.mojang.brigadier.CommandDispatcher", true));
        setLiteralMessageClass(BaseReflection.getClass("com.mojang.brigadier.LiteralMessage", true));
        setParseResultsClass(BaseReflection.getClass("com.mojang.brigadier.ParseResults", true));
        setRedirectModifierClass(BaseReflection.getClass("com.mojang.brigadier.RedirectModifier", true));
        setResultConsumerClass(BaseReflection.getClass("com.mojang.brigadier.ResultConsumer", true));
        setSingleRedirectModifierClass(BaseReflection.getClass("com.mojang.brigadier.SingleRedirectModifier", true));
        setCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.CommandNode", true));
        setKyoriComponentClass(BaseReflection.getClass("net.kyori.adventure.text.Component", true));
        setKyoriGsonComponentSerializerClass(BaseReflection.getClass("net.kyori.adventure.text.serializer.gson.GsonComponentSerializer", true));
        setCraftServerClass(BaseReflection.getCraftBukkitClass("CraftServer", true));
        setChatMessageTypeEnum(BaseReflection.getNMSClass("ChatMessageType", "net.minecraft.network.chat.ChatMessageType", true));
        setPlayerConnectionClass(BaseReflection.getNMSClass("PlayerConnection", "net.minecraft.server.network.PlayerConnection", true));
        setNetworkManagerClass(BaseReflection.getNMSClass("NetworkManager", "net.minecraft.network.NetworkManager", true));
        setMinecraftServerClass(BaseReflection.getNMSClass("MinecraftServer", "net.minecraft.server.MinecraftServer", true));
        setServerConnectionClass(BaseReflection.getNMSClass("ServerConnection", "net.minecraft.server.network.ServerConnection", true));
        setDedicatedServerClass(BaseReflection.getNMSClass("DedicatedServer", "net.minecraft.server.dedicated.DedicatedServer", true));
        setPacketDataSerializerClass(BaseReflection.getNMSClass("PacketDataContainer", "net.minecraft.network.PacketDataContainer", true));
        setGameProfileClass(BaseReflection.getClass("com.mojang.authlib.GameProfile", true));
        setPropertyClass(BaseReflection.getClass("com.mojang.authlib.properties.Property", true));
        setPropertyMapClass(BaseReflection.getClass("com.mojang.authlib.properties.PropertyMap", true));
        setMinecraftKeyClass(BaseReflection.getNMSClass("MinecraftKey", "net.minecraft.resources.MinecraftKey", true));
        setCraftWorldClass(BaseReflection.getCraftBukkitClass("CraftWorld", true));
        setMinecraftWorldClass(BaseReflection.getNMSClass("World", "net.minecraft.world.level.World", true));
        setMinecraftWorldServerClass(BaseReflection.getNMSClass("WorldServer", "net.minecraft.server.level.WorldServer", true));
        setCraftWorldBorderClass(BaseReflection.getCraftBukkitClass("CraftWorldBorder", true));
        setMinecraftWorldBorderClass(BaseReflection.getNMSClass("WorldBorder", "net.minecraft.world.level.border.WorldBorder", true));
        setEntityPlayerClass(BaseReflection.getNMSClass("EntityPlayer", "net.minecraft.server.level.EntityPlayer", true));
        setServerPingClass(BaseReflection.getNMSClass("ServerPing", "net.minecraft.network.protocol.status.ServerPing", true));
        setVibrationPathClass(BaseReflection.getClass("net.minecraft.world.level.gameevent.vibrations.VibrationPath", true));
        setPlayerAbilitiesClass(BaseReflection.getNMSClass("PlayerAbilities", "net.minecraft.world.entity.player.PlayerAbilities", true));
        setIRecipeClass(BaseReflection.getNMSClass("IRecipe", "net.minecraft.world.item.crafting.IRecipe", true));
        setDirectionEnum(BaseReflection.getNMSClass("EnumDirection", "net.minecraft.core.EnumDirection", true));
        setPlayerDigTypeEnum(BaseReflection.getNMSClass("PacketPlayInBlockDig$EnumPlayerDigType", "net.minecraft.network.protocol.game.PacketPlayInBlockDig$EnumPlayerDigType", true));
        setBlockPositionClass(BaseReflection.getNMSClass("BlockPosition", "net.minecraft.core.BlockPosition", true));
        setMinecraftItemStackClass(BaseReflection.getNMSClass("ItemStack", "net.minecraft.world.item.ItemStack", true));
        setHandEnum(BaseReflection.getNMSClass("EnumHand", "net.minecraft.world.EnumHand", true));
        setClientCommandEnum(BaseReflection.getNMSClass("EnumClientCommand", "net.minecraft.network.protocol.game.PacketPlayInClientCommand$EnumClientCommand", true));
        setDifficultyEnum(BaseReflection.getNMSClass("EnumDifficulty", "net.minecraft.world.EnumDifficulty", true));
        setMinecraftEntityClass(BaseReflection.getNMSClass("Entity", "net.minecraft.world.entity.Entity", true));
        setProtocolEnum(BaseReflection.getNMSClass("EnumProtocol", "net.minecraft.network.EnumProtocol", true));
        setAdvancementsStatusEnum(BaseReflection.getNMSClass("PacketPlayInAdvancements$Status", "net.minecraft.network.protocol.game.PacketPlayInAdvancements$Status", true));
        setCraftChatMessageClass(BaseReflection.getCraftBukkitClass("util.CraftChatMessage", true));
        setServerPingPlayerSampleClass(BaseReflection.getNMSClass("ServerPing$ServerPingPlayerSample", "net.minecraft.network.protocol.status.ServerPing$ServerPingPlayerSample", true));
        setServerDataClass(BaseReflection.getNMSClass("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData", true));
        setIChatMutableComponent(BaseReflection.getNMSClass("IChatMutableComponent", "net.minecraft.network.chat.IChatMutableComponent", true));
        setSoundCategoryEnum(BaseReflection.getNMSClass("SoundCategory", "net.minecraft.sounds.SoundCategory", true));
        setAttributeBaseClass(BaseReflection.getNMSClass("AttributeBase", "net.minecraft.world.entity.ai.attributes.AttributeBase", true));
        setAttributeModifierClass(BaseReflection.getNMSClass("AttributeModifier", "net.minecraft.world.entity.ai.attributes.AttributeModifier", true));
        setOperationClass(BaseReflection.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation", true));
        setMobEffectClass(BaseReflection.getNMSClass("MobEffect", "net.minecraft.world.effect.MobEffect", true));
        setFactorCalculationDataClass(BaseReflection.getNMSClass("MobEffect$Loot", "net.minecraft.world.effect.MobEffect$Loot", true));
        setMobEffectListClass(BaseReflection.getNMSClass("MobEffectList", "net.minecraft.world.effect.MobEffectList", true));
        setMobEffectInfoEnum(BaseReflection.getNMSClass("MobEffectInfo", "net.minecraft.world.effect.MobEffectInfo", true));
        setPlayerTeleportFlagsEnum(BaseReflection.getNMSClass("PacketPlayOutPosition$EnumPlayerTeleportFlags", "net.minecraft.network.protocol.game.PacketPlayOutPosition$EnumPlayerTeleportFlags", true));
        setCraftItemStackClass(BaseReflection.getCraftBukkitClass("inventory.CraftItemStack", true));
        setCraftPlayerClass(BaseReflection.getCraftBukkitClass("entity.CraftPlayer", true));
        setResourcePackStatusEnum(BaseReflection.getNMSClass("PacketPlayInResourcePackStatus$EnumResourcePackStatus", "net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus$EnumResourcePackStatus", true));
        if(InitialData.INSTANCE.getPreVersion().greaterThanOrEqualTo(Version.v1_11) && InitialData.INSTANCE.getPreVersion().lessThan(Version.v1_14)) {
            setChatVisibilityEnum(BaseReflection.getNMSClass("EntityHuman$EnumChatVisibility", "null", true));
        } else {
            setChatVisibilityEnum(BaseReflection.getNMSClass("EnumChatVisibility", "net.minecraft.world.entity.player.EnumChatVisibility", true));
        }

        setMainHandEnum(BaseReflection.getNMSClass("EnumMainHand", "net.minecraft.world.entity.EnumMainHand", true));
        setNbtTagCompoundClass(BaseReflection.getNMSClass("NBTTagCompound", "net.minecraft.nbt.NBTTagCompound", true));
        setVector3dClass(BaseReflection.getNMSClass("Vec3D", "net.minecraft.world.phys.Vec3D", true));
        setAxisAlignedClass(BaseReflection.getNMSClass("AxisAlignedBB", "net.minecraft.world.phys.AxisAlignedBB", true));
        setCraftEntityClass(BaseReflection.getCraftBukkitClass("entity.CraftEntity", true));
        setPlayerActionEnum(BaseReflection.getNMSClass("PacketPlayInEntityAction$EnumPlayerAction", " net.minecraft.network.protocol.game.PacketPlayInEntityAction$EnumPlayerAction", true));
        if(this.playerActionEnum == null) {
            setPlayerActionEnum(BaseReflection.getLegacyNMSClass("EnumPlayerAction", true));
        }
        setTileEntityCommandTypeEnum(BaseReflection.getNMSClass("TileEntityCommand$Type", "net.minecraft.world.level.block.entity.TileEntityCommand$Type", true));
        setTileEntityJigsawJointypeEnum(BaseReflection.getNMSClass("TileEntityJigsaw$JointType", "net.minecraft.world.level.block.entity.TileEntityJigsaw$JointType", true));
        setTileEntityStructureUpdateType(BaseReflection.getNMSClass("TileEntityStructure$UpdateType", "net.minecraft.world.level.block.entity.TileEntityStructure$UpdateType", true));
        setBlockPropertyStructureModeEnum(BaseReflection.getNMSClass("BlockPropertyStructureMode", "net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode", true));
        setBlockMirrorEnum(BaseReflection.getNMSClass("EnumBlockMirror", "net.minecraft.world.level.block.EnumBlockMirror", true));
        setBlockRotationEnum(BaseReflection.getNMSClass("EnumBlockRotation", "net.minecraft.world.level.block.EnumBlockRotation", true));
        setPointOEnum(BaseReflection.getNMSClass("PointGroupO", "com.mojang.math.PointGroupO", true));
        setPointSEnum(BaseReflection.getNMSClass("PointGroupS", "com.mojang.math.PointGroupS", true));
        setBaseBlockPositionClass(BaseReflection.getNMSClass("BaseBlockPosition", "net.minecraft.core.BaseBlockPosition", true));
        setEntityUseActionEnum(BaseReflection.getNMSClass("PacketPlayInUseEntity$EnumEntityUseAction", "net.minecraft.network.protocol.game.PacketPlayInUseEntity$EnumEntityUseAction", true));
        if(this.entityUseActionEnum == null) {
            setEntityUseActionEnum(BaseReflection.getLegacyNMSClass("EnumEntityUseAction", true));
        }
        setMovingObjectPositionBlockClass(BaseReflection.getNMSClass("MovingObjectPositionBlock", "net.minecraft.world.phys.MovingObjectPositionBlock", true));
        setInventoryClickTypeEnum(BaseReflection.getNMSClass("InventoryClickType", "net.minecraft.world.inventory.InventoryClickType", true));
        setCraftAdvancementClass(BaseReflection.getCraftBukkitClass("advancement.CraftAdvancement", true));
        setMinecraftAdvancementClass(BaseReflection.getNMSClass("Advancement", "net.minecraft.advancements.Advancement", true));
        setAdvancementFrameTypeEnum(BaseReflection.getNMSClass("AdvancementFrameType", "net.minecraft.advancements.AdvancementFrameType", true));
        setAdvancementDisplayClass(BaseReflection.getNMSClass("AdvancementDisplay", "net.minecraft.advancements.AdvancementDisplay", true));
        setChatFormatEnum(BaseReflection.getNMSClass("EnumChatFormat", "net.minecraft.EnumChatFormat", true));
        setCraftAdvancementProgress(BaseReflection.getCraftBukkitClass("advancement.CraftAdvancementProgress", true));
        setMinecraftAdvancementProgress(BaseReflection.getNMSClass("AdvancementProgress", "net.minecraft.advancements.AdvancementProgress", true));
        setAdvancementPlayerDataClass(BaseReflection.getNMSClass("AdvancementDataPlayer", "net.minecraft.server.AdvancementDataPlayer", true));
        setAttributeOperationEnum(BaseReflection.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation", true));
        setResourceKeyClass(BaseReflection.getNMSClass("ResourceKey", "net.minecraft.resources.ResourceKey", true));
        setInstantMobEffectClass(BaseReflection.getNMSClass("InstantMobEffect", "net.minecraft.world.effect.InstantMobEffect", true));
        setMobEffectAbsorptionClass(BaseReflection.getNMSClass("MobEffectAbsorption", "net.minecraft.world.effect.MobEffectAbsorption", true));
        setMobEffectAttackDamageClass(BaseReflection.getNMSClass("MobEffectAttackDamage", "net.minecraft.world.effect.MobEffectAttackDamage", true));
        setMobEffectHealthBoostClass(BaseReflection.getNMSClass("MobEffectHealthBoost", "net.minecraft.world.effect.MobEffectHealthBoost", true));
        setAttributeRangedClass(BaseReflection.getNMSClass("AttributeRanged", "net.minecraft.world.entity.ai.attributes.AttributeRanged", true));
        setRecipeBookTypeEnum(BaseReflection.getNMSClass("RecipeBookType", "net.minecraft.world.inventory.RecipeBookType", true));
        setMinecraftBlockClass(BaseReflection.getNMSClass("Block", "net.minecraft.world.level.block.Block", true));
        setCraftBlockClass(BaseReflection.getCraftBukkitClass("block.CraftBlock", true));
        setCraftMagicNumbersClass(BaseReflection.getCraftBukkitClass("util.CraftMagicNumbers", true));
        setIBlockDataClass(BaseReflection.getNMSClass("IBlockData", "net.minecraft.world.level.block.state.IBlockData", true));
        setMinecraftItemClass(BaseReflection.getNMSClass("Item", "net.minecraft.world.item.Item", true));
        setEntityTypesClass(BaseReflection.getNMSClass("EntityTypes", "net.minecraft.world.entity.EntityTypes", true));
        setEntityHumanClass(BaseReflection.getNMSClass("EntityHuman", "net.minecraft.world.entity.player.EntityHuman", true));
        setCraftChunkClass(BaseReflection.getCraftBukkitClass("CraftChunk", true));
        setMinecraftChunkClass(BaseReflection.getNMSClass("Chunk", "net.minecraft.world.level.chunk.Chunk", true));
        setIChunkAccessClass(BaseReflection.getNMSClass("IChunkAccess", "net.minecraft.world.level.chunk.IChunkAccess", true));
        setIBlockAccessClass(BaseReflection.getNMSClass("IBlockAccess", "net.minecraft.world.level.IBlockAccess", true));
        setBossActionEnum(BaseReflection.getNMSClass("PacketPlayOutBoss$Action", "net.minecraft.network.protocol.game.PacketPlayOutBoss$d", true));
        setBossActionInterface(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutBoss$Action", true));
        setBarColorEnum(BaseReflection.getNMSClass("BossBattle$BarColor", "net.minecraft.world.BossBattle$BarColor", true));
        setBarStyleEnum(BaseReflection.getNMSClass("BossBattle$BarStyle", "net.minecraft.world.BossBattle$BarStyle", true));
        setBossBattleClass(BaseReflection.getNMSClass("BossBattle", "net.minecraft.world.BossBattle", true));
        setOutCommandClassA(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$Updater", true));
        setOutCommandClassB(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$b", true));
        setOutCommandInterfaceE(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$e", true));
        setMessageSignatureStorageClass(BaseReflection.getClass("net.minecraft.network.chat.MessageSignature$a", true));
        setMinecraftEncryptionSignatureDataClass(BaseReflection.getClass("net.minecraft.util.MinecraftEncryption$b", true));
    }

    private static ClassesContainer INSTANCE;
    public static void init() {
        INSTANCE = new ClassesContainer();
    }
    public static ClassesContainer get() {
        return INSTANCE;
    }
}
