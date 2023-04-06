package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.plugin.util.PacketUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketEventException extends RuntimeException {

    private PacketEventException(String s) {
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

        public CompletedException callEventUnknownError(String abstractType) {
            return new CompletedException(new PacketEventException("An error occurred while trying to initialize the event \"" + abstractType + "\"." + definedReason), showStackTrace, ignore);
        }

        public CompletedException unknownPacketData() {
            return new CompletedException(new PacketEventException("Can't install packet with missing data" + definedReason), showStackTrace, ignore);
        }

        public CompletedException processedNullPlayer(String eventTypeName, @Nullable PacketDataContainer processedPacket) {
            if (processedPacket == null) {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason), showStackTrace, ignore);
            } else {
                return new CompletedException(new PacketEventException("An error occurred during the initialization of the event \"" + eventTypeName + "\", the player was not found" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)), showStackTrace, ignore);
            }
        }

        public CompletedException callEventError(String eventTypeName, Class<?> listener, @Nullable PacketDataContainer processedPacket) {
            if(processedPacket != null) {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\"" + definedReason + PacketUtils.buildPacketDataReport(processedPacket)), showStackTrace, ignore);
            } else {
                return new CompletedException(new PacketEventException("Failed to call event \"" + eventTypeName + "\" for listener \"" + listener.getName() + "\"" + definedReason), showStackTrace, ignore);
            }
        }

        public CompletedException readPacketError(PacketDataContainer data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)), showStackTrace, ignore);
        }

        public CompletedException readPacketError(Object data) {
            return new CompletedException(new PacketEventException("Failed to read incoming packet" + definedReason + PacketUtils.buildPacketDataReport(data)), showStackTrace, ignore);
        }

    }

}
