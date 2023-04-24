package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BannedClientException extends ProtocollerException {

    public BannedClientException(String msg) {
        super(msg);
    }

    @Override
    protected ProtocollerException changeStackTraceIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            setStackTrace(traceElements);
        }
        return this;
    }

    @Override
    protected ProtocollerException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            super.setStackTrace(Utils.joinArrays(traceElements, super.getStackTrace()));
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

        @Override
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
        public Builder mergeStackTraces(StackTraceElement[] stackTraceElements) {
            this.elementsToMerge = stackTraceElements;
            return this;
        }

        @Override
        public Builder changeStackTrace(StackTraceElement[] newTrace) {
            this.newStackTrace = newTrace;
            return this;
        }

        public CompletedException bannedPlugin(@NotNull Plugin client) {
            return new CompletedException(new BannedClientException("Banned plugin \"" + client.getName() + " v." + client.getDescription().getVersion() + "\" tried to register api." + definedReason).changeStackTraceIfNeed(newStackTrace).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException bannedAuthor(@NotNull Plugin plugin) {
            return new CompletedException(new BannedClientException("Plugin \"" + plugin.getName() + " v." + plugin.getDescription().getVersion() + "\" from a banned author tried to register api" + definedReason).changeStackTraceIfNeed(newStackTrace).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }
}
