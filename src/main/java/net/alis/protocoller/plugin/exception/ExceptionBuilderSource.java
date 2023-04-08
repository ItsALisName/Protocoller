package net.alis.protocoller.plugin.exception;

public interface ExceptionBuilderSource<S extends ExceptionBuilderSource<?>> {

    S defineReason(Throwable throwable);

    S mergeStackTraces(StackTraceElement[] elements);

    S changeStackTrace(StackTraceElement[] elements);



}
