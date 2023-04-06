package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AttributeException extends RuntimeException {

    private AttributeException(String s) {
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
            this.definedReason = "\n-> Reason from the branch: " + throwable.getMessage() + "\n";
            return this;
        }

        public CompletedException customMessage(String s) {
            return new CompletedException(new AttributeException(s + definedReason), showStackTrace, ignore);
        }
    }

}
