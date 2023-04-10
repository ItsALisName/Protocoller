package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpdaterException extends ProtocollerException {

    private UpdaterException(String message) {
        super(message);
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

        public CompletedException checkLatestError() {
            return new CompletedException(new UpdaterException("Failed to check latest version" + definedReason), showStackTrace, saveToFile, ignore);
        }

        public CompletedException downloadLatestError() {
            return new CompletedException(new UpdaterException("Failed to download the latest version of the plugin." + definedReason), showStackTrace, saveToFile, ignore);
        }

    }

}
