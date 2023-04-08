package net.alis.protocoller.samples.craftbukkit;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
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
        return Reflect.callMethod(null, iBlockDataFromMaterial, false, material, data);
    }

    public static MaterialData getMaterialFromBlockData(Object IBlockData) {
        return Reflect.callMethod(null, materialDataFromIBlockData, false, IBlockData);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material, byte data) {
        return Reflect.callMethod(null, itemFromMaterial, false, material, data);
    }

    public static MaterialData getMaterialData(Object minecraftItem) {
        return Reflect.callMethod(null, materialDataFromItem, false, minecraftItem);
    }

    public static Material getMaterialFromBlock(Object minecraftBlock) {
        return Reflect.callMethod(null, materialFromMinecraftBlock, false, minecraftBlock);
    }

    public static Material getMaterialFromItem(Object minecraftItem) {
        return Reflect.callMethod(null, materialFromMinecraftItem, false, minecraftItem);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material) {
        return Reflect.callMethod(null, itemFromMaterial$1, false, material);
    }

    public static <MinecraftBlock> MinecraftBlock getBlock(Material material) {
        return Reflect.callMethod(null, minecraftBlockFromMaterial, false, material);
    }

    public static <MinecraftEntityTypes> MinecraftEntityTypes getEntityTypes(EntityType type) {
        return Reflect.callMethod(null, minecraftEntityTypesFromEntityType, false, type);
    }

    public static EntityType getEntityType(Object minecraftEntityTypes) {
        return Reflect.callMethod(null, entityTypeFromMinecraftEntityTypes, false, minecraftEntityTypes);
    }

    public static Material toLegacy(Material material) {
        return Reflect.callMethod(null, toLegacyMaterial, false, material);
    }

    public static Material fromLegacy(Material material) {
        return Reflect.callMethod(null, fromLegacyMaterial, false, material);
    }

    public static Material fromLegacy(MaterialData data) {
        return Reflect.callMethod(null, fromLegacyMaterialData, false, data);
    }

    public static Material fromLegacy(Material material, boolean itemPriority) {
        return Reflect.callMethod(null, fromLegacyMaterialData$1, false, material, itemPriority);
    }

    //public static BlockData fromLegacy(Material material, byte data) {
    //    return Reflect.callMethod(null, fromLegacyBlockData, material, data);
    //}

    public static Material getMaterial(String material, int version) {
        return Reflect.callMethod(null, materialFromVersion, false, material, version);
    }

    public static String getMappingsVersion() {
        return Reflect.callMethod(null, getMappingsVersion, false);
    }

    public static int getDataVersion() {
        return Reflect.callMethod(null, getDataVersion, false);
    }

    public static byte[] serializeItem(ItemStack itemStack) {
        return Reflect.callMethod(null, serializeItem, false, itemStack);
    }

    public static ItemStack deserializeItem(byte[] bytes) {
        return Reflect.callMethod(null, deserializeItem, false, bytes);
    }

    public static byte[] serializeEntity(Entity entity) {
        return Reflect.callMethod(null, serializeEntity, false, entity);
    }

    public static void init() {
        iBlockDataFromMaterial = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getBlock", ClassesContainer.get().getIBlockDataClass(), true, Material.class, byte.class);
        materialDataFromIBlockData = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", MaterialData.class, true, ClassesContainer.get().getIBlockDataClass());
        itemFromMaterial = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getItem", ClassesContainer.get().getMinecraftItemClass(), true, Material.class, short.class);
        materialDataFromItem = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterialData", MaterialData.class, true, ClassesContainer.get().getMinecraftItemClass());
        materialFromMinecraftBlock = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, true, ClassesContainer.get().getMinecraftBlockClass());
        materialFromMinecraftItem = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, true, ClassesContainer.get().getMinecraftItemClass());
        itemFromMaterial$1 = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getItem", ClassesContainer.get().getMinecraftItemClass(), true, Material.class);
        minecraftBlockFromMaterial = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getBlock", ClassesContainer.get().getMinecraftBlockClass(), true, Material.class);
        minecraftEntityTypesFromEntityType = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getEntityTypes", ClassesContainer.get().getEntityTypesClass(), true, EntityType.class);
        entityTypeFromMinecraftEntityTypes = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getEntityType", EntityType.class, true, ClassesContainer.get().getEntityTypesClass());
        toLegacyMaterial = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "toLegacy", Material.class, true, Material.class);
        fromLegacyMaterial = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, true, Material.class);
        fromLegacyMaterialData = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, true, MaterialData.class);
        fromLegacyMaterialData$1 = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", Material.class, true, MaterialData.class, boolean.class);
        //fromLegacyBlockData = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "fromLegacy", BlockData.class, Material.class, byte.class);
        materialFromVersion = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMaterial", Material.class, true, String.class, int.class);
        getMappingsVersion = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getMappingsVersion", String.class, true);
        getDataVersion = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "getDataVersion", int.class, true);
        serializeItem = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "serializeItem", byte[].class, true, ItemStack.class);
        deserializeItem = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "deserializeItem", ItemStack.class, true, byte[].class);
        serializeEntity = Reflect.getMethod(ClassesContainer.get().getCraftMagicNumbersClass(), "serializeEntity", byte[].class, true, Entity.class);
    }

    private static Method materialDataFromIBlockData, iBlockDataFromMaterial, itemFromMaterial, materialDataFromItem, materialFromMinecraftBlock, materialFromMinecraftItem,
    itemFromMaterial$1, minecraftBlockFromMaterial, minecraftEntityTypesFromEntityType, entityTypeFromMinecraftEntityTypes, toLegacyMaterial, fromLegacyMaterial, fromLegacyMaterialData,
    fromLegacyMaterialData$1, fromLegacyBlockData, materialFromVersion, getMappingsVersion, getDataVersion, serializeItem, deserializeItem, serializeEntity;

}
