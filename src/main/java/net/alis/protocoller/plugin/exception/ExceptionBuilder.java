package net.alis.protocoller.plugin.exception;

public class ExceptionBuilder {

    protected static final String path = "plugins/Protocoller/errors/";

    private boolean showStacktrace;

    public ExceptionBuilder() {
        this.showStacktrace = true;
    }

    public ExceptionBuilder showStackTrace(boolean showStacktrace) {
        this.showStacktrace = showStacktrace;
        return this;
    }

    public boolean isShowStacktrace() {
        return showStacktrace;
    }

    public ConfigException.Builder getConfigExceptions() {
        return new ConfigException.Builder(showStacktrace);
    }

    public ServerAccessException.Builder getServerExceptions() {
        return new ServerAccessException.Builder(showStacktrace);
    }

    public ReflectionException.Builder getReflectionExceptions() {
        return new ReflectionException.Builder(showStacktrace);
    }

}
