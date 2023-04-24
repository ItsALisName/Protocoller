package net.alis.protocoller.samples.attributes;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.SpigotConfig;

import java.util.Map;

public class GenericAttributes {

    private static GenericAttributes INSTANCE;

    public static GenericAttributes instance() {
        return INSTANCE;
    }

    public final RegisteredGenericAttribute ATTACK_SPEED, ATTACK_KNOCKBACK, ATTACK_DAMAGE, FLYING_SPEED, MOVEMENT_SPEED, KNOCKBACK_RESISTANCE, MAX_HEALTH, FOLLOW_RANGE,
    ARMOR, ARMOR_TOUGHNESS, LUCK, ZOMBIE_SPAWN_REINFORCEMENTS, HORSE_JUMP_STRENGTH;

    private final Map<String, RegisteredGenericAttribute> access = Maps.newHashMap();

    public GenericAttributes() {
        MAX_HEALTH = register("generic.max_health", (new AttributeRanged("attribute.name.generic.max_health", 20.0, 1.0, SpigotConfig.maxHealth)).setTracked(true));
        FOLLOW_RANGE = register("generic.follow_range", new AttributeRanged("attribute.name.generic.follow_range", 32.0, 0.0, 2048.0));
        KNOCKBACK_RESISTANCE = register("generic.knockback_resistance", new AttributeRanged("attribute.name.generic.knockback_resistance", 0.0, 0.0, 1.0));
        MOVEMENT_SPEED = register("generic.movement_speed", (new AttributeRanged("attribute.name.generic.movement_speed", 0.699999988079071, 0.0, SpigotConfig.movementSpeed)).setTracked(true));
        FLYING_SPEED = register("generic.flying_speed", (new AttributeRanged("attribute.name.generic.flying_speed", 0.4000000059604645, 0.0, 1024.0)).setTracked(true));
        ATTACK_DAMAGE = register("generic.attack_damage", new AttributeRanged("attribute.name.generic.attack_damage", 2.0, 0.0, SpigotConfig.attackDamage));
        ATTACK_KNOCKBACK = register("generic.attack_knockback", new AttributeRanged("attribute.name.generic.attack_knockback", 0.0, 0.0, 5.0));
        ATTACK_SPEED = register("generic.attack_speed", (new AttributeRanged("attribute.name.generic.attack_speed", 4.0, 0.0, 1024.0)).setTracked(true));
        ARMOR = register("generic.armor", (new AttributeRanged("attribute.name.generic.armor", 0.0, 0.0, 30.0)).setTracked(true));
        ARMOR_TOUGHNESS = register("generic.armor_toughness", (new AttributeRanged("attribute.name.generic.armor_toughness", 0.0, 0.0, 20.0)).setTracked(true));
        LUCK = register("generic.luck", (new AttributeRanged("attribute.name.generic.luck", 0.0, -1024.0, 1024.0)).setTracked(true));
        ZOMBIE_SPAWN_REINFORCEMENTS = register("zombie.spawn_reinforcements", new AttributeRanged("attribute.name.zombie.spawn_reinforcements", 0.0, 0.0, 1.0));
        HORSE_JUMP_STRENGTH = register("horse.jump_strength", (new AttributeRanged("attribute.name.horse.jump_strength", 0.7, 0.0, 2.0)).setTracked(true));
    }

    private @NotNull RegisteredGenericAttribute register(String id, AttributeBase attribute) {
        RegisteredGenericAttribute genericAttribute = new RegisteredGenericAttribute(id, (AttributeRanged) attribute);
        this.access.put(id, genericAttribute);
        return genericAttribute;
    }

    public RegisteredGenericAttribute fromId(String id) {
        return this.access.get(id);
    }
    
    public static class RegisteredGenericAttribute {
        
        private final String id;
        private final AttributeRanged attribute;

        private RegisteredGenericAttribute(String id, AttributeRanged attribute) {
            this.id = id;
            this.attribute = attribute;
        }

        public String getId() {
            return id;
        }

        public AttributeRanged getAttribute() {
            return attribute;
        }
    }

    public static void init() {
        INSTANCE = new GenericAttributes();
    }
    
}

