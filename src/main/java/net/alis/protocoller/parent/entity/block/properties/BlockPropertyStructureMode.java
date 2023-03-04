package net.alis.protocoller.parent.entity.block.properties;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.network.chat.ChatComponent;

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

    public static BlockPropertyStructureMode getById(int id) {
        for(BlockPropertyStructureMode structureMode : values()) {
            if(structureMode.id == id) return structureMode;
        }
        return null;
    }

    public Enum<?> original() {
        return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getBlockPropertyStructureModeEnum(), this.id);
    }

}