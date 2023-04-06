package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketEventException extends RuntimeException {

    private PacketEventException(String s) {
        super(s);
    }

    private PacketEventException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
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

        public CompletedException callEventUnknownError(String abstractType) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callEventUnknownError(String abstractType, PacketDataContainer data) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callEventUnknownError(String abstractType, Object data) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException unknownPacketData() {
            return new CompletedException(new PacketEventException("Can't install packet with missing data" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException processedNullPlayer(String eventTypeName, @Nullable PacketDataContainer processedPacket) {
            if (processedPacket == null) {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException callEventError(String eventTypeName, Class<?> listener, @Nullable PacketDataContainer processedPacket) {
            if(processedPacket != null) {
                String pData = PacketUtils.buildPacketDataReport(processedPacket);
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\"" + definedReason + pData).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException callEventError(String eventTypeName, @Nullable PacketDataContainer processedPacket) {
            if(processedPacket != null) {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\"" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException readPacketError(PacketDataContainer data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readPacketError(Object data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge), showStackTrace, saveToFile, ignore);
        }

    }

}
