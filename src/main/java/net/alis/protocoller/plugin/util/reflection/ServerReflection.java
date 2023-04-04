package net.alis.protocoller.plugin.util.reflection;

import com.google.common.collect.Lists;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.enums.Version;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerReflection {

    public static int getProtocolVersion() {
        Class<?> sharedConstants; int protocol = 0;
        if(InitialData.INSTANCE.isLegacyServer()) {
            sharedConstants = BaseReflection.getLegacyNMSClass("SharedConstants");
        } else {
            sharedConstants = BaseReflection.getNMClass("SharedConstants");
        }
        try {
            protocol = BaseReflection.callInterfaceMethod(sharedConstants, 0, int.class);
        } catch (Exception e) {
            protocol = Version.fromPackageName(InitialData.INSTANCE.getPackageVersion()).getProtocol();
        }
        return protocol;
    }

    public static @Nullable Object getMinecraftServer() {
        for(Method method : ClassesContainer.get().getMinecraftServerClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getName().equalsIgnoreCase("getServer")) {
                try {
                    return method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to get MinecraftServer instance!", e);
                }
            }
        }
        return null;
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
