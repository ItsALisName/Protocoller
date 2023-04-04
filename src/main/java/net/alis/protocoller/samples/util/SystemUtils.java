package net.alis.protocoller.samples.util;

import java.util.function.Consumer;

public class SystemUtils {

    public static <T> T accept(T object, Consumer<T> initializer) {
        initializer.accept(object);
        return object;
    }

}
