package net.alis.protocoller.plugin.util.reflection;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.util.AccessedObject;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinecraftReflection {

    public static Object getCraftEntity(Entity entity) {
        return ClassAccessor.get().getCraftEntityClass().cast(entity);
    }

    public static Object getMinecraftEntity(Entity entity) {
        return new AccessedObject(getCraftEntity(entity)).readField(0, ClassAccessor.get().getMinecraftEntityClass());
    }

    public static Entity entityFromMinecraftEntity(Object minecraftEntity) {
        return new AccessedObject(minecraftEntity).readField(0, ClassAccessor.get().getCraftEntityClass());
    }

    public static Entity entityFromCraftEntity(Object craftEntity) {
        return (Entity) craftEntity;
    }

    public static Object getCraftItemStack(ItemStack stack) {
        return ClassAccessor.get().getCraftItemStackClass().cast(stack);
    }

    public static Object getMinecraftItemStack(ItemStack stack) {
        return new AccessedObject(getCraftItemStack(stack)).readField(0, ClassAccessor.get().getMinecraftItemStackClass());
    }

    public static ItemStack itemStackFromMinecraftItemStack(Object minecraftItemStack) {
        return new AccessedObject(minecraftItemStack).readField(0, ClassAccessor.get().getCraftItemStackClass());
    }

    public static ItemStack itemStackFromCraftItemStack(Object craftItemStack) {
        return (ItemStack) craftItemStack;
    }

    public static Object getCraftAdvancement(Advancement advancement) {
        return ClassAccessor.get().getCraftAdvancementClass().cast(advancement);
    }

    public static Object getMinecraftAdvancement(Advancement advancement) {
        return new AccessedObject(getCraftAdvancement(advancement)).readField(0, ClassAccessor.get().getMinecraftAdvancementClass());
    }

    public static Advancement advancementFromMinecraftAdvancement(Object minecraftAdvancement) {
        return new AccessedObject(minecraftAdvancement).readField(0, Advancement.class);
    }

    public static Advancement advancementFromCraftAdvancement(Object craftAdvancement) {
        return (Advancement) craftAdvancement;
    }

    public static Object getCraftAdvancementProgress(AdvancementProgress progress) {
        return ClassAccessor.get().getCraftAdvancementProgress().cast(progress);
    }

    public static Object getMinecraftAdvancementProgress(AdvancementProgress progress) {
        return new AccessedObject(getCraftAdvancementProgress(progress)).readField(0, ClassAccessor.get().getMinecraftAdvancementProgress());
    }

    public static @NotNull AdvancementProgress advancementProgressFromMinecraftProgress(Advancement advancement, Player player, Object minecraftAP) {
        AccessedObject entityPlayer = new AccessedObject(getEntityPlayer(player));
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getCraftAdvancementProgress(), false, ClassAccessor.get().getCraftAdvancementClass(), ClassAccessor.get().getAdvancementPlayerDataClass(), ClassAccessor.get().getMinecraftAdvancementProgress()),
                getCraftAdvancement(advancement), entityPlayer.readField(0, ClassAccessor.get().getAdvancementPlayerDataClass()), minecraftAP
        );
    }

    public static AdvancementProgress advancementProgressFromCraftProgress(Object craftProgress) {
        return (AdvancementProgress) craftProgress;
    }

    public static <CraftBlock> CraftBlock getCraftBlock(Block block) {
        return (CraftBlock) ClassAccessor.get().getCraftBlockClass().cast(block);
    }

    public static Block blockFromCraftBlock(Object craftBlock) {
        return (Block) craftBlock;
    }

    public static Object getCraftChunk(Chunk chunk) {
        return ClassAccessor.get().getCraftChunkClass().cast(chunk);
    }

    public static Object getMinecraftChunk(Chunk chunk) {
        return ((WeakReference<?>) Reflect.readField(getCraftChunk(chunk), 0, WeakReference.class, false)).get();
    }

    public static Chunk chunkFromMinecraftChunk(Object minecraftChunk) {
        return Reflect.readField(minecraftChunk, 0, Chunk.class, false);
    }

    public static Chunk chunkFromCraftChunk(Object craftChunk) {
        return (Chunk) craftChunk;
    }

    public static Object getCraftPlayer(Player player) {
        return ClassAccessor.get().getCraftPlayerClass().cast(player);
    }

    public static Object getEntityPlayer(Player player) {
        Object craftPlayer = getCraftPlayer(player);
        return Reflect.callMethod(craftPlayer, Reflect.getMethod(craftPlayer.getClass(), "getHandle", new Class[]{}), false);
    }

    public static Object getEntityPlayer(Object craftPlayer) {
        return Reflect.callMethod(craftPlayer, Reflect.getMethod(craftPlayer.getClass(), "getHandle", new Class[]{}), false);
    }

    public static Object getPlayerConnection(@NotNull Player player) {
        return new AccessedObject(getEntityPlayer(player)).readField(0, ClassAccessor.get().getPlayerConnectionClass());
    }

    public static Object getPlayerConnection(@NotNull Object entityPlayer) {
        return new AccessedObject(entityPlayer).readField(0, ClassAccessor.get().getPlayerConnectionClass());
    }

    public static Object getPlayerNetworkManager(@NotNull Player player) {
        return new AccessedObject(getPlayerConnection(player)).readField(0, ClassAccessor.get().getNetworkManagerClass());
    }

    public static Object getPlayerNetworkManager(@NotNull Object playerConnection) {
        return new AccessedObject(playerConnection).readField(0, ClassAccessor.get().getNetworkManagerClass());
    }

    public static Channel getPlayerChannel(@NotNull Player player) {
        return new AccessedObject(getPlayerNetworkManager(player)).readField(0, Channel.class);
    }

    public static Channel getPlayerChannel(@NotNull Object networkManager) {
        return new AccessedObject(networkManager).readField(0, Channel.class);
    }

    public static ChannelPipeline getChannelPipeline(Player player) {
        return getPlayerChannel(player).pipeline();
    }

    public static ChannelPipeline getChannelPipeline(@NotNull Channel channel) {
        return channel.pipeline();
    }

    public static int getPlayerPing(Player player) {
        if(IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_17)) {
            return Reflect.readField(getEntityPlayer(player), "ping", false);
        } else {
            return player.getPing();
        }
    }

    public static int getServerProtocolVersion() {
        Class<?> sharedConstants; int protocol = 0;
        if(ApproximateData.get().isLegacyServer()) {
            sharedConstants = Reflect.getLegacyNMSClass("SharedConstants", false);
        } else {
            sharedConstants = Reflect.getNMClass("SharedConstants", false);
        }
        try {
            protocol = Reflect.callInterfaceMethod(sharedConstants, 0, int.class, false);
        } catch (Exception e) {
            protocol = Version.fromPackageName(ApproximateData.get().getPackageVersion()).getProtocol();
        }
        return protocol;
    }

    public static @Nullable Object getMinecraftServer() {
        return Reflect.callInterfaceMethod(ClassAccessor.get().getMinecraftServerClass(), 0, "getServer");
    }

    public static Object getCraftServer(Server server) {
        return ClassAccessor.get().getCraftServerClass().cast(server);
    }

    public static Object getServerConnection() {
        Object server = getMinecraftServer();
        if(server.getClass() == ClassAccessor.get().getDedicatedServerClass()) {
            Object fromDedicated = ClassAccessor.get().getMinecraftServerClass().cast(server);
            return Reflect.readSuperclassField(fromDedicated, 0, ClassAccessor.get().getServerConnectionClass(), false);
        }
        return Reflect.readField(server, 0, ClassAccessor.get().getServerConnectionClass(), false);
    }

    public static Object getServerConnection(Object server) {
        if(server.getClass() == ClassAccessor.get().getDedicatedServerClass()) {
            Object fromDedicated = ClassAccessor.get().getMinecraftServerClass().cast(server);
            return Reflect.readSuperclassField(fromDedicated, 0, ClassAccessor.get().getServerConnectionClass(), false);
        }
        return Reflect.readField(server, 0, ClassAccessor.get().getServerConnectionClass(), false);
    }

    public static @NotNull List<Object> getServerChannelFutures() {
        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        list.addAll(Reflect.readField(getServerConnection(), 0, List.class, false));
        return list;
    }

    public static @NotNull List<Object> getServerNetworkManagers() {
        List<Object> list = Collections.synchronizedList(Lists.newArrayList());
        list.addAll(Reflect.readField(getServerConnection(), 1, List.class, false));
        return list;
    }

}
