package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.network.packet.IndexedParam;
import net.alis.protocoller.plugin.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.FastUtilLegacyAdapter;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.inventory.InventoryClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInWindowClick implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int syncId;
    private int revision;
    private int slot;
    private int button;
    private InventoryClickType actionType;
    private ItemStack stack;

    private final boolean legacyPacket = GlobalProvider.instance().getServer().isLegacy();

    public PacketPlayInWindowClick(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), PacketType.Play.Client.WINDOW_CLICK);
        this.packetData = packetData;
        this.stack = packetData.readMinecraftItemStack(0);
        if(legacyPacket) {
            this.syncId = packetData.readShort(0);
            this.revision = packetData.readInt(0);
            this.slot = packetData.readInt(1);
            this.button = packetData.readInt(2);
            if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
                this.actionType = InventoryClickType.getById(packetData.readInt(3));
            } else {
                this.actionType = InventoryClickType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getInventoryClickTypeEnum()).ordinal());
            }
        } else {
            this.syncId = packetData.readInt(1);
            this.revision = packetData.readInt(2);
            this.slot = packetData.readInt(3);
            this.button = packetData.readInt(4);
            this.actionType = InventoryClickType.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.get().getInventoryClickTypeEnum()).ordinal());
        }
    }

    public PacketPlayInWindowClick(int syncId, int revision, int slot, int button, InventoryClickType actionType, ItemStack stack) {
        PacketBuilder packetBuilder = PacketBuilder.get(getPacketType());
        switch (packetBuilder.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
                    params = new IndexedParam[] {
                        new IndexedParam<>(revision, 0),
                        new IndexedParam<>(Integer.valueOf(syncId).shortValue(), 0),
                        new IndexedParam<>(slot, 1),
                        new IndexedParam<>(button, 2),
                        new IndexedParam<>(actionType.getId(), 3),
                        new IndexedParam<>(MinecraftReflection.getMinecraftItemStack(stack), 0)
                    };
                } else {
                    params = new IndexedParam[] {
                            new IndexedParam<>(revision, 0),
                            new IndexedParam<>(Integer.valueOf(syncId).shortValue(), 0),
                            new IndexedParam<>(slot, 1),
                            new IndexedParam<>(button, 2),
                            new IndexedParam<>(actionType.original(), 0),
                            new IndexedParam<>(MinecraftReflection.getMinecraftItemStack(stack), 0)
                    };
                }
                this.packetData = new PacketDataSerializer(packetBuilder.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(packetBuilder.buildPacket(null, syncId, revision, slot, button, actionType.original(), MinecraftReflection.getMinecraftItemStack(stack), FastUtilLegacyAdapter.newInt2ObjectMap(new Object[]{MinecraftReflection.getMinecraftItemStack(stack)})));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.syncId = syncId;
        this.revision = revision;
        this.slot = slot;
        this.button = button;
        this.actionType = actionType;
        this.stack = stack;
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        if(legacyPacket) {
            this.packetData.writeShort(0, Integer.valueOf(syncId).shortValue());
        } else {
            this.packetData.writeInt(1, syncId);
        }
        this.syncId = syncId;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        if(legacyPacket) {
            this.packetData.writeInt(0, revision);
        } else {
            this.packetData.writeInt(2, revision);
        }
        this.revision = revision;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        if(legacyPacket) {
            this.packetData.writeInt(1, slot);
        } else {
            this.packetData.writeInt(3, slot);
        }
        this.slot = slot;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        if(legacyPacket) {
            this.packetData.writeInt(2, button);
        } else {
            this.packetData.writeInt(4, button);
        }
        this.button = button;
    }

    public InventoryClickType getActionType() {
        return actionType;
    }

    public void setActionType(InventoryClickType actionType) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_9)) {
            this.packetData.writeInt(3, actionType.getId());
        } else {
            this.packetData.writeEnumConstant(0, actionType.original());
        }
        this.actionType = actionType;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.packetData.writeMinecraftItemStack(0, stack);
        this.stack = stack;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.WINDOW_CLICK;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }
}
