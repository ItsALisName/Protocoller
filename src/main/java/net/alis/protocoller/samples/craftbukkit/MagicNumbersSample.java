package net.alis.protocoller.samples.craftbukkit;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
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
        return Reflection.callMethod(null, iBlockDataFromMaterial, material, data);
    }

    public static MaterialData getMaterialFromBlockData(Object IBlockData) {
        return Reflection.callMethod(null, materialDataFromIBlockData, IBlockData);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material, byte data) {
        return Reflection.callMethod(null, itemFromMaterial, material, data);
    }

    public static MaterialData getMaterialData(Object minecraftItem) {
        return Reflection.callMethod(null, materialDataFromItem, minecraftItem);
    }

    public static Material getMaterialFromBlock(Object minecraftBlock) {
        return Reflection.callMethod(null, materialFromMinecraftBlock, minecraftBlock);
    }

    public static Material getMaterialFromItem(Object minecraftItem) {
        return Reflection.callMethod(null, materialFromMinecraftItem, minecraftItem);
    }

    public static <MinecraftItem> MinecraftItem getItem(Material material) {
        return Reflection.callMethod(null, itemFromMaterial$1, material);
    }

    public static <MinecraftBlock> MinecraftBlock getBlock(Material material) {
        return Reflection.callMethod(null, minecraftBlockFromMaterial, material);
    }

    public static <MinecraftEntityTypes> MinecraftEntityTypes getEntityTypes(EntityType type) {
        return Reflection.callMethod(null, minecraftEntityTypesFromEntityType, type);
    }

    public static EntityType getEntityType(Object minecraftEntityTypes) {
        return Reflection.callMethod(null, entityTypeFromMinecraftEntityTypes, minecraftEntityTypes);
    }

    public static Material toLegacy(Material material) {
        return Reflection.callMethod(null, toLegacyMaterial, material);
    }

    public static Material fromLegacy(Material material) {
        return Reflection.callMethod(null, fromLegacyMaterial, material);
    }

    public static Material fromLegacy(MaterialData data) {
        return Reflection.callMethod(null, fromLegacyMaterialData, data);
    }

    public static Material fromLegacy(Material material, boolean itemPriority) {
        return Reflection.callMethod(null, fromLegacyMaterialData$1, material, itemPriority);
    }

    //public static BlockData fromLegacy(Material material, byte data) {
    //    return Reflection.callMethod(null, fromLegacyBlockData, material, data);
    //}

    public static Material getMaterial(String material, int version) {
        return Reflection.callMethod(null, materialFromVersion, material, version);
    }

    public static String getMappingsVersion() {
        return Reflection.callMethod(null, getMappingsVersion);
    }

    public static int getDataVersion() {
        return Reflection.callMethod(null, getDataVersion);
    }

    public static byte[] serializeItem(ItemStack itemStack) {
        return Reflection.callMethod(null, serializeItem, itemStack);
    }

    public static ItemStack deserializeItem(byte[] bytes) {
        return Reflection.callMethod(null, deserializeItem, (Object) bytes);
    }

    public static byte[] serializeEntity(Entity entity) {
        return Reflection.callMethod(null, serializeEntity, entity);
    }

    public static Entity deserializeEntity(byte[] bytes) {
        return Reflection.callMethod(null, deserializeEntity, (Object) bytes);
    }

    public static void init() {
        iBlockDataFromMaterial = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getBlock", ClassesContainer.INSTANCE.getIBlockDataClass(), Material.class, byte.class);
        materialDataFromIBlockData = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMaterial", MaterialData.class, ClassesContainer.INSTANCE.getIBlockDataClass());
        itemFromMaterial = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getItem", ClassesContainer.INSTANCE.getMinecraftItemClass(), Material.class, byte.class);
        materialDataFromItem = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMaterialData", MaterialData.class, ClassesContainer.INSTANCE.getMinecraftItemClass());
        materialFromMinecraftBlock = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMaterial", Material.class, ClassesContainer.INSTANCE.getMinecraftBlockClass());
        materialFromMinecraftItem = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMaterial", Material.class, ClassesContainer.INSTANCE.getMinecraftItemClass());
        itemFromMaterial$1 = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getItem", ClassesContainer.INSTANCE.getMinecraftItemClass(), Material.class);
        minecraftBlockFromMaterial = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getBlock", ClassesContainer.INSTANCE.getMinecraftBlockClass(), Material.class);
        minecraftEntityTypesFromEntityType = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getEntityTypes", ClassesContainer.INSTANCE.getEntityTypesClass(), EntityType.class);
        entityTypeFromMinecraftEntityTypes = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getEntityType", EntityType.class, ClassesContainer.INSTANCE.getEntityTypesClass());
        toLegacyMaterial = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "toLegacy", Material.class, Material.class);
        fromLegacyMaterial = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "fromLegacy", Material.class, Material.class);
        fromLegacyMaterialData = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "fromLegacy", Material.class, MaterialData.class);
        fromLegacyMaterialData$1 = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "fromLegacy", Material.class, MaterialData.class, boolean.class);
        //fromLegacyBlockData = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "fromLegacy", BlockData.class, Material.class, byte.class);
        materialFromVersion = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMaterial", Material.class, String.class, int.class);
        getMappingsVersion = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getMappingsVersion", String.class);
        getDataVersion = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "getDataVersion", int.class);
        serializeItem = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "serializeItem", byte[].class, ItemStack.class);
        deserializeItem = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "deserializeItem", ItemStack.class, byte[].class);
        serializeEntity = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "serializeEntity", byte[].class, Entity.class);
        deserializeEntity = Reflection.getMethod(ClassesContainer.INSTANCE.getCraftMagicNumbersClass(), "deserializeEntity", Entity.class, byte[].class);
    }

    private static Method materialDataFromIBlockData, iBlockDataFromMaterial, itemFromMaterial, materialDataFromItem, materialFromMinecraftBlock, materialFromMinecraftItem,
    itemFromMaterial$1, minecraftBlockFromMaterial, minecraftEntityTypesFromEntityType, entityTypeFromMinecraftEntityTypes, toLegacyMaterial, fromLegacyMaterial, fromLegacyMaterialData,
    fromLegacyMaterialData$1, fromLegacyBlockData, materialFromVersion, getMappingsVersion, getDataVersion, serializeItem, deserializeItem, serializeEntity, deserializeEntity;

}
