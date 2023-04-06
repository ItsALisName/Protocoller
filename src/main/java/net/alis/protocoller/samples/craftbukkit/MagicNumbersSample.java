package net.alis.protocoller.samples.craftbukkit;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Method;

public class MagicNumbersSample {

    public static <IBlockData> IBlockData getBlock(MaterialData materialData) {
        return getBlock(materialData.getItemType(), materialData.getData());
    }

    public static <IBlockData> IBlockData getBlock(Material material, byte data) {
        return BaseReflection.callMethod(null, iBlockDataFromMaterial, false, material, data);
    }

    public static MaterialData getMaterialFromBlockData(Object IBlockData) {
        return BaseReflection.callMethod(null, materialDataFromIBlockData, false, IBlockData);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material, byte data) {
        return BaseReflection.callMethod(null, itemFromMaterial, false, material, data);
    }

    public static MaterialData getMaterialData(Object minecraftItem) {
        return BaseReflection.callMethod(null, materialDataFromItem, false, minecraftItem);
    }

    public static Material getMaterialFromBlock(Object minecraftBlock) {
        return BaseReflection.callMethod(null, materialFromMinecraftBlock, false, minecraftBlock);
    }

    public static Material getMaterialFromItem(Object minecraftItem) {
        return BaseReflection.callMethod(null, materialFromMinecraftItem, false, minecraftItem);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material) {
        return BaseReflection.callMethod(null, itemFromMaterial$1, false, material);
    }

    public static <MinecraftBlock> MinecraftBlock getBlock(Material material) {
        return BaseReflection.callMethod(null, minecraftBlockFromMaterial, false, material);
    }

    public static <MinecraftEntityTypes> MinecraftEntityTypes getEntityTypes(EntityType type) {
        return BaseReflection.callMethod(null, minecraftEntityTypesFromEntityType, false, type);
    }

    public static EntityType getEntityType(Object minecraftEntityTypes) {
        return BaseReflection.callMethod(null, entityTypeFromMinecraftEntityTypes, false, minecraftEntityTypes);
    }

    public static Material toLegacy(Material material) {
        return BaseReflection.callMethod(null, toLegacyMaterial, false, material);
    }

    public static Material fromLegacy(Material material) {
        return BaseReflection.callMethod(null, fromLegacyMaterial, false, material);
    }

    public static Material fromLegacy(MaterialData data) {
        return BaseReflection.callMethod(null, fromLegacyMaterialData, false, data);
    }

    public static Material fromLegacy(Material material, boolean itemPriority) {
        return BaseReflection.callMethod(null, fromLegacyMaterialData$1, false, material, itemPriority);
    }

    //public static BlockData fromLegacy(Material material, byte data) {
    //    return BaseReflection.callMethod(null, fromLegacyBlockData, material, data);
    //}

    public static Material getMaterial(String material, int version) {
        return BaseReflection.callMethod(null, materialFromVersion, false, material, version);
    }

    public static String getMappingsVersion() {
        return BaseReflection.callMethod(null, getMappingsVersion, false);
    }

    public static int getDataVersion() {
        return BaseReflection.callMethod(null, getDataVersion, false);
    }

    public static byte[] serializeItem(ItemStack itemStack) {
        return BaseReflection.callMethod(null, serializeItem, false, itemStack);
    }

    public static ItemStack deserializeItem(byte[] bytes) {
        return BaseReflection.callMethod(null, deserializeItem, false, bytes);
    }

    public static byte[] serializeEntity(Entity entity) {
        return BaseReflection.callMethod(null, serializeEntity, false, entity);
    }

    public static void init() {
        iBlockDataFromMaterial = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getBlock", ClassesContainer.get().getIBlockDataClass(), Material.class, byte.class);
        materialDataFromIBlockData = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", MaterialData.class, ClassesContainer.get().getIBlockDataClass());
        itemFromMaterial = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getItem", ClassesContainer.get().getMinecraftItemClass(), Material.class, short.class);
        materialDataFromItem = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterialData", MaterialData.class, ClassesContainer.get().getMinecraftItemClass());
        materialFromMinecraftBlock = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, ClassesContainer.get().getMinecraftBlockClass());
        materialFromMinecraftItem = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, ClassesContainer.get().getMinecraftItemClass());
        itemFromMaterial$1 = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getItem", ClassesContainer.get().getMinecraftItemClass(), Material.class);
        minecraftBlockFromMaterial = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getBlock", ClassesContainer.get().getMinecraftBlockClass(), Material.class);
        minecraftEntityTypesFromEntityType = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getEntityTypes", ClassesContainer.get().getEntityTypesClass(), EntityType.class);
        entityTypeFromMinecraftEntityTypes = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getEntityType", EntityType.class, ClassesContainer.get().getEntityTypesClass());
        toLegacyMaterial = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "toLegacy", Material.class, Material.class);
        fromLegacyMaterial = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, Material.class);
        fromLegacyMaterialData = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, MaterialData.class);
        fromLegacyMaterialData$1 = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, MaterialData.class, boolean.class);
        //fromLegacyBlockData = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", BlockData.class, Material.class, byte.class);
        materialFromVersion = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, String.class, int.class);
        getMappingsVersion = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMappingsVersion", String.class);
        getDataVersion = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getDataVersion", int.class);
        serializeItem = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "serializeItem", byte[].class, ItemStack.class);
        deserializeItem = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "deserializeItem", ItemStack.class, byte[].class);
        serializeEntity = BaseReflection.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "serializeEntity", byte[].class, Entity.class);
    }

    private static Method materialDataFromIBlockData, iBlockDataFromMaterial, itemFromMaterial, materialDataFromItem, materialFromMinecraftBlock, materialFromMinecraftItem,
    itemFromMaterial$1, minecraftBlockFromMaterial, minecraftEntityTypesFromEntityType, entityTypeFromMinecraftEntityTypes, toLegacyMaterial, fromLegacyMaterial, fromLegacyMaterialData,
    fromLegacyMaterialData$1, fromLegacyBlockData, materialFromVersion, getMappingsVersion, getDataVersion, serializeItem, deserializeItem, serializeEntity;

}
