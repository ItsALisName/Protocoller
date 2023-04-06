package net.alis.protocoller.plugin.exception;

import net.alis.protocoller.packet.MinecraftPacketType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketException extends RuntimeException {

    private PacketException(String s) {
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

        public CompletedException missingPacketBuilder(@NotNull MinecraftPacketType type) {
            return new CompletedException(new PacketException("Could not find PacketBuilder for packet \"" + type.getPacketName() + "(ID: " + type.getPacketId() + ")\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException missingPacketType(@NotNull Class<?> packet) {
            return new CompletedException(new PacketException("Failed to find packet type for packet \"" + packet.getName() + "\"" + definedReason), showStackTrace, ignore);
        }

        public CompletedException overPayload() {
            return new CompletedException(new PacketException("Payload may not be larger than 1048576 bytes" + definedReason), showStackTrace, ignore);
        }

        public CompletedException illegalPacketCast(Class<?> packet$0, Class<?> packet$1) {
            return new CompletedException(new PacketException("Packet \"" + packet$0.getName() + "\" cannot be cast to packet \"" + packet$1.getName() + "\"" + definedReason), showStackTrace, ignore);
        }

    }

}
