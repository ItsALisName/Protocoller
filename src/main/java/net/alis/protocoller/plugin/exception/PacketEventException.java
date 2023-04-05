package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketEventException extends RuntimeException {

    private PacketEventException(String s) {
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

        public CompletedException unknownPacketData() {
            return new CompletedException(new PacketEventException("Can't install packet with missing data" + definedReason), showStackTrace, ignore);
        }
    }

}
