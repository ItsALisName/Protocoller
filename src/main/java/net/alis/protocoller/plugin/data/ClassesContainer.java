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
        setLightEngineClass(BaseReflection.getNMSClass("LightEngine", "net.minecraft.world.level.lighting.LightEngine"));
        setNbtTagEndClass(BaseReflection.getNMSClass("NBTTagEnd", "net.minecraft.nbt.NBTTagEnd"));
        setNbtTagByteClass(BaseReflection.getNMSClass("NBTTagByte", "net.minecraft.nbt.NBTTagByte"));
        setNbtTagShortClass(BaseReflection.getNMSClass("NBTTagShort", "net.minecraft.nbt.NBTTagShort"));
        setNbtTagIntClass(BaseReflection.getNMSClass("NBTTagInt", "net.minecraft.nbt.NBTTagInt"));
        setNbtTagLongClass(BaseReflection.getNMSClass("NBTTagLong", "net.minecraft.nbt.NBTTagLong"));
        setNbtTagFloatClass(BaseReflection.getNMSClass("NBTTagFloat", "net.minecraft.nbt.NBTTagFloat"));
        setNbtTagDoubleClass(BaseReflection.getNMSClass("NBTTagDouble", "net.minecraft.nbt.NBTTagDouble"));
        setNbtTagByteArrayClass(BaseReflection.getNMSClass("NBTTagByteArray", "net.minecraft.nbt.NBTTagByteArray"));
        setNbtTagStringClass(BaseReflection.getNMSClass("NBTTagString", "net.minecraft.nbt.NBTTagString"));
        setNbtTagListClass(BaseReflection.getNMSClass("NBTTagList", "net.minecraft.nbt.NBTTagList"));
        setNbtTagIntArrayClass(BaseReflection.getNMSClass("NBTTagIntArray", "net.minecraft.nbt.NBTTagIntArray"));
        setNbtTagLongArrayClass(BaseReflection.getNMSClass("NBTTagLongArray", "net.minecraft.nbt.NBTTagLongArray"));
        setBlockEntityInfoClass(BaseReflection.getClass("net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$a"));
        setTileEntityTypesClass(BaseReflection.getNMSClass("TileEntityTypes", "net.minecraft.world.level.block.entity.TileEntityTypes"));
        setChunkCoordIntPairClass(BaseReflection.getNMSClass("ChunkCoordIntPair", "net.minecraft.world.level.ChunkCoordIntPair"));
        setLastSeenMessagesCacheClass(BaseReflection.getNMSClass("LastSeenMessages$a", "net.minecraft.network.chat.LastSeenMessages$a"));
        setLastSeenMessagesUpdaterClass(BaseReflection.getNMSClass("LastSeenMessages$b", "net.minecraft.network.chat.LastSeenMessages$b"));
        setLastSeenMessagesClass(BaseReflection.getNMSClass("LastSeenMessages", "net.minecraft.network.chat.LastSeenMessages"));
        setMessageSignatureClass(BaseReflection.getNMSClass("MessageSignature", "net.minecraft.network.chat.MessageSignature"));
        setSuggestionClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.Suggestion"));
        setSuggestionsClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.Suggestions"));
        setStringRangeClass(BaseReflection.getClass("com.mojang.brigadier.context.StringRange"));
        setIChatBaseComponentClass(BaseReflection.getNMSClass("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent"));
        setStringReaderClass(BaseReflection.getClass("com.mojang.brigadier.StringReader"));
        setArgumentTypeClass(BaseReflection.getClass("com.mojang.brigadier.arguments.ArgumentType"));
        setArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.ArgumentBuilder"));
        setLiteralArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.LiteralArgumentBuilder"));
        setRequiredArgumentBuilderClass(BaseReflection.getClass("com.mojang.brigadier.builder.RequiredArgumentBuilder"));
        setCommandContextClass(BaseReflection.getClass("com.mojang.brigadier.context.CommandContext"));
        setChatSerializerClass(BaseReflection.getSubClass(getIChatBaseComponentClass(), "ChatSerializer"));
        setCommandContextBuilderClass(BaseReflection.getClass("com.mojang.brigadier.context.CommandContextBuilder"));
        setParsedArgumentClass(BaseReflection.getClass("com.mojang.brigadier.context.ParsedArgument"));
        setParsedCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.context.ParsedCommandNode"));
        setSuggestionContextClass(BaseReflection.getClass("com.mojang.brigadier.context.SuggestionContext"));
        setIntegerSuggestionClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.IntegerSuggestion"));
        setSuggestionProviderClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.SuggestionProvider"));
        setSuggestionsBuilderClass(BaseReflection.getClass("com.mojang.brigadier.suggestion.SuggestionsBuilder"));
        setArgumentCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.ArgumentCommandNode"));
        setLiteralCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.LiteralCommandNode"));
        setRootCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.RootCommandNode"));
        setAmbiguityConsumerClass(BaseReflection.getClass("com.mojang.brigadier.AmbiguityConsumer"));
        setCommandClass(BaseReflection.getClass("com.mojang.brigadier.Command"));
        setCommandDispatcherClass(BaseReflection.getClass("com.mojang.brigadier.CommandDispatcher"));
        setLiteralMessageClass(BaseReflection.getClass("com.mojang.brigadier.LiteralMessage"));
        setParseResultsClass(BaseReflection.getClass("com.mojang.brigadier.ParseResults"));
        setRedirectModifierClass(BaseReflection.getClass("com.mojang.brigadier.RedirectModifier"));
        setResultConsumerClass(BaseReflection.getClass("com.mojang.brigadier.ResultConsumer"));
        setSingleRedirectModifierClass(BaseReflection.getClass("com.mojang.brigadier.SingleRedirectModifier"));
        setCommandNodeClass(BaseReflection.getClass("com.mojang.brigadier.tree.CommandNode"));
        setKyoriComponentClass(BaseReflection.getClass("net.kyori.adventure.text.Component"));
        setKyoriGsonComponentSerializerClass(BaseReflection.getClass("net.kyori.adventure.text.serializer.gson.GsonComponentSerializer"));
        setCraftServerClass(BaseReflection.getCraftBukkitClass("CraftServer"));
        setChatMessageTypeEnum(BaseReflection.getNMSClass("ChatMessageType", "net.minecraft.network.chat.ChatMessageType"));
        setPlayerConnectionClass(BaseReflection.getNMSClass("PlayerConnection", "net.minecraft.server.network.PlayerConnection"));
        setNetworkManagerClass(BaseReflection.getNMSClass("NetworkManager", "net.minecraft.network.NetworkManager"));
        setMinecraftServerClass(BaseReflection.getNMSClass("MinecraftServer", "net.minecraft.server.MinecraftServer"));
        setServerConnectionClass(BaseReflection.getNMSClass("ServerConnection", "net.minecraft.server.network.ServerConnection"));
        setDedicatedServerClass(BaseReflection.getNMSClass("DedicatedServer", "net.minecraft.server.dedicated.DedicatedServer"));
        setPacketDataSerializerClass(BaseReflection.getNMSClass("PacketDataContainer", "net.minecraft.network.PacketDataContainer"));
        setGameProfileClass(BaseReflection.getClass("com.mojang.authlib.GameProfile"));
        setPropertyClass(BaseReflection.getClass("com.mojang.authlib.properties.Property"));
        setPropertyMapClass(BaseReflection.getClass("com.mojang.authlib.properties.PropertyMap"));
        setMinecraftKeyClass(BaseReflection.getNMSClass("MinecraftKey", "net.minecraft.resources.MinecraftKey"));
        setCraftWorldClass(BaseReflection.getCraftBukkitClass("CraftWorld"));
        setMinecraftWorldClass(BaseReflection.getNMSClass("World", "net.minecraft.world.level.World"));
        setMinecraftWorldServerClass(BaseReflection.getNMSClass("WorldServer", "net.minecraft.server.level.WorldServer"));
        setCraftWorldBorderClass(BaseReflection.getCraftBukkitClass("CraftWorldBorder"));
        setMinecraftWorldBorderClass(BaseReflection.getNMSClass("WorldBorder", "net.minecraft.world.level.border.WorldBorder"));
        setEntityPlayerClass(BaseReflection.getNMSClass("EntityPlayer", "net.minecraft.server.level.EntityPlayer"));
        setServerPingClass(BaseReflection.getNMSClass("ServerPing", "net.minecraft.network.protocol.status.ServerPing"));
        setVibrationPathClass(BaseReflection.getClass("net.minecraft.world.level.gameevent.vibrations.VibrationPath"));
        setPlayerAbilitiesClass(BaseReflection.getNMSClass("PlayerAbilities", "net.minecraft.world.entity.player.PlayerAbilities"));
        setIRecipeClass(BaseReflection.getNMSClass("IRecipe", "net.minecraft.world.item.crafting.IRecipe"));
        setDirectionEnum(BaseReflection.getNMSClass("EnumDirection", "net.minecraft.core.EnumDirection"));
        setPlayerDigTypeEnum(BaseReflection.getNMSClass("PacketPlayInBlockDig$EnumPlayerDigType", "net.minecraft.network.protocol.game.PacketPlayInBlockDig$EnumPlayerDigType"));
        setBlockPositionClass(BaseReflection.getNMSClass("BlockPosition", "net.minecraft.core.BlockPosition"));
        setMinecraftItemStackClass(BaseReflection.getNMSClass("ItemStack", "net.minecraft.world.item.ItemStack"));
        setHandEnum(BaseReflection.getNMSClass("EnumHand", "net.minecraft.world.EnumHand"));
        setClientCommandEnum(BaseReflection.getNMSClass("EnumClientCommand", "net.minecraft.network.protocol.game.PacketPlayInClientCommand$EnumClientCommand"));
        setDifficultyEnum(BaseReflection.getNMSClass("EnumDifficulty", "net.minecraft.world.EnumDifficulty"));
        setMinecraftEntityClass(BaseReflection.getNMSClass("Entity", "net.minecraft.world.entity.Entity"));
        setProtocolEnum(BaseReflection.getNMSClass("EnumProtocol", "net.minecraft.network.EnumProtocol"));
        setAdvancementsStatusEnum(BaseReflection.getNMSClass("PacketPlayInAdvancements$Status", "net.minecraft.network.protocol.game.PacketPlayInAdvancements$Status"));
        setCraftChatMessageClass(BaseReflection.getCraftBukkitClass("util.CraftChatMessage"));
        setServerPingPlayerSampleClass(BaseReflection.getNMSClass("ServerPing$ServerPingPlayerSample", "net.minecraft.network.protocol.status.ServerPing$ServerPingPlayerSample"));
        setServerDataClass(BaseReflection.getNMSClass("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData"));
        setIChatMutableComponent(BaseReflection.getNMSClass("IChatMutableComponent", "net.minecraft.network.chat.IChatMutableComponent"));
        setSoundCategoryEnum(BaseReflection.getNMSClass("SoundCategory", "net.minecraft.sounds.SoundCategory"));
        setAttributeBaseClass(BaseReflection.getNMSClass("AttributeBase", "net.minecraft.world.entity.ai.attributes.AttributeBase"));
        setAttributeModifierClass(BaseReflection.getNMSClass("AttributeModifier", "net.minecraft.world.entity.ai.attributes.AttributeModifier"));
        setOperationClass(BaseReflection.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation"));
        setMobEffectClass(BaseReflection.getNMSClass("MobEffect", "net.minecraft.world.effect.MobEffect"));
        setFactorCalculationDataClass(BaseReflection.getNMSClass("MobEffect$Loot", "net.minecraft.world.effect.MobEffect$Loot"));
        setMobEffectListClass(BaseReflection.getNMSClass("MobEffectList", "net.minecraft.world.effect.MobEffectList"));
        setMobEffectInfoEnum(BaseReflection.getNMSClass("MobEffectInfo", "net.minecraft.world.effect.MobEffectInfo"));
        setPlayerTeleportFlagsEnum(BaseReflection.getNMSClass("PacketPlayOutPosition$EnumPlayerTeleportFlags", "net.minecraft.network.protocol.game.PacketPlayOutPosition$EnumPlayerTeleportFlags"));
        setCraftItemStackClass(BaseReflection.getCraftBukkitClass("inventory.CraftItemStack"));
        setCraftPlayerClass(BaseReflection.getCraftBukkitClass("entity.CraftPlayer"));
        setResourcePackStatusEnum(BaseReflection.getNMSClass("PacketPlayInResourcePackStatus$EnumResourcePackStatus", "net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus$EnumResourcePackStatus"));
        if(InitialData.INSTANCE.getPreVersion().greaterThanOrEqualTo(Version.v1_11) && InitialData.INSTANCE.getPreVersion().lessThan(Version.v1_14)) {
            setChatVisibilityEnum(BaseReflection.getNMSClass("EntityHuman$EnumChatVisibility", "null"));
        } else {
            setChatVisibilityEnum(BaseReflection.getNMSClass("EnumChatVisibility", "net.minecraft.world.entity.player.EnumChatVisibility"));
        }

        setMainHandEnum(BaseReflection.getNMSClass("EnumMainHand", "net.minecraft.world.entity.EnumMainHand"));
        setNbtTagCompoundClass(BaseReflection.getNMSClass("NBTTagCompound", "net.minecraft.nbt.NBTTagCompound"));
        setVector3dClass(BaseReflection.getNMSClass("Vec3D", "net.minecraft.world.phys.Vec3D"));
        setAxisAlignedClass(BaseReflection.getNMSClass("AxisAlignedBB", "net.minecraft.world.phys.AxisAlignedBB"));
        setCraftEntityClass(BaseReflection.getCraftBukkitClass("entity.CraftEntity"));
        setPlayerActionEnum(BaseReflection.getNMSClass("PacketPlayInEntityAction$EnumPlayerAction", " net.minecraft.network.protocol.game.PacketPlayInEntityAction$EnumPlayerAction"));
        if(this.playerActionEnum == null) {
            setPlayerActionEnum(BaseReflection.getLegacyNMSClass("EnumPlayerAction"));
        }
        setTileEntityCommandTypeEnum(BaseReflection.getNMSClass("TileEntityCommand$Type", "net.minecraft.world.level.block.entity.TileEntityCommand$Type"));
        setTileEntityJigsawJointypeEnum(BaseReflection.getNMSClass("TileEntityJigsaw$JointType", "net.minecraft.world.level.block.entity.TileEntityJigsaw$JointType"));
        setTileEntityStructureUpdateType(BaseReflection.getNMSClass("TileEntityStructure$UpdateType", "net.minecraft.world.level.block.entity.TileEntityStructure$UpdateType"));
        setBlockPropertyStructureModeEnum(BaseReflection.getNMSClass("BlockPropertyStructureMode", "net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode"));
        setBlockMirrorEnum(BaseReflection.getNMSClass("EnumBlockMirror", "net.minecraft.world.level.block.EnumBlockMirror"));
        setBlockRotationEnum(BaseReflection.getNMSClass("EnumBlockRotation", "net.minecraft.world.level.block.EnumBlockRotation"));
        setPointOEnum(BaseReflection.getNMSClass("PointGroupO", "com.mojang.math.PointGroupO"));
        setPointSEnum(BaseReflection.getNMSClass("PointGroupS", "com.mojang.math.PointGroupS"));
        setBaseBlockPositionClass(BaseReflection.getNMSClass("BaseBlockPosition", "net.minecraft.core.BaseBlockPosition"));
        setEntityUseActionEnum(BaseReflection.getNMSClass("PacketPlayInUseEntity$EnumEntityUseAction", "net.minecraft.network.protocol.game.PacketPlayInUseEntity$EnumEntityUseAction"));
        if(this.entityUseActionEnum == null) {
            setEntityUseActionEnum(BaseReflection.getLegacyNMSClass("EnumEntityUseAction"));
        }
        setMovingObjectPositionBlockClass(BaseReflection.getNMSClass("MovingObjectPositionBlock", "net.minecraft.world.phys.MovingObjectPositionBlock"));
        setInventoryClickTypeEnum(BaseReflection.getNMSClass("InventoryClickType", "net.minecraft.world.inventory.InventoryClickType"));
        setCraftAdvancementClass(BaseReflection.getCraftBukkitClass("advancement.CraftAdvancement"));
        setMinecraftAdvancementClass(BaseReflection.getNMSClass("Advancement", "net.minecraft.advancements.Advancement"));
        setAdvancementFrameTypeEnum(BaseReflection.getNMSClass("AdvancementFrameType", "net.minecraft.advancements.AdvancementFrameType"));
        setAdvancementDisplayClass(BaseReflection.getNMSClass("AdvancementDisplay", "net.minecraft.advancements.AdvancementDisplay"));
        setChatFormatEnum(BaseReflection.getNMSClass("EnumChatFormat", "net.minecraft.EnumChatFormat"));
        setCraftAdvancementProgress(BaseReflection.getCraftBukkitClass("advancement.CraftAdvancementProgress"));
        setMinecraftAdvancementProgress(BaseReflection.getNMSClass("AdvancementProgress", "net.minecraft.advancements.AdvancementProgress"));
        setAdvancementPlayerDataClass(BaseReflection.getNMSClass("AdvancementDataPlayer", "net.minecraft.server.AdvancementDataPlayer"));
        setAttributeOperationEnum(BaseReflection.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation"));
        setResourceKeyClass(BaseReflection.getNMSClass("ResourceKey", "net.minecraft.resources.ResourceKey"));
        setInstantMobEffectClass(BaseReflection.getNMSClass("InstantMobEffect", "net.minecraft.world.effect.InstantMobEffect"));
        setMobEffectAbsorptionClass(BaseReflection.getNMSClass("MobEffectAbsorption", "net.minecraft.world.effect.MobEffectAbsorption"));
        setMobEffectAttackDamageClass(BaseReflection.getNMSClass("MobEffectAttackDamage", "net.minecraft.world.effect.MobEffectAttackDamage"));
        setMobEffectHealthBoostClass(BaseReflection.getNMSClass("MobEffectHealthBoost", "net.minecraft.world.effect.MobEffectHealthBoost"));
        setAttributeRangedClass(BaseReflection.getNMSClass("AttributeRanged", "net.minecraft.world.entity.ai.attributes.AttributeRanged"));
        setRecipeBookTypeEnum(BaseReflection.getNMSClass("RecipeBookType", "net.minecraft.world.inventory.RecipeBookType"));
        setMinecraftBlockClass(BaseReflection.getNMSClass("Block", "net.minecraft.world.level.block.Block"));
        setCraftBlockClass(BaseReflection.getCraftBukkitClass("block.CraftBlock"));
        setCraftMagicNumbersClass(BaseReflection.getCraftBukkitClass("util.CraftMagicNumbers"));
        setIBlockDataClass(BaseReflection.getNMSClass("IBlockData", "net.minecraft.world.level.block.state.IBlockData"));
        setMinecraftItemClass(BaseReflection.getNMSClass("Item", "net.minecraft.world.item.Item"));
        setEntityTypesClass(BaseReflection.getNMSClass("EntityTypes", "net.minecraft.world.entity.EntityTypes"));
        setEntityHumanClass(BaseReflection.getNMSClass("EntityHuman", "net.minecraft.world.entity.player.EntityHuman"));
        setCraftChunkClass(BaseReflection.getCraftBukkitClass("CraftChunk"));
        setMinecraftChunkClass(BaseReflection.getNMSClass("Chunk", "net.minecraft.world.level.chunk.Chunk"));
        setIChunkAccessClass(BaseReflection.getNMSClass("IChunkAccess", "net.minecraft.world.level.chunk.IChunkAccess"));
        setIBlockAccessClass(BaseReflection.getNMSClass("IBlockAccess", "net.minecraft.world.level.IBlockAccess"));
        setBossActionEnum(BaseReflection.getNMSClass("PacketPlayOutBoss$Action", "net.minecraft.network.protocol.game.PacketPlayOutBoss$d"));
        setBossActionInterface(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutBoss$Action"));
        setBarColorEnum(BaseReflection.getNMSClass("BossBattle$BarColor", "net.minecraft.world.BossBattle$BarColor"));
        setBarStyleEnum(BaseReflection.getNMSClass("BossBattle$BarStyle", "net.minecraft.world.BossBattle$BarStyle"));
        setBossBattleClass(BaseReflection.getNMSClass("BossBattle", "net.minecraft.world.BossBattle"));
        setOutCommandClassA(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$Updater"));
        setOutCommandClassB(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$b"));
        setOutCommandInterfaceE(BaseReflection.getClass("net.minecraft.network.protocol.game.PacketPlayOutCommands$e"));
        setMessageSignatureStorageClass(BaseReflection.getClass("net.minecraft.network.chat.MessageSignature$a"));
        setMinecraftEncryptionSignatureDataClass(BaseReflection.getClass("net.minecraft.util.MinecraftEncryption$b"));
    }

    private static ClassesContainer INSTANCE;
    public static void init() {
        INSTANCE = new ClassesContainer();
    }
    public static ClassesContainer get() {
        return INSTANCE;
    }
}
