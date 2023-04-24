package net.alis.protocoller.util;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AccessedObject {

    private final Object object;
    private final List<Field> fields = new ArrayList<>();
    private final List<Field> superclassFields = new ArrayList<>();

    public AccessedObject(Object object) {
        this.object = object;
        if(object == null) {
            new IllegalArgumentException("AccessedObject cannot be created with null input object").printStackTrace();
            return;
        }
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            this.fields.add(field);
        }
        if(object.getClass().getSuperclass() != null) {
            for(Field sField : object.getClass().getSuperclass().getDeclaredFields()) {
                sField.setAccessible(true);
                this.superclassFields.add(sField);
            }
        }
    }

    public AccessedObject(Object o, boolean ignoreStatic) {
        this.object = o;
        if(object == null) {
            new IllegalArgumentException("AccessedObject cannot be created with null input object").printStackTrace();
            return;
        }
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(ignoreStatic && field.toGenericString().contains("static ")) continue;
            this.fields.add(field);
        }
        if(object.getClass().getSuperclass() != null) {
            for(Field sField : object.getClass().getSuperclass().getDeclaredFields()) {
                sField.setAccessible(true);
                if(ignoreStatic && sField.toGenericString().contains("static ")) continue;
                this.superclassFields.add(sField);
            }
        }
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Field> getSuperclassFields() {
        return superclassFields;
    }

    public Object getOriginal() {
        return object;
    }

    public <PARAM> PARAM invoke(Method method, Object... params) {
        return Reflect.callMethod(this.object, method, false, params);
    }

    public <PARAM> PARAM readField(@NotNull Field field) {
        field.setAccessible(true);
        return Reflect.readField(this.object, field, false);
    }

    public void writeField(Field field, Object o) {
        Reflect.writeField(this.object, field, o, false);
    }

    public Field getField(String name) {
        for(Field f : fields) if(f.getName().equalsIgnoreCase(name)) return f;
        return null;
    }

    public Field getSuperclassField(String name) {
        for(Field f : superclassFields) if(f.getName().equalsIgnoreCase(name)) return f;
        return null;
    }

    public <PARAM> PARAM readNamedField(String name) {
        for(Field namedField : fields) {
            if(namedField.getName().equalsIgnoreCase(name)) return Reflect.readField(this.object, namedField, false);
        }
        return null;
    }

    public void writeNamedField(String name, Object value) {
        for(Field namedField : fields) {
            if(namedField.getName().equalsIgnoreCase(name))
                Reflect.writeField(this.object, namedField, value, false);
        }
    }

    public <PARAM> PARAM readNamedSuperclassField(String name) {
        for(Field field : superclassFields) {
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(name)) return Reflect.readField(this.object, field, false);
        }
        return null;
    }

    public void writeNamedSuperclassField(String name, Object value) {
        for(Field namedField : superclassFields) {
            if(namedField.getName().equalsIgnoreCase(name))
                Reflect.writeField(this.object, namedField, value, false);
        }
    }

    public <PARAM> PARAM readField(int index) {
        int start = 0;
        for(Field field : this.fields) {
            if(start == index) return Reflect.readField(this.object, field, false);
            start += 1;
        }
        return null;
    }

    public <PARAM> PARAM readSuperclassField(int index) {
        int start = 0;
        for(Field field : this.superclassFields) {
            if(start == index) return Reflect.readField(this.object, field, false);
            start += 1;
        }
        return null;
    }

    public <PARAM> PARAM readField(@NotNull Predicate<Version> versionPredicate, int index1, int index2) {
        if(versionPredicate.test(IProtocolAccess.get().getServer().getVersion())) {
            return this.readField(index1);
        } else {
            return this.readField(index2);
        }
    }

    public <PARAM> PARAM readField(@NotNull Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) {
        if(versionPredicate.test(IProtocolAccess.get().getServer().getVersion())) {
            return this.readField(index1, type);
        } else {
            return this.readField(index2, type);
        }
    }

    public <PARAM> PARAM readSuperclassField(@NotNull Predicate<Version> p, int index1, int index2) {
        if(p.test(IProtocolAccess.get().getServer().getVersion())) {
            return this.readSuperclassField(index1);
        } else {
            return this.readSuperclassField(index2);
        }
    }

    public <PARAM> PARAM readSuperclassField(@NotNull Predicate<Version> p, int index1, int index2, Class<?> type) {
        if(p.test(IProtocolAccess.get().getServer().getVersion())) {
            return this.readSuperclassField(index1, type);
        } else {
            return this.readSuperclassField(index2, type);
        }
    }

    public <PARAM> PARAM readField(int index, Class<?> type) {
        int start = 0;
        for(Field field : this.fields) {
            if(field.getType() == type) {
                if(start == index) return Reflect.readField(this.object, field, false);
                start += 1; continue;
            }
        }
        return new ExceptionBuilder().setIgnored(true).getReflectionExceptions().fieldNotFound(type, index, this.object.getClass()).throwException();
    }

    public void write(int index, @NotNull Object param) {
        int start = 0;
        Class<?> type = param.getClass();
        for(Field field : this.fields) {
            if(field.getType() == type) {
                field.setAccessible(true);
                if(start == index) Reflect.writeField(this.object, field, param, false);
                start += 1; continue;
            }
        }
        new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, this.object.getClass()).throwException();
    }

    public void write(@NotNull Predicate<Version> versionPredicate, int index1, int index2, Object value) {
        if(versionPredicate.test(IProtocolAccess.get().getServer().getVersion())) {
            write(index1, value);
        } else {
            write(index2, value);
        }
    }

    public void writeSpecify(int index, Class<?> specify, Object param) {
        int start = 0;
        for(Field field : this.fields) {
            field.setAccessible(true);
            if(field.getType() == specify && start == index) Reflect.writeField(this.object, field, param, false);
            start += 1;
        }
        writeSpecifySuperclass(index, specify, param);
    }

    public void writeSpecify(Predicate<Version> versionPredicate, int index1, int index2, Class<?> specify, Object param) {
        if(versionPredicate.test(IProtocolAccess.get().getServer().getVersion())) {
            writeSpecify(index1, specify, param);
        } else {
            writeSpecify(index2, specify, param);
        }
    }

    public <PARAM> PARAM readSuperclassField(int index, Class<?> type) {
        int start = 0;
        for(Field field : superclassFields) {
            if(field.getType() == type) {
                if(index == start) return Reflect.readField(this.object, field, false);
                start += 1;
            }
        }
        return null;
    }

    public void writeSuperclass(int index, Object param) {
        int start = 0;
        for(Field field : superclassFields) {
            if(field.getType() == param.getClass()) {
                if(index == start) Reflect.writeField(this.object, field, param, false);
                start += 1;
            }
        }
    }

    public void writeSpecifySuperclass(int index, Class<?> type, Object param) {
        int start = 0;
        for(Field field : superclassFields) {
            if(field.getType() == type) {
                if(index == start) Reflect.writeField(this.object, field, param, false);
                start += 1;
            }
        }
    }
}
