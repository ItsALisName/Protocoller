package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReflectionException extends RuntimeException {

    private ReflectionException(String s) {
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

        public CompletedException fieldNotFound(@NotNull Class<?> type, int index, @NotNull Class<?> from) {
            return new CompletedException(new ReflectionException("Could not find field with type \"" + type.getSimpleName() + "\" at number \"" + index + "\" in class \"" + from.getName() + "\"" + definedReason), showStackTrace);
        }

        public CompletedException classNotFound(String clazz) {
            return new CompletedException(new ReflectionException("Failed to find class \"" + clazz + "\"" + definedReason), showStackTrace);
        }

    }

}
