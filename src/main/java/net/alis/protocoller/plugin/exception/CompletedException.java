package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.config.ProtocollerConfig;
import net.alis.protocoller.plugin.managers.FileManager;
import net.alis.protocoller.plugin.util.TaskSimplifier;
import net.alis.protocoller.plugin.util.Utils;

public class CompletedException {

    private final RuntimeException exception;
    private final boolean saveToFile;
    private final boolean showStacktrace;
    private final boolean ignore;

    protected CompletedException(RuntimeException exception, boolean showStacktrace, boolean ignore) {
        this.exception = exception;
        this.saveToFile = ProtocollerConfig.isSaveErrors();
        this.showStacktrace = showStacktrace;
        this.ignore = ignore;
    }

    public <F> F throwException() {
        if(!ignore){
            if (saveToFile) {
                TaskSimplifier.get().preformAsync(() -> {
                    FileManager manager = new FileManager(ExceptionBuilder.path, this.exception.getClass().getSimpleName() + "_" + Utils.getCurrentDate(false));
                    manager.startWriting().write("Exception: " + this.exception.getClass().getName());
                    manager.writeNewLine("Date and time: " + Utils.getCurrentDate(true) + ", " + Utils.getCurrentTime()).writeNewLine("Cause: " + this.exception.getMessage());
                    manager.writeNewLine("Stacktrace: ");
                    for (StackTraceElement traceElement : this.exception.getStackTrace())
                        manager.writeNewLine(traceElement.toString());
                    manager.stopAll();
                });
            }
            if (!showStacktrace) this.exception.setStackTrace(new StackTraceElement[]{});
            throw this.exception;
        }
        return null;
    }
}
