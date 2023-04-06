package net.alis.protocoller.util;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AccessedObject {

    private final Object object;
    private final List<Field> fields;

    public AccessedObject(Object object) {
        this.object = object;
        this.fields = new ArrayList<>();
        if(object == null) {
            new IllegalArgumentException("AccessedObject cannot be created with null input object").printStackTrace();
            return;
        }
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            this.fields.add(field);
        }
    }

    public Object getObject() {
        return object;
    }

    public <PARAM> PARAM read(int index, Class<?> type) {
        int start = 0;
        for(Field field : this.fields) {
            if(field.getType() == type) {
                if(start == index) return BaseReflection.readField(this.object, field, false);
                start += 1; continue;
            }
        }
        PARAM param = readSuperclass(index, type);
        if(param != null) return param;
        if(start == 0) {
            return new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, this.object.getClass()).throwException();
        } else {
            return new ExceptionBuilder().getReflectionExceptions().readFieldError(this.object.getClass(), index, type).throwException();
        }
    }

    public void write(int index, @NotNull Object param) {
        int start = 0;
        Class<?> type = param.getClass();
        for(Field field : this.fields) {
            if(field.getType() == type) {
                field.setAccessible(true);
                if(start == index) BaseReflection.writeField(this.object, field, param, false);
                start += 1; continue;
            }
        }
        try {
            writeSuperclass(index, param);
            return;
        } catch (Exception ignored) {}
        if(start == 0) {
            new ExceptionBuilder().getReflectionExceptions().fieldNotFound(type, index, this.object.getClass()).throwException();
        } else {
            new ExceptionBuilder().getReflectionExceptions().writeFieldError(this.object.getClass(), index, type).throwException();
        }
    }

    public void writeSpecify(int index, Class<?> specify, Object param) {
        int start = 0;
        for(Field field : this.fields) {
            field.setAccessible(true);
            if(field.getType() == specify && start == index) BaseReflection.writeField(this.object, field, param, false);
            start += 1;
        }
        writeSpecifySuperclass(index, specify, param);
    }


    public <PARAM> PARAM readSuperclass(int index, Class<?> type) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            if(field.getType() == type) {
                if(index == start) return BaseReflection.readField(this.object, field, false);
                start += 1;
            }
        }
        return null;
    }

    public void writeSuperclass(int index, Object param) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            if(field.getType() == param.getClass()) {
                field.setAccessible(true);
                if(index == start) BaseReflection.writeField(this.object, field, param, false);
                start += 1;
            }
        }
    }

    public void writeSpecifySuperclass(int index, Class<?> type, Object param) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            if(field.getType() == type) {
                field.setAccessible(true);
                if(index == start) BaseReflection.writeField(this.object, field, param, false);
                start += 1;
            }
        }
    }
}
