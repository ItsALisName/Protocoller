package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class ReflectionException extends RuntimeException {

    private ReflectionException(String s) {
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

        public CompletedException fieldNotFound(@NotNull Class<?> type, int index, @NotNull Class<?> from) {
            return new CompletedException(new ReflectionException("Could not find field with type \"" + type.getSimpleName() + "\" at number \"" + index + "\" in class \"" + from.getName() + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException fieldNotFound(@NotNull Class<?> from, String name) {
            return new CompletedException(new ReflectionException("Could not find any field named \"" + name + "\" in class \"" + from.getName() + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException writeFieldError(@NotNull Class<?> clazz, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to change field \"" + field.getName() + "\" in object of class \"" + clazz.getSimpleName() + "\" to object of class \"" + field.getType().getSimpleName() + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException readFieldError(@NotNull Class<?> from, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to get value of field \"" + field.getName() + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException classNotFound(String clazz) {
            return new CompletedException(new ReflectionException("Failed to find class \"" + clazz + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException subClassNotFound(String subClazz, String fromClass) {
            return new CompletedException(new ReflectionException("Could not find subclass \"" + subClazz + "\" in class \"" + fromClass + "\"" + definedReason), showStackTrace, ignore);
        }

    }

}
