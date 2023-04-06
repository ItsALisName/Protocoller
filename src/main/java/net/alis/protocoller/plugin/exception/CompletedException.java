package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.config.ProtocollerConfig;

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
                ExceptionBuilder.writeExceptionFile(this.exception);
            }
            if (!showStacktrace) this.exception.setStackTrace(new StackTraceElement[]{});
            throw this.exception;
        }
        return null;
    }

}
