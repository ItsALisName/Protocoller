package net.alis.protocoller.samples.inventory;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;

public enum InventoryClickType {

    PICKUP(0, "pickup"),
    QUICK_MOVE(1, "quick_move"),
    SWAP(2, "swap"),
    CLONE(3, "clone"),
    THROW(4, "throw"),
    QUICK_CRAFT(5, "quick_craft"),
    PICKUP_ALL(6, "pickup_all");

    private final int id;
    private final String name;

    InventoryClickType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static InventoryClickType getById(int id) {
        Utils.checkClassSupportability(clazz(), "InventoryClickType", false);
        for(InventoryClickType clickType : values()) {
            if(clickType.id == id) return clickType;
        }
        return null;
    }

    public Enum<?> original() {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getInventoryClickTypeEnum();
    }
}
