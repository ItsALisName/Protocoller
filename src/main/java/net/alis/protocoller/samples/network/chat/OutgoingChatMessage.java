package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.NetworkPlayer;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.plugin.v0_0_5.entity.ProtocolPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class OutgoingChatMessage {

    private final Object result;

    public OutgoingChatMessage(@NotNull PlayerChatMessage message) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.result = Reflect.callMethod(null, createMethod, true, message.createOriginal());
        this.sendToPlayerMethod = Reflect.getMethod(result.getClass(), "sendToPlayer", false);
    }

    public Object getResult() {
        return result;
    }

    public void sendToPlayer(NetworkPlayer sender, boolean filterMaskEnabled, @NotNull ChatBound params, @Nullable ChatComponent unsigned) {
        Reflect.callMethod(this.result, sendToPlayerMethod, false, ((ProtocolPlayer)sender).getHandle().getOriginal(), filterMaskEnabled, params.createOriginal(), unsigned != null ? unsigned.asIChatBaseComponent() : null);
    }

    private static Method createMethod;
    private final Method sendToPlayerMethod;

    public static void init() {
        if(clazz() != null)
            createMethod = Reflect.getMethod(clazz(), "a", clazz(), true, PlayerChatMessage.clazz());
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getOutgoingChatMessageClass();
    }

}
