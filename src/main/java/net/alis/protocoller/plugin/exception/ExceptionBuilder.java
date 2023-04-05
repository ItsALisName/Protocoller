package net.alis.protocoller.plugin.exception;

public class ExceptionBuilder {

    protected static final String path = "plugins/Protocoller/errors/";

    private boolean showStacktrace;
    private boolean ignore;

    public ExceptionBuilder() {
        this.showStacktrace = true;
        this.ignore = false;
    }

    public ExceptionBuilder showStackTrace(boolean showStacktrace) {
        this.showStacktrace = showStacktrace;
        return this;
    }

    public ExceptionBuilder ignore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    public boolean isShowStacktrace() {
        return showStacktrace;
    }

    public ConfigException.Builder getConfigExceptions() {
        return new ConfigException.Builder(showStacktrace, ignore);
    }

    public ServerAccessException.Builder getServerExceptions() {
        return new ServerAccessException.Builder(showStacktrace, ignore);
    }

    public ReflectionException.Builder getReflectionExceptions() {
        return new ReflectionException.Builder(showStacktrace, ignore);
    }

    public PacketException.Builder getPacketExceptions() {
        return new PacketException.Builder(showStacktrace, ignore);
    }

    public PacketEventException.Builder getEventsExceptions() {
        return new PacketEventException.Builder(showStacktrace, ignore);
    }

    public InjectionException.Builder getInjectExceptions() {
        return new InjectionException.Builder(showStacktrace, ignore);
    }

    public CryptographyException.Builder getCryptographyExceptions() {
        return new CryptographyException.Builder(showStacktrace, ignore);
    }

    public MinecraftKeyException.Builder getMinecraftKeyExceptions() {
        return new MinecraftKeyException.Builder(showStacktrace, ignore);
    }

}
