package net.alis.protocoller.bukkit.data;

import lombok.Setter;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

public class ClassesContainer {

    public static ClassesContainer INSTANCE;

    private @Setter Class<?>
        iChatBaseComponentClass, suggestionClass, suggestionsClass, stringRangeClass, stringReaderClass,
        chatSerializerClass, argumentTypeClass, argumentBuilderClass, literalArgumentBuilderClass, requiredArgumentBuilderClass,
        commandContextClass, commandContextBuilderClass, parsedArgumentClass, parsedCommandNodeClass, suggestionContextClass,
        integerSuggestionClass, suggestionProviderClass, suggestionsBuilderClass, argumentCommandNodeClass, commandNodeClass,
        literalCommandNodeClass, rootCommandNodeClass, ambiguityConsumerClass, commandClass, commandDispatcherClass,literalMessageClass,
        parseResultsClass, redirectModifierClass, resultConsumerClass, singleRedirectModifierClass, kyoriComponentClass,
        kyoriGsonComponentSerializerClass, chatMessageTypeClass, playerConnectionClass, networkManagerClass, minecraftServerClass,
        serverConnectionClass, craftServerClass, dedicatedServerClass, packetDataSerializerClass, gameProfileClass, propertyClass,
        propertyMapClass, minecraftKeyClass, craftWorldClass, minecraftWorldClass, minecraftWorldServerClass, craftWorldBorderClass,
        minecraftWorldBorderClass, entityPlayerClass, serverPingClass, vibrationPathClass, playerAbilitiesClass, iRecipeClass, directionEnum,
        playerDigTypeEnum, blockPositionClass, minecraftItemStackClass, handEnum, clientCommandEnum, difficultyEnum, playerActionEnum,
        minecraftEntityClass, protocolEnum, advancementsStatusEnum, craftChatMessageClass, serverDataClass, serverPingPlayerSampleClass,
        iChatMutableComponent, soundCategoryEnum, attributeBaseClass, attributeModifierClass, operationClass, factorCalculationDataClass,
        mobEffectClass, mobEffectListClass, mobEffectInfoEnum, playerTeleportFlagsEnum, craftItemStackClass, craftPlayerClass, resourcePackStatusEnum,
        chatVisibilityEnum, mainHandEnum, nbtTagCompoundClass, vector3dClass, axisAlignedClass, craftEntityClass, tileEntityCommandTypeEnum,
        tileEntityJigsawJointypeEnum, tileEntityStructureUpdateType, blockPropertyStructureModeEnum, blockMirrorEnum, pointOEnum, pointSEnum, blockRotationEnum,
        baseBlockPositionClass, entityUseActionEnum, movingObjectPositionBlockClass;
    ClassesContainer() {
        setSuggestionClass(Reflection.getClass("com.mojang.brigadier.suggestion.Suggestion"));
        setSuggestionsClass(Reflection.getClass("com.mojang.brigadier.suggestion.Suggestions"));
        setStringRangeClass(Reflection.getClass("com.mojang.brigadier.context.StringRange"));
        setIChatBaseComponentClass(Reflection.getNMSClass("IChatBaseComponent", "net.minecraft.network.chat.IChatBaseComponent"));
        setStringReaderClass(Reflection.getClass("com.mojang.brigadier.StringReader"));
        setChatSerializerClass(Reflection.getSubClass(getIChatBaseComponentClass(), "ChatSerializer"));
        setArgumentTypeClass(Reflection.getClass("com.mojang.brigadier.arguments.ArgumentType"));
        setArgumentBuilderClass(Reflection.getClass("com.mojang.brigadier.builder.ArgumentBuilder"));
        setLiteralArgumentBuilderClass(Reflection.getClass("com.mojang.brigadier.builder.LiteralArgumentBuilder"));
        setRequiredArgumentBuilderClass(Reflection.getClass("com.mojang.brigadier.builder.RequiredArgumentBuilder"));
        setCommandContextClass(Reflection.getClass("com.mojang.brigadier.context.CommandContext"));
        setCommandContextBuilderClass(Reflection.getClass("com.mojang.brigadier.context.CommandContextBuilder"));
        setParsedArgumentClass(Reflection.getClass("com.mojang.brigadier.context.ParsedArgument"));
        setParsedCommandNodeClass(Reflection.getClass("com.mojang.brigadier.context.ParsedCommandNode"));
        setSuggestionContextClass(Reflection.getClass("com.mojang.brigadier.context.SuggestionContext"));
        setIntegerSuggestionClass(Reflection.getClass("com.mojang.brigadier.suggestion.IntegerSuggestion"));
        setSuggestionProviderClass(Reflection.getClass("com.mojang.brigadier.suggestion.SuggestionProvider"));
        setSuggestionsBuilderClass(Reflection.getClass("com.mojang.brigadier.suggestion.SuggestionsBuilder"));
        setArgumentCommandNodeClass(Reflection.getClass("com.mojang.brigadier.tree.ArgumentCommandNode"));
        setLiteralCommandNodeClass(Reflection.getClass("com.mojang.brigadier.tree.LiteralCommandNode"));
        setRootCommandNodeClass(Reflection.getClass("com.mojang.brigadier.tree.RootCommandNode"));
        setAmbiguityConsumerClass(Reflection.getClass("com.mojang.brigadier.AmbiguityConsumer"));
        setCommandClass(Reflection.getClass("com.mojang.brigadier.Command"));
        setCommandDispatcherClass(Reflection.getClass("com.mojang.brigadier.CommandDispatcher"));
        setLiteralMessageClass(Reflection.getClass("com.mojang.brigadier.LiteralMessage"));
        setParseResultsClass(Reflection.getClass("com.mojang.brigadier.ParseResults"));
        setRedirectModifierClass(Reflection.getClass("com.mojang.brigadier.RedirectModifier"));
        setResultConsumerClass(Reflection.getClass("com.mojang.brigadier.ResultConsumer"));
        setSingleRedirectModifierClass(Reflection.getClass("com.mojang.brigadier.SingleRedirectModifier"));
        setCommandNodeClass(Reflection.getClass("com.mojang.brigadier.tree.CommandNode"));
        setKyoriComponentClass(Reflection.getClass("net.kyori.adventure.text.Component"));
        setKyoriGsonComponentSerializerClass(Reflection.getClass("net.kyori.adventure.text.serializer.gson.GsonComponentSerializer"));
        setCraftServerClass(Reflection.getCraftBukkitClass("CraftServer"));
        setChatMessageTypeClass(Reflection.getNMSClass("ChatMessageType", "net.minecraft.network.chat.ChatMessageType"));
        setPlayerConnectionClass(Reflection.getNMSClass("PlayerConnection", "net.minecraft.server.network.PlayerConnection"));
        setNetworkManagerClass(Reflection.getNMSClass("NetworkManager", "net.minecraft.network.NetworkManager"));
        setMinecraftServerClass(Reflection.getNMSClass("MinecraftServer", "net.minecraft.server.MinecraftServer"));
        setServerConnectionClass(Reflection.getNMSClass("ServerConnection", "net.minecraft.server.network.ServerConnection"));
        setDedicatedServerClass(Reflection.getNMSClass("DedicatedServer", "net.minecraft.server.dedicated.DedicatedServer"));
        setPacketDataSerializerClass(Reflection.getNMSClass("PacketDataSerializer", "net.minecraft.network.PacketDataSerializer"));
        setGameProfileClass(Reflection.getClass("com.mojang.authlib.GameProfile"));
        setPropertyClass(Reflection.getClass("com.mojang.authlib.properties.Property"));
        setPropertyMapClass(Reflection.getClass("com.mojang.authlib.properties.PropertyMap"));
        setMinecraftKeyClass(Reflection.getNMSClass("MinecraftKey", "net.minecraft.resources.MinecraftKey"));
        setCraftWorldClass(Reflection.getCraftBukkitClass("CraftWorld"));
        setMinecraftWorldClass(Reflection.getNMSClass("World", "net.minecraft.world.level.World"));
        setMinecraftWorldServerClass(Reflection.getNMSClass("WorldServer", "net.minecraft.server.level.WorldServer"));
        setCraftWorldBorderClass(Reflection.getCraftBukkitClass("CraftWorldBorder"));
        setMinecraftWorldBorderClass(Reflection.getNMSClass("WorldBorder", "net.minecraft.world.level.border.WorldBorder"));
        setEntityPlayerClass(Reflection.getNMSClass("EntityPlayer", "net.minecraft.server.level.EntityPlayer"));
        setServerPingClass(Reflection.getNMSClass("ServerPing", "net.minecraft.network.protocol.status.ServerPing"));
        setVibrationPathClass(Reflection.getClass("net.minecraft.world.level.gameevent.vibrations.VibrationPath"));
        setPlayerAbilitiesClass(Reflection.getNMSClass("PlayerAbilities", "net.minecraft.world.entity.player.PlayerAbilities"));
        setIRecipeClass(Reflection.getNMSClass("IRecipe", "net.minecraft.world.item.crafting.IRecipe"));
        setDirectionEnum(Reflection.getNMSClass("EnumDirection", "net.minecraft.core.EnumDirection"));
        setPlayerDigTypeEnum(Reflection.getNMSClass("PacketPlayInBlockDig$EnumPlayerDigType", "net.minecraft.network.protocol.game.PacketPlayInBlockDig$EnumPlayerDigType"));
        setBlockPositionClass(Reflection.getNMSClass("BlockPosition", "net.minecraft.core.BlockPosition"));
        setMinecraftItemStackClass(Reflection.getNMSClass("ItemStack", "net.minecraft.world.item.ItemStack"));
        setHandEnum(Reflection.getNMSClass("EnumHand", "net.minecraft.world.EnumHand"));
        setClientCommandEnum(Reflection.getNMSClass("EnumClientCommand", "net.minecraft.network.protocol.game.PacketPlayInClientCommand$EnumClientCommand"));
        setDifficultyEnum(Reflection.getNMSClass("EnumDifficulty", "net.minecraft.world.EnumDifficulty"));
        setMinecraftEntityClass(Reflection.getNMSClass("Entity", "net.minecraft.world.entity.Entity"));
        setProtocolEnum(Reflection.getNMSClass("EnumProtocol", "net.minecraft.network.EnumProtocol"));
        setAdvancementsStatusEnum(Reflection.getNMSClass("PacketPlayInAdvancements$Status", "net.minecraft.network.protocol.game.PacketPlayInAdvancements$Status"));
        setCraftChatMessageClass(Reflection.getCraftBukkitClass("util.CraftChatMessage"));
        setServerPingPlayerSampleClass(Reflection.getNMSClass("ServerPing$ServerPingPlayerSample", "net.minecraft.network.protocol.status.ServerPing$ServerPingPlayerSample"));
        setServerDataClass(Reflection.getNMSClass("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData"));
        setIChatMutableComponent(Reflection.getNMSClass("IChatMutableComponent", "net.minecraft.network.chat.IChatMutableComponent"));
        setSoundCategoryEnum(Reflection.getNMSClass("SoundCategory", "net.minecraft.sounds.SoundCategory"));
        setAttributeBaseClass(Reflection.getNMSClass("AttributeBase", "net.minecraft.world.entity.ai.attributes.AttributeBase"));
        setAttributeModifierClass(Reflection.getNMSClass("AttributeModifier", "net.minecraft.world.entity.ai.attributes.AttributeModifier"));
        setOperationClass(Reflection.getNMSClass("AttributeModifier$Operation", "net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation"));
        setMobEffectClass(Reflection.getNMSClass("MobEffect", "net.minecraft.world.effect.MobEffect"));
        setFactorCalculationDataClass(Reflection.getNMSClass("MobEffect$a", "net.minecraft.world.effect.MobEffect$a"));
        setMobEffectListClass(Reflection.getNMSClass("MobEffectList", "net.minecraft.world.effect.MobEffectList"));
        setMobEffectInfoEnum(Reflection.getNMSClass("MobEffectInfo", "net.minecraft.world.effect.MobEffectInfo"));
        setPlayerTeleportFlagsEnum(Reflection.getNMSClass("PacketPlayOutPosition$EnumPlayerTeleportFlags", "net.minecraft.network.protocol.game.PacketPlayOutPosition$EnumPlayerTeleportFlags"));
        setCraftItemStackClass(Reflection.getCraftBukkitClass("inventory.CraftItemStack"));
        setCraftPlayerClass(Reflection.getCraftBukkitClass("entity.CraftPlayer"));
        setResourcePackStatusEnum(Reflection.getNMSClass("PacketPlayInResourcePackStatus$EnumResourcePackStatus", "net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus$EnumResourcePackStatus"));
        if(InitialData.INSTANCE.getPreVersion().greaterThanOrEqualTo(Version.v1_11) && InitialData.INSTANCE.getPreVersion().lessThan(Version.v1_14)) {
            setChatVisibilityEnum(Reflection.getNMSClass("EntityHuman$EnumChatVisibility", "null"));
        } else {
            setChatVisibilityEnum(Reflection.getNMSClass("EnumChatVisibility", "net.minecraft.world.entity.player.EnumChatVisibility"));
        }
        setMainHandEnum(Reflection.getNMSClass("EnumMainHand", "net.minecraft.world.entity.EnumMainHand"));
        setNbtTagCompoundClass(Reflection.getNMSClass("NBTTagCompound", "net.minecraft.nbt.NBTTagCompound"));
        setVector3dClass(Reflection.getNMSClass("Vec3D", "net.minecraft.world.phys.Vec3D"));
        setAxisAlignedClass(Reflection.getNMSClass("AxisAlignedBB", "net.minecraft.world.phys.AxisAlignedBB"));
        setCraftEntityClass(Reflection.getCraftBukkitClass("entity.CraftEntity"));
        setPlayerActionEnum(Reflection.getNMSClass("PacketPlayInEntityAction$EnumPlayerAction", " net.minecraft.network.protocol.game.PacketPlayInEntityAction$EnumPlayerAction"));
        if(this.playerActionEnum == null) {
            setPlayerActionEnum(Reflection.getLegacyNMSClass("EnumPlayerAction"));
        }
        setTileEntityCommandTypeEnum(Reflection.getNMSClass("TileEntityCommand$Type", "net.minecraft.world.level.block.entity.TileEntityCommand$Type"));
        setTileEntityJigsawJointypeEnum(Reflection.getNMSClass("TileEntityJigsaw$JointType", "net.minecraft.world.level.block.entity.TileEntityJigsaw$JointType"));
        setTileEntityStructureUpdateType(Reflection.getNMSClass("TileEntityStructure$UpdateType", "net.minecraft.world.level.block.entity.TileEntityStructure$UpdateType"));
        setBlockPropertyStructureModeEnum(Reflection.getNMSClass("BlockPropertyStructureMode", "net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode"));
        setBlockMirrorEnum(Reflection.getNMSClass("EnumBlockMirror", "net.minecraft.world.level.block.EnumBlockMirror"));
        setBlockRotationEnum(Reflection.getNMSClass("EnumBlockRotation", "net.minecraft.world.level.block.EnumBlockRotation"));
        setPointOEnum(Reflection.getNMSClass("PointGroupO", "com.mojang.math.PointGroupO"));
        setPointSEnum(Reflection.getNMSClass("PointGroupS", "com.mojang.math.PointGroupS"));
        setBaseBlockPositionClass(Reflection.getNMSClass("BaseBlockPosition", "net.minecraft.core.BaseBlockPosition"));
        setEntityUseActionEnum(Reflection.getNMSClass("PacketPlayInUseEntity$EnumEntityUseAction", "net.minecraft.network.protocol.game.PacketPlayInUseEntity$EnumEntityUseAction"));
        if(this.entityUseActionEnum == null) {
            setEntityUseActionEnum(Reflection.getLegacyNMSClass("EnumEntityUseAction"));
        }
        setMovingObjectPositionBlockClass(Reflection.getNMSClass("MovingObjectPositionBlock", "net.minecraft.world.phys.MovingObjectPositionBlock"));
    }

    public static void init() {
        INSTANCE = new ClassesContainer();
    }


    public Class<?> getIChatBaseComponentClass() {
        return iChatBaseComponentClass;
    }

    public Class<?> getSuggestionClass() {
        return suggestionClass;
    }

    public Class<?> getSuggestionsClass() {
        return suggestionsClass;
    }

    public Class<?> getStringRangeClass() {
        return stringRangeClass;
    }

    public Class<?> getStringReaderClass() {
        return stringReaderClass;
    }

    public Class<?> getChatSerializerClass() {
        return chatSerializerClass;
    }

    public Class<?> getArgumentTypeClass() {
        return argumentTypeClass;
    }

    public Class<?> getArgumentBuilderClass() {
        return argumentBuilderClass;
    }

    public Class<?> getLiteralArgumentBuilderClass() {
        return literalArgumentBuilderClass;
    }

    public Class<?> getRequiredArgumentBuilderClass() {
        return requiredArgumentBuilderClass;
    }

    public Class<?> getCommandContextClass() {
        return commandContextClass;
    }

    public Class<?> getCommandContextBuilderClass() {
        return commandContextBuilderClass;
    }

    public Class<?> getParsedArgumentClass() {
        return parsedArgumentClass;
    }

    public Class<?> getParsedCommandNodeClass() {
        return parsedCommandNodeClass;
    }

    public Class<?> getSuggestionContextClass() {
        return suggestionContextClass;
    }

    public Class<?> getIntegerSuggestionClass() {
        return integerSuggestionClass;
    }

    public Class<?> getSuggestionProviderClass() {
        return suggestionProviderClass;
    }

    public Class<?> getSuggestionsBuilderClass() {
        return suggestionsBuilderClass;
    }

    public Class<?> getArgumentCommandNodeClass() {
        return argumentCommandNodeClass;
    }

    public Class<?> getCommandNodeClass() {
        return commandNodeClass;
    }

    public Class<?> getLiteralCommandNodeClass() {
        return literalCommandNodeClass;
    }

    public Class<?> getRootCommandNodeClass() {
        return rootCommandNodeClass;
    }

    public Class<?> getAmbiguityConsumerClass() {
        return ambiguityConsumerClass;
    }

    public Class<?> getCommandClass() {
        return commandClass;
    }

    public Class<?> getCommandDispatcherClass() {
        return commandDispatcherClass;
    }

    public Class<?> getLiteralMessageClass() {
        return literalMessageClass;
    }

    public Class<?> getParseResultsClass() {
        return parseResultsClass;
    }

    public Class<?> getRedirectModifierClass() {
        return redirectModifierClass;
    }

    public Class<?> getResultConsumerClass() {
        return resultConsumerClass;
    }

    public Class<?> getSingleRedirectModifierClass() {
        return singleRedirectModifierClass;
    }

    public Class<?> getKyoriComponentClass() {
        return kyoriComponentClass;
    }

    public Class<?> getKyoriGsonComponentSerializerClass() {
        return kyoriGsonComponentSerializerClass;
    }

    public Class<?> getChatMessageTypeClass() {
        return chatMessageTypeClass;
    }

    public Class<?> getPlayerConnectionClass() {
        return playerConnectionClass;
    }

    public Class<?> getNetworkManagerClass() {
        return networkManagerClass;
    }

    public Class<?> getMinecraftServerClass() {
        return minecraftServerClass;
    }

    public Class<?> getServerConnectionClass() {
        return serverConnectionClass;
    }

    public Class<?> getCraftServerClass() {
        return craftServerClass;
    }

    public Class<?> getDedicatedServerClass() {
        return dedicatedServerClass;
    }

    public Class<?> getPacketDataSerializerClass() {
        return packetDataSerializerClass;
    }

    public Class<?> getGameProfileClass() {
        return gameProfileClass;
    }

    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    public Class<?> getPropertyMapClass() {
        return propertyMapClass;
    }

    public Class<?> getMinecraftKeyClass() {
        return minecraftKeyClass;
    }

    public Class<?> getCraftWorldClass() {
        return craftWorldClass;
    }

    public Class<?> getMinecraftWorldClass() {
        return minecraftWorldClass;
    }

    public Class<?> getMinecraftWorldServerClass() {
        return minecraftWorldServerClass;
    }

    public Class<?> getCraftWorldBorderClass() {
        return craftWorldBorderClass;
    }

    public Class<?> getMinecraftWorldBorderClass() {
        return minecraftWorldBorderClass;
    }

    public Class<?> getEntityPlayerClass() {
        return entityPlayerClass;
    }

    public Class<?> getServerPingClass() {
        return serverPingClass;
    }

    public Class<?> getVibrationPathClass() {
        return vibrationPathClass;
    }

    public Class<?> getPlayerAbilitiesClass() {
        return playerAbilitiesClass;
    }

    public Class<?> getIRecipeClass() {
        return iRecipeClass;
    }

    public Class<?> getDirectionEnum() {
        return directionEnum;
    }

    public Class<?> getPlayerDigTypeEnum() {
        return playerDigTypeEnum;
    }

    public Class<?> getBlockPositionClass() {
        return blockPositionClass;
    }

    public Class<?> getMinecraftItemStackClass() {
        return minecraftItemStackClass;
    }

    public Class<?> getHandEnum() {
        return handEnum;
    }

    public Class<?> getClientCommandEnum() {
        return clientCommandEnum;
    }

    public Class<?> getDifficultyEnum() {
        return difficultyEnum;
    }

    public Class<?> getPlayerActionEnum() {
        return playerActionEnum;
    }

    public Class<?> getMinecraftEntityClass() {
        return minecraftEntityClass;
    }

    public Class<?> getProtocolEnum() {
        return protocolEnum;
    }

    public Class<?> getAdvancementsStatusEnum() {
        return advancementsStatusEnum;
    }

    public Class<?> getCraftChatMessageClass() {
        return craftChatMessageClass;
    }

    public Class<?> getServerDataClass() {
        return serverDataClass;
    }

    public Class<?> getServerPingPlayerSampleClass() {
        return serverPingPlayerSampleClass;
    }

    public Class<?> getiChatMutableComponent() {
        return iChatMutableComponent;
    }

    public Class<?> getSoundCategoryEnum() {
        return soundCategoryEnum;
    }

    public Class<?> getAttributeBaseClass() {
        return attributeBaseClass;
    }

    public Class<?> getAttributeModifierClass() {
        return attributeModifierClass;
    }

    public Class<?> getOperationClass() {
        return operationClass;
    }

    public Class<?> getFactorCalculationDataClass() {
        return factorCalculationDataClass;
    }

    public Class<?> getMobEffectClass() {
        return mobEffectClass;
    }

    public Class<?> getMobEffectListClass() {
        return mobEffectListClass;
    }

    public Class<?> getMobEffectInfoEnum() {
        return mobEffectInfoEnum;
    }

    public Class<?> getPlayerTeleportFlagsEnum() {
        return playerTeleportFlagsEnum;
    }

    public Class<?> getCraftItemStackClass() {
        return craftItemStackClass;
    }

    public Class<?> getCraftPlayerClass() {
        return craftPlayerClass;
    }

    public Class<?> getResourcePackStatusEnum() {
        return resourcePackStatusEnum;
    }

    public Class<?> getChatVisibilityEnum() {
        return chatVisibilityEnum;
    }

    public Class<?> getMainHandEnum() {
        return mainHandEnum;
    }

    public Class<?> getNbtTagCompoundClass() {
        return nbtTagCompoundClass;
    }

    public Class<?> getVector3dClass() {
        return vector3dClass;
    }

    public Class<?> getAxisAlignedClass() {
        return axisAlignedClass;
    }

    public Class<?> getCraftEntityClass() {
        return craftEntityClass;
    }

    public Class<?> getTileEntityCommandTypeEnum() {
        return tileEntityCommandTypeEnum;
    }

    public Class<?> getTileEntityJigsawJointypeEnum() {
        return tileEntityJigsawJointypeEnum;
    }

    public Class<?> getTileEntityStructureUpdateType() {
        return tileEntityStructureUpdateType;
    }

    public Class<?> getBlockPropertyStructureModeEnum() {
        return blockPropertyStructureModeEnum;
    }

    public Class<?> getBlockMirrorEnum() {
        return blockMirrorEnum;
    }

    public Class<?> getPointOEnum() {
        return pointOEnum;
    }

    public Class<?> getPointSEnum() {
        return pointSEnum;
    }

    public Class<?> getBlockRotationEnum() {
        return blockRotationEnum;
    }

    public Class<?> getBaseBlockPositionClass() {
        return baseBlockPositionClass;
    }

    public Class<?> getEntityUseActionEnum() {
        return entityUseActionEnum;
    }

    public Class<?> getMovingObjectPositionBlockClass() {
        return movingObjectPositionBlockClass;
    }
}
