package net.alis.protocoller.plugin.exception;

import lombok.SneakyThrows;
import net.alis.protocoller.plugin.managers.LogsManager;

public class CompletedException {

    private final ProtocollerException exception;
    private final boolean saveToFile;
    private final boolean showStacktrace;
    private final boolean ignore;

    protected CompletedException(ProtocollerException exception, boolean showStacktrace, boolean saveToFile, boolean ignore) {
        this.exception = exception;
        this.saveToFile = saveToFile;
        this.showStacktrace = showStacktrace;
        this.ignore = ignore;
    }

    @SneakyThrows
    public <F> F throwException() {
        if(!ignore){
            if (saveToFile) {
                ExceptionBuilder.writeExceptionFile(this.exception);
            }
            if (!showStacktrace) this.exception.setStackTrace(new StackTraceElement[]{});
            throw this.exception;
        }
        return null;
    }

    public Exception showException() {
        if (saveToFile) {
            ExceptionBuilder.writeExceptionFile(this.exception);
        }
        LogsManager.get().getLogger().error("An exception was found: " + exception.getClass().getName());
        LogsManager.get().getLogger().error("Message: " + exception.getMessage());
        if(showStacktrace) {
            LogsManager.get().getLogger().error("StackTrace:");
            for(StackTraceElement ste : this.exception.getStackTrace()) {
                LogsManager.get().getLogger().error(ste.toString());
            }
        }
        return this.exception;
    }

}
