package net.alis.protocoller.parent.resources;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.exceptions.ResourceKeyInvalidException;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.util.ObjectAccessor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

public class MinecraftKey {

    private final boolean isLegacy = GlobalProvider.instance().getServer().isLegacy();
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
        ObjectAccessor accessor = new ObjectAccessor(originalKey);
        if(isLegacy) {
            this.namespace = accessor.read(0, String.class);
            this.path = accessor.read(1, String.class);
        } else {
            this.namespace = accessor.read(2, String.class);
            this.path = accessor.read(3, String.class);
        }
    }

    private MinecraftKey(String[] id) {
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

    public MinecraftKey copy(UnaryOperator<String> pathFunction) {
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
    
    

    private static String verifyAndBuildNamespace(String namespace, String path) {
        if (!verifyNamespace(namespace)) {
            String normalizedSpace = StringUtils.normalizeSpace(namespace);
            throw new ResourceKeyInvalidException("Non [Skip$1-z0-9_.-] character in namespace of location: " + normalizedSpace + ":" + StringUtils.normalizeSpace(path));
        } else {
            return namespace;
        }
    }

    private static String verifyAndBuildPath(String namespace, String path) {
        if (!verifyPath(path)) {
            throw new ResourceKeyInvalidException("Non [Skip$1-z0-9/._-] character in path of location: " + namespace + ":" + StringUtils.normalizeSpace(path));
        } else {
            return path;
        }
    }

    protected static String[] build(String id) {
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

    private static boolean verifyNamespace(String namespace) {
        for(int i = 0; i < namespace.length(); ++i) {
            if (!regex(namespace.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean verifyPath(String path) {
        for(int i = 0; i < path.length(); ++i) {
            if (!regex(path.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean regex(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }
    
    protected interface ExtraData { }

    public Object createOriginal() {
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getMinecraftKeyClass(), String.class, String.class),
                this.namespace, this.path
        );
    }
    
}
