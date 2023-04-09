package net.alis.protocoller.samples.attributes;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.nbt.NBTTagCompound;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class AttributeModifier implements ObjectSample {
    private double value;
    private AttributeOperation operation;
    private Supplier<String> nameGetter;
    private UUID uuid;

    protected AttributeModifier() {}

    public AttributeModifier(UUID uuid, String name, double value, AttributeOperation operation) {
        this(uuid, () -> name, value, operation);
    }

    public AttributeModifier(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.value = accessor.read(0, double.class);
        this.operation = AttributeOperation.getById(((Enum<?>)accessor.read(0, ClassAccessor.get().getAttributeOperationEnum())).ordinal());
        this.nameGetter = () -> ((Supplier<String>)accessor.read(0, Supplier.class)).get();
        this.uuid = accessor.read(0, UUID.class);
    }

    public AttributeModifier(UUID uuid, Supplier<String> nameGetter, double value, AttributeOperation operation) {
        this.uuid = uuid;
        this.nameGetter = nameGetter;
        this.value = value;
        this.operation = operation;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setOperation(AttributeOperation operation) {
        this.operation = operation;
    }

    public void setNameGetter(Supplier<String> nameGetter) {
        this.nameGetter = nameGetter;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.nameGetter.get();
    }

    public AttributeOperation getOperation() {
        return this.operation;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            AttributeModifier attributeModifier = (AttributeModifier) object;
            return Objects.equals(this.uuid, attributeModifier.uuid);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    public NBTTagCompound getNBT() {
        NBTTagCompound compoundTag = new NBTTagCompound();
        compoundTag.setString("Name", this.getName());
        compoundTag.setDouble("Amount", this.value);
        compoundTag.setInteger("Operation", this.operation.getId());
        compoundTag.setUniqueId("UUID", this.uuid);
        return compoundTag;
    }

    @Nullable
    public static AttributeModifier fromNBT(NBTTagCompound nbt) {
        try {
            UUID uUID = nbt.getUniqueId("UUID");
            AttributeOperation operation = AttributeOperation.getById(nbt.getInteger("Operation"));
            return new AttributeModifier(uUID, nbt.getString("Name"), nbt.getDouble("Amount"), operation);
        } catch (Exception e) {
            LogsManager.get().getLogger().warn("Unable to create attribute: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getAttributeModifierClass(), UUID.class, Supplier.class, double.class, ClassAccessor.get().getAttributeOperationEnum()),
                this.uuid, this.nameGetter, this.value, this.operation.original()
        );
    }
}
