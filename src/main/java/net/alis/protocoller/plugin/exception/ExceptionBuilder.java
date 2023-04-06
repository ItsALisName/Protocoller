package net.alis.protocoller.plugin.exception;

import lombok.SneakyThrows;
import net.alis.protocoller.plugin.config.ProtocollerConfig;
import net.alis.protocoller.plugin.data.InitialData;
import net.alis.protocoller.plugin.managers.FileWorker;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.plugin.util.Utils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ExceptionBuilder {

    protected static final String path = "plugins/Protocoller/errors/";

    private boolean showStacktrace;
    private boolean ignore;

    public ExceptionBuilder() {
        this.showStacktrace = true;
        this.ignore = false;
    }

    public ExceptionBuilder showStackTrace(boolean showStacktrace) {
        this.showStacktrace = showStacktrace;
        return this;
    }

    public ExceptionBuilder setIgnored(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    public boolean isShowStacktrace() {
        return showStacktrace;
    }

    public ConfigException.Builder getConfigExceptions() {
        return new ConfigException.Builder(showStacktrace, ignore);
    }

    public ServerAccessException.Builder getServerExceptions() {
        return new ServerAccessException.Builder(showStacktrace, ignore);
    }

    public ReflectionException.Builder getReflectionExceptions() {
        return new ReflectionException.Builder(showStacktrace, ignore);
    }

    public PacketException.Builder getPacketExceptions() {
        return new PacketException.Builder(showStacktrace, ignore);
    }

    public PacketEventException.Builder getEventsExceptions() {
        return new PacketEventException.Builder(showStacktrace, ignore);
    }

    public InjectionException.Builder getInjectExceptions() {
        return new InjectionException.Builder(showStacktrace, ignore);
    }

    public CryptographyException.Builder getCryptographyExceptions() {
        return new CryptographyException.Builder(showStacktrace, ignore);
    }

    public MinecraftKeyException.Builder getMinecraftKeyExceptions() {
        return new MinecraftKeyException.Builder(showStacktrace, ignore);
    }

    public NBTException.Builder getNBTExceptions() {
        return new NBTException.Builder(showStacktrace, ignore);
    }

    public AttributeException.Builder getAttributeExceptions() {
        return new AttributeException.Builder(showStacktrace, ignore);
    }

    protected static void writeExceptionFile(@NotNull Throwable e) {
        String serverVersion = GlobalProvider.instance() != null ? GlobalProvider.instance().getServer().getVersion().asString() : InitialData.get().getPreVersion().asString();
        String fileName = e.getClass().getSimpleName() + "_" + Utils.getCurrentDate(false) + "_" + Utils.getCurrentTime(true) + ".txt";
        if(TaskSimplifier.get() != null) {
            TaskSimplifier.get().preformAsync(() -> {
                FileWorker fileWorker = new FileWorker(ExceptionBuilder.path, fileName);
                fileWorker.startWriting()
                        .write("Exception: " + e.getClass().getName())
                        .writeNewLine()
                        .writeNewLine("Date and time: " + Utils.getCurrentDate(true) + ", " + Utils.getCurrentTime(false))
                        .writeNewLine()
                        .writeNewLine("Reason: " + e.getMessage())
                        .writeNewLine()
                        .writeNewLine("Minecraft server core: " + Bukkit.getVersion().split("-")[1])
                        .writeNewLine()
                        .writeNewLine("Minecraft server version: " + serverVersion)
                        .writeNewLine()
                        .writeNewLine("Stacktrace: ");
                for (StackTraceElement traceElement : e.getStackTrace())
                    fileWorker.writeNewLine("    " + traceElement.toString().replace("Protocoller.jar//", ""));
                fileWorker.writeNewLine()
                        .writeNewLine("Please, report about that: https://github.com/ItsALisName/Protocoller/issues")
                        .stopAll();
            });
        } else {
            FileWorker fileWorker = new FileWorker(ExceptionBuilder.path, fileName);
            fileWorker.startWriting()
                    .write("Exception: " + e.getClass().getName())
                    .writeNewLine()
                    .writeNewLine("Date and time: " + Utils.getCurrentDate(true) + ", " + Utils.getCurrentTime(false))
                    .writeNewLine()
                    .writeNewLine("Reason: " + e.getMessage())
                    .writeNewLine()
                    .writeNewLine("Minecraft server core: " + Bukkit.getVersion().split("-")[1])
                    .writeNewLine()
                    .writeNewLine("Minecraft server version: " + serverVersion)
                    .writeNewLine()
                    .writeNewLine("Stacktrace: ");
            for (StackTraceElement traceElement : e.getStackTrace())
                fileWorker.writeNewLine("    " + traceElement.toString().replace("Protocoller.jar//", ""));
            fileWorker.writeNewLine()
                    .writeNewLine("Please, report about that: https://github.com/ItsALisName/Protocoller/issues")
                    .stopAll();
        }
    }

    @SneakyThrows
    public static <F> F throwException(Exception e, boolean showStacktrace) {
        if (ProtocollerConfig.isSaveErrors()) {
            writeExceptionFile(e);
        }
        if (!showStacktrace) e.setStackTrace(new StackTraceElement[]{});
        throw e;
    }

    @Contract(value = "_,_ -> fail", pure = true)
    public static <F> F throwException(@NotNull Error error, boolean showStacktrace) {
        if (ProtocollerConfig.isSaveErrors()) {
            writeExceptionFile(error);
        }
        if (!showStacktrace) error.setStackTrace(new StackTraceElement[]{});
        throw error;
    }

}
