package net.alis.protocoller.plugin.exception;

import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InjectionException extends RuntimeException {

    private InjectionException(String s) {
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

        public CompletedException failedChannelFutureInject() {
            return new CompletedException(new InjectionException("Failed to inject to ChannelFuture." + definedReason), showStackTrace, ignore);
        }

        public CompletedException failedChannelFutureInject(@NotNull PluginDescriptionFile source) {
            return new CompletedException(new InjectionException("Failed to inject to ChannelFuture, Possible error initiator: \"" + source.getName() + "\"" + definedReason), showStackTrace, ignore);
        }

    }

}
