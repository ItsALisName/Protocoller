package net.alis.protocoller.samples.entity.block.properties;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BlockPropertyStructureMode {
    SAVE(0, "save"),
    LOAD(1, "load"),
    CORNER(2, "corner"),
    DATA(3, "data");

    private final int id;
    private final String name;
    private final ChatComponent optionName;

    BlockPropertyStructureMode(int id, String name) {
        this.name = name;
        this.id = id;
        this.optionName = new ChatComponent("structure_block.mode_info." + name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChatComponent getOptionName() {
        return optionName;
    }

    @Contract(pure = true)
    public static @Nullable BlockPropertyStructureMode getById(int id) {
        Utils.checkClassSupportability(clazz(), "BlockPropertyStructureMode", false);
        for(BlockPropertyStructureMode structureMode : values()) {
            if(structureMode.id == id) return structureMode;
        }
        return null;
    }

    public @NotNull Enum<?> original() {
        Utils.checkClassSupportability(clazz(), "BlockPropertyStructureMode", false);
        return Reflect.readEnumValue((Class<? extends Enum<?>>)clazz(), this.id);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBlockPropertyStructureModeEnum();
    }

}
