package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerAccessException extends RuntimeException {

    private ServerAccessException(String s) {
        super(s);
    }

    private ServerAccessException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            super.setStackTrace(Utils.joinArrays(traceElements, super.getStackTrace()));
        }
        return this;
    }

    public static class Builder {

        private final boolean showStackTrace;
        private final boolean ignore;
        private final boolean saveToFile;
        private @Nullable String definedReason = "";
        private StackTraceElement[] elementsToMerge = null;

        protected Builder(boolean showStackTrace, boolean saveToFile, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
            this.saveToFile = saveToFile;
        }

        public Builder defineReason(@NotNull Throwable throwable) {
            if(throwable.getMessage() != null) {
                this.definedReason = "\n-> Reason from the branch: " + throwable.getMessage() + "\n";
            }
            return this;
        }

        public Builder mergeStackTraces(StackTraceElement[] stackTraceElements) {
            this.elementsToMerge = stackTraceElements;
            return this;
        }

        public CompletedException networkManagersError() {
            return new CompletedException(new ServerAccessException("Failed to get server network managers!" + definedReason).mergeStackTracesIfNeed(elementsToMerge).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException channelFuturesError() {
            return new CompletedException(new ServerAccessException("Failed to get server channels futures!" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }

}
