package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InjectionException extends RuntimeException {

    private InjectionException(String s) {
        super(s);
    }

    private InjectionException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
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

        public CompletedException failedChannelFutureInject() {
            return new CompletedException(new InjectionException("Failed to inject to ChannelFuture." + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException failedChannelFutureInject(@NotNull PluginDescriptionFile source) {
            return new CompletedException(new InjectionException("Failed to inject to ChannelFuture, Possible error initiator: \"" + source.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }

}
