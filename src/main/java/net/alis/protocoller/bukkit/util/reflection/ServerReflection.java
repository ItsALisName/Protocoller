package net.alis.protocoller.bukkit.util.reflection;

import com.google.common.collect.Lists;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.data.InitialData;
import net.alis.protocoller.bukkit.enums.Version;
import org.bukkit.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerReflection {

    public static int getProtocolVersion() {
        Class<?> sharedConstants; int protocol = 0;
        if(InitialData.INSTANCE.isLegacyServer()) {
            sharedConstants = Reflection.getLegacyNMSClass("SharedConstants");
        } else {
            sharedConstants = Reflection.getNMClass("SharedConstants");
        }
        try {
            protocol = Reflection.callInterfaceMethod(sharedConstants, 0, int.class);
        } catch (Exception e) {
            protocol = Version.fromPackageName(InitialData.INSTANCE.getPackageVersion()).getProtocol();
        }
        return protocol;
    }

    public static Object getMinecraftServer() {
        for(Method method : ClassesContainer.INSTANCE.getMinecraftServerClass().getDeclaredMethods()) {
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
        return ClassesContainer.INSTANCE.getCraftServerClass().cast(server);
    }

    public static Object getServerConnection() {
        Object server = getMinecraftServer();
        if(server.getClass() == ClassesContainer.INSTANCE.getDedicatedServerClass()) {
            Object fromDedicated = ClassesContainer.INSTANCE.getMinecraftServerClass().cast(server);
            return Reflection.readSuperclassField(fromDedicated, 0, ClassesContainer.INSTANCE.getServerConnectionClass());
        }
        return Reflection.readField(server, 0, ClassesContainer.INSTANCE.getServerConnectionClass());
    }

    public static List<Object> getServerChannelFutures() {
        List<Object> list = Collections.synchronizedList(new ArrayList<Object>());
        list.addAll(Reflection.readField(getServerConnection(), 0, List.class));
        return list;
    }

    public static List<Object> getServerNetworkManagers() {
        List<Object> list = Collections.synchronizedList(Lists.newArrayList());
        list.addAll(Reflection.readField(getServerConnection(), 1, List.class));
        return list;
    }

}
