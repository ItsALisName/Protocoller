package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;

public class ConfigException extends RuntimeException {

    private ConfigException(String s) {
        super(s);
    }

    public static class Builder {

        private final boolean showStackTrace;
        private final boolean ignore;

        protected Builder(boolean showStackTrace, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
        }

        public @NotNull CompletedException nullConfigFile(String configName, String pathToConfig) {
            return new CompletedException(new ConfigException("The configuration file \"" + configName +"\" on the path \"" + pathToConfig + "\" was not found!"), showStackTrace, ignore);
        }

        public @NotNull CompletedException unknownPath(String fileName, String path) {
            return new CompletedException(new ConfigException("While reading the configuration file \"" + fileName + "\", the path \"" + path + "\" was not found! Please check the configuration file and fix the errors!"), showStackTrace, ignore);
        }

        public @NotNull CompletedException wrongType(String fileName, String parameterPath, Class<?> requiredType, Class<?> type) {
            return new CompletedException(new ConfigException("When reading a parameter along the \"" + parameterPath + "\" path from the \"" + fileName + "\" configuration file, the resulting type does not match the requested one! (Expected: \"" + requiredType.getSimpleName() + "\", Received: \"" + type.getSimpleName() + "\")"), showStackTrace, ignore);
        }
    }

}
