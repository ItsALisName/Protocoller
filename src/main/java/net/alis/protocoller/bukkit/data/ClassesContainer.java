package net.alis.protocoller.bukkit.data;

import lombok.Getter;
import lombok.Setter;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.util.reflection.Reflection;

public class ClassesContainer {

    public static ClassesContainer INSTANCE;

    private @Getter @Setter Class<?>
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
            chatVisibilityEnum, mainHandEnum, nbtTagCompoundClass, vector3dClass;

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
        setPlayerActionEnum(Reflection.getNMSClass("EnumPlayerAction", "net.minecraft.network.protocol.game.PacketPlayInEntityAction$EnumPlayerAction"));
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
    }

    public static void init() {
        INSTANCE = new ClassesContainer();
    }
    
}
