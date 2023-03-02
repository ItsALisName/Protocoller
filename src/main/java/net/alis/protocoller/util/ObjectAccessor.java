package net.alis.protocoller.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectAccessor {

    private final Object object;
    private final List<Field> fields;

    public ObjectAccessor(Object object) {
        this.object = object;
        this.fields = new ArrayList<>();
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true); this.fields.add(field);
        }
    }

    public Object getObject() {
        return object;
    }

    public <O> O read(int index, Class<?> type) {
        int start = 0;
        for(Field field : this.fields) {
            field.setAccessible(true);
            if(field.getType() == type) {
                if(start == index) {
                    try { return (O) field.get(this.object); } catch (IllegalAccessException e) {
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
                        throw new RuntimeException("Failed to write field with type '" +
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
    }

}
