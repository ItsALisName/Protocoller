package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerAccessException extends RuntimeException {

    private ServerAccessException(String s) {
        super(s);
    }

    public static class Builder {

        private final boolean showStackTrace;
        private @Nullable String definedReason = "";

        protected Builder(boolean showStackTrace) {
            this.showStackTrace = showStackTrace;
        }

        public Builder defineReason(@NotNull Throwable throwable) {
            this.definedReason = "\nReason: " + throwable.getMessage();
            return this;
        }

        public CompletedException networkManagersError() {
            return new CompletedException(new ServerAccessException("Failed to get server network managers!" + definedReason), showStackTrace);
        }

        public CompletedException channelFuturesError() {
            return new CompletedException(new ServerAccessException("Failed to get server channels futures!" + definedReason), showStackTrace);
        }

    }

}
