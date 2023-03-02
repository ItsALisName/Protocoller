package net.alis.protocoller.bukkit.util.reflection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PlayerReflection {

    public static Object getCraftPlayer(Player player) {
        return ClassesContainer.INSTANCE.getCraftPlayerClass().cast(player);
    }

    public static Object getEntityPlayer(Player player) {
        try {
            return ClassesContainer.INSTANCE.getCraftPlayerClass().getMethod("getHandle").invoke(getCraftPlayer(player));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to get EntityPlayer...", e);
        }
    }

    public static Object getEntityPlayer(Object craftPlayer) {
        try {
            return ClassesContainer.INSTANCE.getCraftPlayerClass().getMethod("getHandle").invoke(craftPlayer);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to get EntityPlayer...", e);
        }
    }

    public static Object getPlayerConnection(@NotNull Player player) {
        for(Field field : ClassesContainer.INSTANCE.getEntityPlayerClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == ClassesContainer.INSTANCE.getPlayerConnectionClass()) {
                try {
                    return field.get(getEntityPlayer(player));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get PlayerConnection...", e);
                }
            }
        }
        return null;
    }

    public static Object getPlayerConnection(@NotNull Object entityPlayer) {
        for(Field field : ClassesContainer.INSTANCE.getEntityPlayerClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == ClassesContainer.INSTANCE.getPlayerConnectionClass()) {
                try {
                    return field.get(entityPlayer);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get PlayerConnection...", e);
                }
            }
        }
        return null;
    }

    public static Object getPlayerNetworkManager(@NotNull Player player) {
        for(Field field : ClassesContainer.INSTANCE.getPlayerConnectionClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == ClassesContainer.INSTANCE.getNetworkManagerClass()) {
                try {
                    return field.get(getPlayerConnection(player));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get player NetworkManager...", e);
                }
            }
        }
        return null;
    }

    public static Object getPlayerNetworkManager(@NotNull Object playerConnection) {
        for(Field field : ClassesContainer.INSTANCE.getPlayerConnectionClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == ClassesContainer.INSTANCE.getNetworkManagerClass()) {
                try {
                    return field.get(playerConnection);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get player NetworkManager...", e);
                }
            }
        }
        return null;
    }

    public static Channel getPlayerChannel(@NotNull Player player) {
        for(Field field : ClassesContainer.INSTANCE.getNetworkManagerClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == Channel.class) {
                try {
                    return (Channel) field.get(getPlayerNetworkManager(player));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get player Channel...", e);
                }
            }
        }
        return null;
    }

    public static Channel getPlayerChannel(@NotNull Object networkManager) {
        for(Field field : ClassesContainer.INSTANCE.getNetworkManagerClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == Channel.class) {
                try {
                    return (Channel) field.get(networkManager);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get player Channel...", e);
                }
            }
        }
        return null;
    }

    public static ChannelPipeline getChannelPipeline(Player player) {
        return getPlayerChannel(player).pipeline();
    }

    public static ChannelPipeline getChannelPipeline(Channel channel) {
        return channel.pipeline();
    }

    public static int getPlayerPing(Player player) {
        if(GlobalProvider.instance().getServer().isLegacy()) {
            return Reflection.readField(getEntityPlayer(player), "ping");
        } else {
            return player.getPing();
        }
    }

}
