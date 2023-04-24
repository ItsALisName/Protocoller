package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.ProtocollerClient;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.plugin.providers.ApiProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketEventException extends ProtocollerException {

    private PacketEventException(String s) {
        super(s);
    }

    protected ProtocollerException mergeStackTracesIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            super.setStackTrace(Utils.joinArrays(traceElements, super.getStackTrace()));
        }
        return this;
    }

    protected ProtocollerException changeStackTraceIfNeed(StackTraceElement[] traceElements) {
        if(traceElements != null && traceElements.length > 0){
            setStackTrace(traceElements);
        }
        return this;
    }

    public static class Builder implements ExceptionBuilderSource<Builder> {
        private final boolean showStackTrace;
        private final boolean ignore;
        private final boolean saveToFile;
        private @Nullable String definedReason = "";
        private StackTraceElement[] elementsToMerge = null;
        private StackTraceElement[] newStackTrace;

        protected Builder(boolean showStackTrace, boolean saveToFile, boolean ignore) {
            this.showStackTrace = showStackTrace;
            this.ignore = ignore;
            this.saveToFile = saveToFile;
        }

        @Override
        public Builder defineReason(@NotNull Throwable throwable) {
            if(throwable.getMessage() != null) {
                this.definedReason = "\n-> Reason from the branch: " + throwable.getMessage() + "\n";
            } else {
                if(throwable.getCause().getMessage() != null) {
                        this.definedReason = "\n-> Reason from the branch: " + throwable.getCause().getMessage() + "\n";
                    }
            }
            return this;
        }

        @Override
        public Builder mergeStackTraces(StackTraceElement[] stackTraceElements) {
            this.elementsToMerge = stackTraceElements;
            return this;
        }

        @Override
        public Builder changeStackTrace(StackTraceElement[] newTrace) {
            this.newStackTrace = newTrace;
            return this;
        }

        public CompletedException customMessage(String message) {
            return new CompletedException(new PacketEventException(message + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callEventUnknownError(String abstractType) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callEventUnknownError(String abstractType, PacketDataContainer data) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException callEventUnknownError(String abstractType, Object data) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException unknownPacketData() {
            return new CompletedException(new PacketEventException("Can't install packet with missing data" + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException processedNullPlayer(String eventTypeName, @Nullable PacketDataContainer processedPacket) {
            if (processedPacket == null) {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException callEventError(String eventTypeName, Class<?> listener, ProtocollerClient client, @Nullable PacketDataContainer processedPacket) {
            if(processedPacket != null) {
                String pData = PacketUtils.buildPacketDataReport(processedPacket);
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\" of plugin \"" + ((ApiProvider)client).getFullName() + "\"" + definedReason + pData).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\" of plugin \"" + ((ApiProvider)client).getFullName() + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException callEventError(String eventTypeName, @Nullable PacketDataContainer processedPacket) {
            if(processedPacket != null) {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\"" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            } else {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\"" + definedReason).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
            }
        }

        public CompletedException readPacketError(PacketDataContainer data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

        public CompletedException readPacketError(Object data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)).mergeStackTracesIfNeed(elementsToMerge).changeStackTraceIfNeed(newStackTrace), showStackTrace, saveToFile, ignore);
        }

    }

}
