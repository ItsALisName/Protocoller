package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnsupportedObjectException extends ProtocollerException {

    private UnsupportedObjectException(String msg) {
        super(msg);
    }

    protected ProtocollerException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            super.setStackTrace(Utils.joinArrays(traceElements, super.getStackTrace()));
        }
        return this;
    }

    @Override
    protected ProtocollerException changeStackTraceIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            super.setStackTrace(traceElements);
        }
        return this;
    }

    public static class Builder implements ExceptionBuilderSource<Builder> {

        private final boolean showStackTrace;
        private final boolean ignore;
        private final boolean saveToFile;
        private @Nullable String definedReason = "";
        private StackTraceElement[] elementsToMerge = null;
        private StackTraceElement[] newStackTrace;

        protected Builder(boolean showStackTrace, boolean saveToFile, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
            this.saveToFile = saveToFile;
        }

        public Builder defineReason(@NotNull Throwable throwable) {
            if(throwable.getMessage() != null) {
                this.definedReason = "\n-> Reason from the branch: " + throwable.getMessage() + "\n";
            } else {
                if(throwable.getCause().getMessage() != null) {
                    this.definedReason = "\n-> Reason from the branch: " + throwable.getCause().getMessage() + "\n";
                }
            }
            return this;
        }

        @Override
        public Builder changeStackTrace(StackTraceElement[] newTrace) {
            this.newStackTrace = newTrace;
            return this;
        }

        public Builder mergeStackTraces(StackTraceElement[] stackTraceElements) {
            this.elementsToMerge = stackTraceElements;
            return this;
        }

        public CompletedException unsupportedPacketError(String packetName) {
            return new CompletedException(new UnsupportedObjectException("Packet \"" + packetName + "\" could not be created because your current server version does not support this object!" + definedReason), showStackTrace, saveToFile, ignore);
        }

        public CompletedException unsupportedObjectError(String objectName) {
            return new CompletedException(new UnsupportedObjectException("An instance of class \"" + objectName + "\" cannot be created because your current server version does not support this object!" + definedReason), showStackTrace, saveToFile, ignore);
        }

    }

}
