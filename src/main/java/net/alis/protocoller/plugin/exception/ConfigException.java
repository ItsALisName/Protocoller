package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;

public class ConfigException extends RuntimeException {

    private ConfigException(String s) {
        super(s);
    }

    public static class Builder {

        private final boolean showStackTrace;
        private final boolean ignore;
        private String definedReason = "";

        protected Builder(boolean showStackTrace, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
        }

        public Builder defineReason(@NotNull Throwable s) {
            this.definedReason = "\n-> Reason from the branch: " + s.getMessage() + "\n";
            return this;
        }

        public CompletedException differentVersionsError(String fileName, String current, String needed) {
            return new CompletedException(new ConfigException("Error updating config file \"" + fileName + "\", required file version \"" + needed + "\", current file version \"" + current + "\"" + definedReason), showStackTrace, ignore);
        }

        public @NotNull CompletedException nullConfigFile(String configName, String pathToConfig) {
            return new CompletedException(new ConfigException("The configuration file \"" + configName +"\" on the path \"" + pathToConfig + "\" was not found!" + definedReason), showStackTrace, ignore);
        }

        public @NotNull CompletedException unknownPath(String fileName, String path) {
            return new CompletedException(new ConfigException("While reading the configuration file \"" + fileName + "\", the path \"" + path + "\" was not found! Please check the configuration file and fix the errors!" + definedReason), showStackTrace, ignore);
        }

        public @NotNull CompletedException wrongType(String fileName, String parameterPath, @NotNull Class<?> requiredType, @NotNull Class<?> type) {
            return new CompletedException(new ConfigException("When reading a parameter along the \"" + parameterPath + "\" path from the \"" + fileName + "\" configuration file, the resulting type does not match the requested one! (Expected: \"" + requiredType.getSimpleName() + "\", Received: \"" + type.getSimpleName() + "\")" + definedReason), showStackTrace, ignore);
        }
    }

}
