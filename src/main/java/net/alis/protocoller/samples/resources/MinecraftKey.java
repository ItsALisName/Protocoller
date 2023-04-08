package net.alis.protocoller.samples.resources;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

public class MinecraftKey {

    private final String namespace;
    private final String path;

    protected MinecraftKey(String namespace, String path, @Nullable ExtraData extraData) {
        this.namespace = namespace;
        this.path = path;
    }

    public MinecraftKey(String namespace, String path) {
        this(verifyAndBuildNamespace(namespace, path), verifyAndBuildPath(namespace, path), null);
    }

    public MinecraftKey(Object originalKey) {
        AccessedObject accessor = new AccessedObject(originalKey);
        if(GlobalProvider.instance().getServer().isLegacy()) {
            this.namespace = accessor.read(0, String.class);
            this.path = accessor.read(1, String.class);
        } else {
            this.namespace = accessor.read(2, String.class);
            this.path = accessor.read(3, String.class);
        }
    }

    public MinecraftKey(String @NotNull [] id) {
        this(id[0], id[1]);
    }

    public MinecraftKey(String id) {
        this(build(id));
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getPath() {
        return this.path;
    }

    public MinecraftKey copy(String path) {
        return new MinecraftKey(this.namespace, verifyAndBuildPath(this.namespace, path), null);
    }

    public MinecraftKey copy(@NotNull UnaryOperator<String> pathFunction) {
        return this.copy(pathFunction.apply(this.path));
    }

    public MinecraftKey newNamespace(String namespace) {
        return this.copy(namespace + this.path);
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public String toLegacyString() {
        return (this.namespace + "|" + this.path).toUpperCase();
    }
    
    

    public static String verifyAndBuildNamespace(String namespace, String path) {
        if (!verifyNamespace(namespace)) {
            String normalizedSpace = StringUtils.normalizeSpace(namespace);
            return new ExceptionBuilder().getMinecraftKeyExceptions().verifyError(normalizedSpace, StringUtils.normalizeSpace(path)).throwException();
        } else {
            return namespace;
        }
    }

    public static String verifyAndBuildPath(String namespace, String path) {
        if (!verifyPath(path)) {
            return new ExceptionBuilder().getMinecraftKeyExceptions().verifyError(namespace, StringUtils.normalizeSpace(path)).throwException();
        } else {
            return path;
        }
    }

    public static String @NotNull [] build(@NotNull String id) {
        String[] strings = new String[]{"minecraft", id};
        int i = id.indexOf(':');
        if (i >= 0) {
            strings[1] = id.substring(i + 1);
            if (i >= 1) {
                strings[0] = id.substring(0, i);
            }
        }
        return strings;
    }

    public static boolean verifyNamespace(@NotNull String namespace) {
        for(int i = 0; i < namespace.length(); ++i) {
            if (!regex(namespace.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean verifyPath(@NotNull String path) {
        for(int i = 0; i < path.length(); ++i) {
            if (!regex(path.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean regex(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }
    
    protected interface ExtraData { }

    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getMinecraftKeyClass(), String.class, String.class),
                this.namespace, this.path
        );
    }
    
}
