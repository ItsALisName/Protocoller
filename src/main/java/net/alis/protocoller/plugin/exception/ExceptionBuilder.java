package net.alis.protocoller.plugin.exception;

import lombok.SneakyThrows;
import net.alis.protocoller.plugin.config.configs.ProtocollerConfig;
import net.alis.protocoller.plugin.memory.ApproximateData;
import net.alis.protocoller.util.FileWorker;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.ITaskAccess;
import net.alis.protocoller.plugin.util.Utils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ExceptionBuilder {

    protected static final String path = "plugins/Protocoller/errors/";

    private boolean showStacktrace;
    private boolean ignore;
    private boolean saveToFile;

    public ExceptionBuilder() {
        this.showStacktrace = true;
        this.ignore = false;
        this.saveToFile = ProtocollerConfig.isSaveErrors();
    }

    public ExceptionBuilder saveToFile(boolean saveToFile) {
        this.saveToFile = saveToFile;
        return this;
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
        return new ConfigException.Builder(showStacktrace, saveToFile, ignore);
    }

    public ServerAccessException.Builder getServerExceptions() {
        return new ServerAccessException.Builder(showStacktrace, saveToFile, ignore);
    }

    public ReflectionException.Builder getReflectionExceptions() {
        return new ReflectionException.Builder(showStacktrace, saveToFile, ignore);
    }

    public PacketException.Builder getPacketExceptions() {
        return new PacketException.Builder(showStacktrace, saveToFile, ignore);
    }

    public PacketEventException.Builder getEventsExceptions() {
        return new PacketEventException.Builder(showStacktrace, saveToFile, ignore);
    }

    public InjectionException.Builder getInjectExceptions() {
        return new InjectionException.Builder(showStacktrace, saveToFile, ignore);
    }

    public CryptographyException.Builder getCryptographyExceptions() {
        return new CryptographyException.Builder(showStacktrace, saveToFile, ignore);
    }

    public MinecraftKeyException.Builder getMinecraftKeyExceptions() {
        return new MinecraftKeyException.Builder(showStacktrace, saveToFile, ignore);
    }

    public NBTException.Builder getNBTExceptions() {
        return new NBTException.Builder(showStacktrace, saveToFile, ignore);
    }

    public AttributeException.Builder getAttributeExceptions() {
        return new AttributeException.Builder(showStacktrace, saveToFile, ignore);
    }

    public BannedClientException.Builder getClientExceptions() {
        return new BannedClientException.Builder(showStacktrace, saveToFile, ignore);
    }

    public UpdaterException.Builder getUpdaterExceptions() {
        return new UpdaterException.Builder(showStacktrace, saveToFile, ignore);
    }

    public UnsupportedObjectException.Builder getUnsupportedObjectsExceptions() {
        return new UnsupportedObjectException.Builder(showStacktrace, saveToFile, ignore);
    }

    protected static void writeExceptionFile(@NotNull Throwable e) {
        String serverVersion = IProtocolAccess.get() != null ? IProtocolAccess.get().getServer().getVersion().asString() : ApproximateData.get().getPreVersion().asString();
        String fileName = e.getClass().getSimpleName() + "_" + Utils.currentDate(false) + "_" + Utils.currentTime(true) + ".txt";
        if(ITaskAccess.get() != null && ProtocollerConfig.isAsyncFilesWorking()) {
            ITaskAccess.get().doAsync(() -> {
                FileWorker fileWorker = new FileWorker(ExceptionBuilder.path, fileName);
                fileWorker.startWriting()
                        .write("Exception: " + e.getClass().getName())
                        .writeNewLine()
                        .writeNewLine("Date and time: " + Utils.currentDate(true) + ", " + Utils.currentTime(false))
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
                    .writeNewLine("Date and time: " + Utils.currentDate(true) + ", " + Utils.currentTime(false))
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
