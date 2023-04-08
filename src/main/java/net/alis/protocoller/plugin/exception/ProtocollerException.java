package net.alis.protocoller.plugin.exception;

public abstract class ProtocollerException extends Exception {

    protected ProtocollerException(String message) {
        super(message);
    }

    protected ProtocollerException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ProtocollerException(Throwable cause) {
        super(cause);
    }

    protected abstract ProtocollerException changeStackTraceIfNeed(StackTraceElement[] traceElements);

    protected abstract ProtocollerException mergeStackTracesIfNeed(StackTraceElement[] traceElements);

}
