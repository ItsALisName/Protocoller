package net.alis.protocoller.plugin.v0_0_5.command;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.command.CommandListener;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.UUID;

public class ProtocolCommandListener implements CommandListener {

    private final AccessedObject handle;

    public ProtocolCommandListener(Object iCommandListener) {
        this.handle = new AccessedObject(iCommandListener, true);
    }

    @Override
    public void sendSystemMessage(@NotNull ChatComponent msg, UUID sender) {
        if(sendSystemMessageMethodLevel == 0)
            this.handle.invoke(sendSystemMessageMethod, msg.asIChatBaseComponent());
        else
            this.handle.invoke(sendSystemMessageMethod, msg.asIChatBaseComponent(), sender);
    }

    @Override
    public boolean acceptsSuccess() {
        return this.handle.invoke(acceptsSuccessMethod);
    }

    @Override
    public boolean acceptsFailure() {
        return this.handle.invoke(acceptsFailureMethod);
    }

    @Override
    public boolean shouldInformAdmins() {
        return this.handle.invoke(shouldInformAdminsMethod);
    }

    @Override
    public boolean alwaysAccepts() {
        if(alwaysAcceptsMethod != null) return this.handle.invoke(alwaysAcceptsMethod);
        return CommandListener.super.alwaysAccepts();
    }

    private static Method sendSystemMessageMethod,acceptsSuccessMethod, acceptsFailureMethod, shouldInformAdminsMethod, alwaysAcceptsMethod;
    private static int sendSystemMessageMethodLevel;

    public static void init() {
        if(ApproximateData.get().getPreVersion().greaterThanOrEqualTo(Version.v1_13)){
            sendSystemMessageMethodLevel = 0;
            sendSystemMessageMethod = Reflect.getMethod(clazz(), Void.TYPE, true, ChatComponent.clazz());
            if (sendSystemMessageMethod == null) {
                sendSystemMessageMethodLevel = 1;
                sendSystemMessageMethod = Reflect.getMethod(clazz(), Void.TYPE, false, ChatComponent.clazz(), UUID.class);
            }
            acceptsSuccessMethod = Reflect.getMethod(clazz(), 0, boolean.class, false);
            acceptsFailureMethod = Reflect.getMethod(clazz(), 1, boolean.class, false);
            shouldInformAdminsMethod = Reflect.getMethod(clazz(), 2, boolean.class, false);
            alwaysAcceptsMethod = Reflect.getMethod(clazz(), 3, boolean.class, true);
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getICommandListenerClass();
    }
}
