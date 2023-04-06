package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketException extends RuntimeException {

    private PacketException(String s) {
        super(s);
    }

    private PacketException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
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

        public CompletedException missingPacketBuilder(@NotNull MinecraftPacketType type) {
            return new CompletedException(new PacketException("Could not find PacketBuilder for packet \"" + type.getPacketName() + "(ID: " + type.getPacketId() + ")\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException missingPacketType(@NotNull Class<?> packet) {
            return new CompletedException(new PacketException("Failed to find packet type for packet \"" + packet.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException overPayload() {
            return new CompletedException(new PacketException("Payload may not be larger than 1048576 bytes" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException illegalPacketCast(Class<?> packet$0, Class<?> packet$1) {
            return new CompletedException(new PacketException("Packet \"" + packet$0.getName() + "\" cannot be cast to packet \"" + packet$1.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }

}
