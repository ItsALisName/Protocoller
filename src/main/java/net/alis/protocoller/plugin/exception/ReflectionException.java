package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionException extends RuntimeException {

    private ReflectionException(String s) {
        super(s);
    }

    private ReflectionException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
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

        public CompletedException fieldNotFound(@NotNull Class<?> type, int index, @NotNull Class<?> from) {
            return new CompletedException(new ReflectionException("Could not find field with type \"" + type.getSimpleName() + "\" at number \"" + index + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException fieldNotFound(@NotNull Class<?> from, String name) {
            return new CompletedException(new ReflectionException("Could not find any field named \"" + name + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException writeFieldError(@NotNull Class<?> clazz, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to change field \"" + field.getName() + "\" in object of class \"" + clazz.getSimpleName() + "\" to object of class \"" + field.getType().getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException writeFieldError(@NotNull Class<?> clazz, int index, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to change field indexed \"" + index + "\" with name \"" + field.getName() + "\" in object of class \"" + clazz.getSimpleName() + "\" to object of class \"" + field.getType().getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException writeFieldError(@NotNull Class<?> from, int index, @NotNull Class<?> type) {
            return new CompletedException(new ReflectionException("Failed to change field indexed \"" + index + "\" returning type \"" + type.getSimpleName() + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readFieldError(@NotNull Class<?> from, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to get value of field \"" + field.getName() + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readFieldError(@NotNull Class<?> from, @NotNull String field) {
            return new CompletedException(new ReflectionException("Failed to get value of field \"" + field + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readFieldError(@NotNull Class<?> from, int index, @NotNull Field field) {
            return new CompletedException(new ReflectionException("Failed to get value of field indexed \"" + index + "\" with name \"" + field.getName() + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readFieldError(@NotNull Class<?> from, int index, @NotNull Class<?> type) {
            return new CompletedException(new ReflectionException("Failed to get value of field indexed \"" + index + "\" returning type \"" + type.getSimpleName() + "\" from object of class \"" + from.getSimpleName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException classNotFound(String clazz) {
            return new CompletedException(new ReflectionException("Failed to find class \"" + clazz + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException subClassNotFound(String subClazz, String fromClass) {
            return new CompletedException(new ReflectionException("Could not find subclass \"" + subClazz + "\" in class \"" + fromClass + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException methodNotFound(Class<?> from, String methodName, Class<?> @NotNull ... parameters) {
            if(parameters.length > 0){
                List<String> params = new ArrayList<>();
                for (Class<?> p : parameters) {
                    if (p != null) {
                        params.add(p.getSimpleName());
                    } else {
                        params.add("UnknownClass(Null)");
                    }
                }
                return new CompletedException(new ReflectionException("Could not find method named \"" + methodName + "\" in class \"" + from.getName() + "\" with parameters \"" + String.join(", ",params) + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new ReflectionException("Could not find method named \"" + methodName + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException methodNotFound(Class<?> from, String methodName, Class<?> returnType, Class<?> @NotNull ... parameters) {
            if(parameters.length > 0) {
                List<String> params = new ArrayList<>();
                for (Class<?> p : parameters) {
                    if (p != null) {
                        params.add(p.getSimpleName());
                    } else {
                        params.add("UnknownClass(Null)");
                    }
                }
                return new CompletedException(new ReflectionException("Could not find method named \"" + methodName + "\" returning type \"" + returnType.getName() + "\" in class \"" + from.getName() + "\" with parameters \"" + String.join(", ", params) + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new ReflectionException("Could not find method named \"" + methodName + "\" returning type \"" + returnType.getName() + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException methodNotFound(@NotNull Class<?> from, int index, @NotNull Class<?> returnType) {
            return new CompletedException(new ReflectionException("Could not find method indexed \"" + index + "\"  with returning type \"" + returnType.getName() + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException methodNotFound(@NotNull Class<?> from, int index, String methodName) {
            return new CompletedException(new ReflectionException("Could not find method indexed \"" + index + "\" with name \"" + methodName + "\" in class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callMethodError(@NotNull Class<?> from, @NotNull Method method) {
            return new CompletedException(new ReflectionException("Failed to call method named \"" + method.getName() + "\" returning type \"" + method.getReturnType().getName() + "\" from class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callInterfaceMethodError(@NotNull Class<?> from, int index, @NotNull Class<?> returnType) {
            return new CompletedException(new ReflectionException("Failed to call method indexed \"" + index + "\" returning type \"" + returnType.getName() + "\" from class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callInterfaceMethodError(@NotNull Class<?> from, int index, @NotNull String methodName) {
            return new CompletedException(new ReflectionException("Failed to call method indexed \"" + index + "\" with name \"" + methodName + "\" from class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException constructorNotFound(Class<?> from, Class<?> @NotNull ... parameters) {
            if(parameters.length > 0){
                List<String> params = new ArrayList<>();
                for (Class<?> p : parameters) {
                    if (p != null) {
                        params.add(p.getSimpleName());
                    } else {
                        params.add("UnknownClass(Null)");
                    }
                }
                return new CompletedException(new ReflectionException("Could not find constructor in class \"" + from.getName() + "\" with parameters \"" + String.join(", ",params) + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new ReflectionException("Could not find constructor in class \"" + from.getName() + "\" without parameters" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException classNewInstanceError(@NotNull Class<?> from) {
            return new CompletedException(new ReflectionException("Failed to create an empty instance of class \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callConstructorError(Constructor<?> constructor, Class<?> @NotNull ... parameters) {
            List<String> params = new ArrayList<>();
            for (Class<?> p : parameters) {
                if (p != null) {
                    params.add(p.getSimpleName());
                } else {
                    params.add("UnknownClass(Null)");
                }
            }
            return new CompletedException(new ReflectionException("Failed to call constructor named \"" + constructor.getName() + "\" with parameters \"" + String.join(", ", params) +"\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException findEnumConstantError(@NotNull Class<?> from, int id) {
            return new CompletedException(new ReflectionException("Failed to find enum constant number \"" + id + "\" from enum \"" + from.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }

}
