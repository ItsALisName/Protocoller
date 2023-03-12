package net.alis.protocoller.samples.effect;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MobEffects {
    
    private static MobEffects INSTANCE;

    public static MobEffects instance() {
        return INSTANCE;
    }

    public final RegisteredMobEffect SPEED, SLOWNESS, HASTE, MINING_FATIGUE, STRENGTH, INSTANT_HEALTH, INSTANT_DAMAGE,
            JUMP_BOOST, NAUSEA, REGENERATION, RESISTANCE, FIRE_RESISTANCE, WATER_BREATHING, INVISIBILITY, BLINDNESS, NIGHT_VISION,
            HUNGER, WEAKNESS, POISON, WITHER, HEALTH_BOOST, ABSORPTION, SATURATION, GLOWING, LEVITATION, LUCK, UNLUCK, SNOW_FALLING,
            CONDUIT_POWER, DOLPHINS_GRACE, BAD_OMEN, HERO_OF_THE_VILLAGE, DARKNESS;

    private final Map<Integer, RegisteredMobEffect> access = Maps.newHashMap();

    public MobEffects() {
        SPEED = register(1, "speed", (new MobEffectList(MobEffectInfo.BENEFICIAL, 8171462)));
        SLOWNESS = register(2, "slowness", (new MobEffectList(MobEffectInfo.HARMFUL, 5926017)));
        HASTE = register(3, "haste", (new MobEffectList(MobEffectInfo.BENEFICIAL, 14270531)));
        MINING_FATIGUE = register(4, "mining_fatigue", (new MobEffectList(MobEffectInfo.HARMFUL, 4866583)));
        STRENGTH = register(5, "strength", (new MobEffectAttackDamage(MobEffectInfo.BENEFICIAL, 9643043, 3.0)));
        INSTANT_HEALTH = register(6, "instant_health", new InstantMobEffect(MobEffectInfo.BENEFICIAL, 16262179));
        INSTANT_DAMAGE = register(7, "instant_damage", new InstantMobEffect(MobEffectInfo.HARMFUL, 4393481));
        JUMP_BOOST = register(8, "jump_boost", new MobEffectList(MobEffectInfo.BENEFICIAL, 2293580));
        NAUSEA = register(9, "nausea", new MobEffectList(MobEffectInfo.HARMFUL, 5578058));
        REGENERATION = register(10, "regeneration", new MobEffectList(MobEffectInfo.BENEFICIAL, 13458603));
        RESISTANCE = register(11, "resistance", new MobEffectList(MobEffectInfo.BENEFICIAL, 10044730));
        FIRE_RESISTANCE = register(12, "fire_resistance", new MobEffectList(MobEffectInfo.BENEFICIAL, 14981690));
        WATER_BREATHING = register(13, "water_breathing", new MobEffectList(MobEffectInfo.BENEFICIAL, 3035801));
        INVISIBILITY = register(14, "invisibility", new MobEffectList(MobEffectInfo.BENEFICIAL, 8356754));
        BLINDNESS = register(15, "blindness", new MobEffectList(MobEffectInfo.HARMFUL, 2039587));
        NIGHT_VISION = register(16, "night_vision", new MobEffectList(MobEffectInfo.BENEFICIAL, 2039713));
        HUNGER = register(17, "hunger", new MobEffectList(MobEffectInfo.HARMFUL, 5797459));
        WEAKNESS = register(18, "weakness", (new MobEffectAttackDamage(MobEffectInfo.HARMFUL, 4738376, -4.0)));
        POISON = register(19, "poison", new MobEffectList(MobEffectInfo.HARMFUL, 5149489));
        WITHER = register(20, "wither", new MobEffectList(MobEffectInfo.HARMFUL, 3484199));
        HEALTH_BOOST = register(21, "health_boost", (new MobEffectHealthBoost(MobEffectInfo.BENEFICIAL, 16284963)));
        ABSORPTION = register(22, "absorption", new MobEffectAbsorption(MobEffectInfo.BENEFICIAL, 2445989));
        SATURATION = register(23, "saturation", new InstantMobEffect(MobEffectInfo.BENEFICIAL, 16262179));
        GLOWING = register(24, "glowing", new MobEffectList(MobEffectInfo.NEUTRAL, 9740385));
        LEVITATION = register(25, "levitation", new MobEffectList(MobEffectInfo.HARMFUL, 13565951));
        LUCK = register(26, "luck", (new MobEffectList(MobEffectInfo.BENEFICIAL, 3381504)));
        UNLUCK = register(27, "unluck", (new MobEffectList(MobEffectInfo.HARMFUL, 12624973)));
        SNOW_FALLING = register(28, "slow_falling", new MobEffectList(MobEffectInfo.BENEFICIAL, 16773073));
        CONDUIT_POWER = register(29, "conduit_power", new MobEffectList(MobEffectInfo.BENEFICIAL, 1950417));
        DOLPHINS_GRACE = register(30, "dolphins_grace", new MobEffectList(MobEffectInfo.BENEFICIAL, 8954814));
        BAD_OMEN = register(31, "bad_omen", new MobEffectList(MobEffectInfo.NEUTRAL, 745784));
        HERO_OF_THE_VILLAGE = register(32, "hero_of_the_village", new MobEffectList(MobEffectInfo.BENEFICIAL, 4521796));
        DARKNESS = register(33, "darkness", (new MobEffectList(MobEffectInfo.HARMFUL, 2696993)));
    }

    private RegisteredMobEffect register(int rawId, String id, MobEffectList entry) {
        RegisteredMobEffect registeredMobEffect = new RegisteredMobEffect(rawId, id, entry);
        this.access.put(rawId, registeredMobEffect);
        return registeredMobEffect;
    }

    @Nullable
    public RegisteredMobEffect effectFromId(int id) {
        if(id > 0){
            return this.access.get(id);
        }
        return null;
    }

    public int idFromEffect(MobEffectList effect) {
        for(Map.Entry<Integer, RegisteredMobEffect> e : this.access.entrySet()) {
            if(e.getValue().getEntry().equals(effect)) {
                return e.getKey();
            }
        }
        return -1;
    }
    
    public static class RegisteredMobEffect {
        
        private final int rawId;
        private final String id;
        private final MobEffectList entry;

        private RegisteredMobEffect(int rawId, String id, MobEffectList entry) {
            this.rawId = rawId;
            this.id = id;
            this.entry = entry;
        }

        public final int getRawId() {
            return rawId;
        }

        public final String getId() {
            return id;
        }

        public final MobEffectList getEntry() {
            return entry;
        }
    }
    
    public static void init() {
        INSTANCE = new MobEffects();
    }
    
}
