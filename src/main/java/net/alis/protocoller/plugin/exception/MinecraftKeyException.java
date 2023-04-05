package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinecraftKeyException extends RuntimeException {

    private MinecraftKeyException(String s) {
        super(s);
    }

    public static class Builder {
        private final boolean showStackTrace;
        private final boolean ignore;
        private @Nullable String definedReason = "";

        protected Builder(boolean showStackTrace, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
        }

        public Builder defineReason(@NotNull Throwable throwable) {
            this.definedReason = "\nReason: " + throwable.getMessage();
            return this;
        }

        public CompletedException verifyError(String namespace, String path) {
            return new CompletedException(new MinecraftKeyException("Non [Skip$1-z0-9_.-] character in namespace of location: " + namespace + ":" + path + definedReason), showStackTrace, ignore);
        }
    }

}
