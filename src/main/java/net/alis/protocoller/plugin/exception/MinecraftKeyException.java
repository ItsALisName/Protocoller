package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinecraftKeyException extends ProtocollerException {

    private MinecraftKeyException(String s) {
        super(s);
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

        public CompletedException verifyError(String namespace, String path) {
            return new CompletedException(new MinecraftKeyException("Non [a-z0-9_.-] character in namespace of location: " + namespace + ":" + path + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }
    }

}
