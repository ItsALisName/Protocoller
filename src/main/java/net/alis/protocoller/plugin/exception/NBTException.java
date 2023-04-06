package net.alis.protocoller.plugin.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class NBTException extends RuntimeException {

    private NBTException(String s) {
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

        public CompletedException customMessage(String message) {
            return new CompletedException(new NBTException(message), showStackTrace, ignore);
        }

        public CompletedException fileRemoveError(File file) {
            return new CompletedException(new NBTException("Failed to delete tags file: \"" + file + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException byteReadError(byte bytes) {
            return new CompletedException(new NBTException("Failed to read nbt tag[type=\"" + bytes + "\"] from nbt..." + definedReason), showStackTrace, ignore);
        }

        public CompletedException readFromError(String type) {
            return new CompletedException(new NBTException("Failed to get tag \"" + type + "\" from NBT." + definedReason), showStackTrace, ignore);
        }
    }

}
