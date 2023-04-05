package net.alis.protocoller.plugin.util.reflection;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinecraftReflection {

    public static Object getCraftEntity(Entity entity) {
        return ClassesContainer.get().getCraftEntityClass().cast(entity);
    }

    public static Object getMinecraftEntity(Entity entity) {
        return new AccessedObject(getCraftEntity(entity)).read(0, ClassesContainer.get().getMinecraftEntityClass());
    }

    public static Entity entityFromMinecraftEntity(Object minecraftEntity) {
        return new AccessedObject(minecraftEntity).read(0, ClassesContainer.get().getCraftEntityClass());
    }

    public static Entity entityFromCraftEntity(Object craftEntity) {
        return (Entity) craftEntity;
    }

    public static Object getCraftItemStack(ItemStack stack) {
        return ClassesContainer.get().getCraftItemStackClass().cast(stack);
    }

    public static Object getMinecraftItemStack(ItemStack stack) {
        return new AccessedObject(getCraftItemStack(stack)).read(0, ClassesContainer.get().getMinecraftItemStackClass());
    }

    public static ItemStack itemStackFromMinecraftItemStack(Object minecraftItemStack) {
        return new AccessedObject(minecraftItemStack).read(0, ClassesContainer.get().getCraftItemStackClass());
    }

    public static ItemStack itemStackFromCraftItemStack(Object craftItemStack) {
        return (ItemStack) craftItemStack;
    }

    public static Object getCraftAdvancement(Advancement advancement) {
        return ClassesContainer.get().getCraftAdvancementClass().cast(advancement);
    }

    public static Object getMinecraftAdvancement(Advancement advancement) {
        return new AccessedObject(getCraftAdvancement(advancement)).read(0, ClassesContainer.get().getMinecraftAdvancementClass());
    }

    public static Advancement advancementFromMinecraftAdvancement(Object minecraftAdvancement) {
        return new AccessedObject(minecraftAdvancement).read(0, Advancement.class);
    }

    public static Advancement advancementFromCraftAdvancement(Object craftAdvancement) {
        return (Advancement) craftAdvancement;
    }

    public static Object getCraftAdvancementProgress(AdvancementProgress progress) {
        return ClassesContainer.get().getCraftAdvancementProgress().cast(progress);
    }

    public static Object getMinecraftAdvancementProgress(AdvancementProgress progress) {
        return new AccessedObject(getCraftAdvancementProgress(progress)).read(0, ClassesContainer.get().getMinecraftAdvancementProgress());
    }

    public static @NotNull AdvancementProgress advancementProgressFromMinecraftProgress(Advancement advancement, Player player, Object minecraftAP) {
        AccessedObject entityPlayer = new AccessedObject(getEntityPlayer(player));
        return BaseReflection.callConstructor(
                BaseReflection.getConstructor(ClassesContainer.get().getCraftAdvancementProgress(), ClassesContainer.get().getCraftAdvancementClass(), ClassesContainer.get().getAdvancementPlayerDataClass(), ClassesContainer.get().getMinecraftAdvancementProgress()),
                getCraftAdvancement(advancement), entityPlayer.read(0, ClassesContainer.get().getAdvancementPlayerDataClass()), minecraftAP
        );
    }

    public static AdvancementProgress advancementProgressFromCraftProgress(Object craftProgress) {
        return (AdvancementProgress) craftProgress;
    }

    public static <CraftBlock> CraftBlock getCraftBlock(Block block) {
        return (CraftBlock) ClassesContainer.get().getCraftBlockClass().cast(block);
    }

    public static Block blockFromCraftBlock(Object craftBlock) {
        return (Block) craftBlock;
    }

    public static Object getCraftChunk(Chunk chunk) {
        return ClassesContainer.get().getCraftChunkClass().cast(chunk);
    }

    public static Object getMinecraftChunk(Chunk chunk) {
        return ((WeakReference<?>) BaseReflection.readField(getCraftChunk(chunk), 0, WeakReference.class)).get();
    }

    public static Chunk chunkFromMinecraftChunk(Object minecraftChunk) {
        return BaseReflection.readField(minecraftChunk, 0, Chunk.class);
    }

    public static Chunk chunkFromCraftChunk(Object craftChunk) {
        return (Chunk) craftChunk;
    }

    public static Object getCraftPlayer(Player player) {
        return ClassesContainer.get().getCraftPlayerClass().cast(player);
    }

    public static Object getEntityPlayer(Player player) {
        Object craftPlayer = getCraftPlayer(player);
        return BaseReflection.callMethod(craftPlayer, BaseReflection.getMethod(craftPlayer.getClass(), "getHandle", new Class[]{}), craftPlayer);
    }

    public static Object getEntityPlayer(Object craftPlayer) {
        return BaseReflection.callMethod(craftPlayer, BaseReflection.getMethod(craftPlayer.getClass(), "getHandle", new Class[]{}), craftPlayer);
    }

    public static Object getPlayerConnection(@NotNull Player player) {
        return new AccessedObject(getEntityPlayer(player)).read(0, ClassesContainer.get().getPlayerConnectionClass());
    }

    public static Object getPlayerConnection(@NotNull Object entityPlayer) {
        return new AccessedObject(entityPlayer).read(0, ClassesContainer.get().getPlayerConnectionClass());
    }

    public static Object getPlayerNetworkManager(@NotNull Player player) {
        return new AccessedObject(getPlayerConnection(player)).read(0, ClassesContainer.get().getNetworkManagerClass());
    }

    public static Object getPlayerNetworkManager(@NotNull Object playerConnection) {
        return new AccessedObject(playerConnection).read(0, ClassesContainer.get().getNetworkManagerClass());
    }

    public static Channel getPlayerChannel(@NotNull Player player) {
        return new AccessedObject(getPlayerNetworkManager(player)).read(0, Channel.class);
    }

    public static Channel getPlayerChannel(@NotNull Object networkManager) {
        return new AccessedObject(networkManager).read(0, Channel.class);
    }

    public static ChannelPipeline getChannelPipeline(Player player) {
        return getPlayerChannel(player).pipeline();
    }

    public static ChannelPipeline getChannelPipeline(@NotNull Channel channel) {
        return channel.pipeline();
    }

    public static int getPlayerPing(Player player) {
        if(GlobalProvider.instance().getServer().isLegacy()) {
            return BaseReflection.readField(getEntityPlayer(player), "ping");
        } else {
            return player.getPing();
        }
    }

    public static int getServerProtocolVersion() {
        Class<?> sharedConstants; int protocol = 0;
        if(InitialData.INSTANCE.isLegacyServer()) {
            sharedConstants = BaseReflection.getLegacyNMSClass("SharedConstants", false);
        } else {
            sharedConstants = BaseReflection.getNMClass("SharedConstants", false);
        }
        try {
            protocol = BaseReflection.callInterfaceMethod(sharedConstants, 0, int.class);
        } catch (Exception e) {
            protocol = Version.fromPackageName(InitialData.INSTANCE.getPackageVersion()).getProtocol();
        }
        return protocol;
    }

    public static @Nullable Object getMinecraftServer() {
        return BaseReflection.callInterfaceMethod(ClassesContainer.get().getMinecraftServerClass(), 0, "getServer");
    }

    public static Object getCraftServer(Server server) {
        return ClassesContainer.get().getCraftServerClass().cast(server);
    }

    public static Object getServerConnection() {
        Object server = getMinecraftServer();
        if(server.getClass() == ClassesContainer.get().getDedicatedServerClass()) {
            Object fromDedicated = ClassesContainer.get().getMinecraftServerClass().cast(server);
            return BaseReflection.readSuperclassField(fromDedicated, 0, ClassesContainer.get().getServerConnectionClass());
        }
        return BaseReflection.readField(server, 0, ClassesContainer.get().getServerConnectionClass());
    }

    public static @NotNull List<Object> getServerChannelFutures() {
        List<Object> list = Collections.synchronizedList(new ArrayList<Object>());
        list.addAll(BaseReflection.readField(getServerConnection(), 0, List.class));
        return list;
    }

    public static @NotNull List<Object> getServerNetworkManagers() {
        List<Object> list = Collections.synchronizedList(Lists.newArrayList());
        list.addAll(BaseReflection.readField(getServerConnection(), 1, List.class));
        return list;
    }

}
