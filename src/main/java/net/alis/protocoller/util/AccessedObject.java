package net.alis.protocoller.util;

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
            new IllegalArgumentException("Object cannot be null!").printStackTrace();
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
            field.setAccessible(true);
            if(field.getType() == type) {
                if(start == index) {
                    try {
                        return (PARAM) field.get(this.object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to read field with type '" +
                                type.getSimpleName() +
                                "' numbered '" + index +
                                "' in object '" + this.object.getClass().getSimpleName() + "'" +
                                "\nReason: '" + e.getCause().getMessage() + "'",
                                e
                        );
                    }
                }
                start += 1; continue;
            }
        }
        PARAM param = readSuperclass(index, type);
        if(param != null) {
            return param;
        }
        if(start == 0) {
            throw new RuntimeException("Field with type '" + type.getSimpleName() +
                    "' at number '" + index +
                    "' in object '" + this.object.getClass().getSimpleName() + "' was not found");
        } else {
            throw new RuntimeException("Failed to read field with type '" +
                    type.getSimpleName() +
                    "' numbered '" + index +
                    "' in object '" + this.object.getClass().getSimpleName() + "'"
            );
        }
    }

    public void write(int index, Object param) {
        int start = 0;
        Class<?> type = param.getClass();
        for(Field field : this.fields) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(start == index) {
                    try {
                        field.set(this.object, param);
                        return;
                    } catch (IllegalAccessException e) {
                        String reason = "";
                        if(e.getCause() != null) {
                            reason = e.getCause().getMessage();
                        } else {
                            reason = "UNKNOWN REASON";
                        }
                        throw new RuntimeException("Failed to write field with type '" +
                                type.getSimpleName() +
                                "' numbered '" + index +
                                "' in object '" + this.object.getClass().getSimpleName() + "'" +
                                " because: '" + reason + "'",
                                e
                        );
                    }
                }
                start += 1; continue;
            }
        }
        try {
            writeSuperclass(index, param);
            return;
        } catch (Exception ignored) {}
        if(start == 0) {
            throw new RuntimeException("Field with type '" + type.getSimpleName() +
                    "' at number '" + index +
                    "' in object '" + this.object.getClass().getSimpleName() + "' was not found");
        } else {
            throw new RuntimeException("Failed to write field with type '" +
                    type.getSimpleName() +
                    "' numbered '" + index +
                    "' in object '" + this.object.getClass().getSimpleName() + "'"
            );
        }
    }

    public void writeSpecify(int index, Class<?> specify, Object param) {
        int start = 0;
        for(Field field : this.fields) {
            field.setAccessible(true);
            if(field.getType() == specify && start == index) {
                try {
                    field.set(this.object, param);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to write field with type '" +
                            specify.getSimpleName() +
                            "' numbered '" + index +
                            "' in object '" + this.object.getClass().getSimpleName() + "'" +
                            "\nReason: '" + e.getCause().getMessage() + "'",
                            e
                    );
                }
            }
            start += 1;
        }
        writeSpecifySuperclass(index, specify, param);
    }


    public <PARAM> PARAM readSuperclass(int index, Class<?> type) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        return (PARAM) field.get(this.object);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to read field from object!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] From object: '" + this.object.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
        return null;
    }

    public void writeSuperclass(int index, Object param) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == param.getClass()) {
                if(index == start) {
                    try {
                        field.set(this.object, param);
                        return;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to write field in object(Superclass)!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] In object: '" + this.object.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + param.getClass().getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
    }

    public void writeSpecifySuperclass(int index, Class<?> type, Object param) {
        int start = 0;
        for(Field field : this.object.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(index == start) {
                    try {
                        field.set(this.object, param);
                        return;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(
                                "Failed to write field in object(Superclass)!\n" +
                                        "\n[Protocoller] Details: " +
                                        "\n[Protocoller] In object: '" + this.object.toString() + "'" +
                                        "\n[Protocoller] Requested field type: '" + type.getSimpleName() + "'" +
                                        "\n[Protocoller] Requested field index: '" + index + "'",
                                e
                        );
                    }
                }
                start += 1;
            }
        }
    }
}
