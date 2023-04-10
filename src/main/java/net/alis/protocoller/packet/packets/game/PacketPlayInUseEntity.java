package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketBuilder;
import net.alis.protocoller.plugin.v0_0_4.network.packet.PacketDataSerializer;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.PacketUtils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.packet.type.PlayInPacket;
import net.alis.protocoller.samples.entity.Hand;
import net.alis.protocoller.samples.phys.Vector3D;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PacketPlayInUseEntity implements PlayInPacket {

    private final PacketDataContainer packetData;
    private int entityId;
    private boolean sneaking;
    private EntityUseAction entityUseAction;

    private @Nullable Vector3D vector;
    private @Nullable Hand hand;

    private final PacketBuilder creator = PacketBuilder.get(getPacketType());

    public PacketPlayInUseEntity(@NotNull PacketDataContainer packetData) {
        PacketUtils.checkPacketCompatibility(packetData.getType(), this.getPacketType());
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.entityUseAction = EntityUseAction.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getEntityUseActionEnum()).ordinal());
        if(creator.getConstructorIndicator().getLevel() < 2) {
            this.vector = new Vector3D(packetData.readObject(0, ClassAccessor.get().getVector3dClass()));
            if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
                this.hand = Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassAccessor.get().getHandEnum()).ordinal());
                if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
                    this.sneaking = packetData.readBoolean(0);
                }
            } else {
                this.hand = Hand.MAIN_HAND;
            }
        } else {
            this.vector = Vector3D.ZERO;
            this.hand = Hand.MAIN_HAND;
            this.sneaking = packetData.readBoolean(0);
        }
    }

    public PacketPlayInUseEntity(Entity entity, boolean sneaking, EntityUseAction entityUseAction, @Nullable Vector3D vector, @Nullable Hand hand) {
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
                    params = new IndexedParam[] {
                        new IndexedParam<>(entity.getEntityId(), 0),
                        new IndexedParam<>(entityUseAction.original(), 0),
                        new IndexedParam<>(vector.createOriginal(), 0),
                        new IndexedParam<>(hand.original(), 0),
                        new IndexedParam<>(sneaking, 0)
                    };
                } else {
                    params = new IndexedParam[] {
                        new IndexedParam<>(entity.getEntityId(), 0),
                        new IndexedParam<>(entityUseAction.original(), 0),
                        new IndexedParam<>(vector.createOriginal(), 0)
                    };
                }
                this.packetData = new PacketDataSerializer(creator.buildPacket(params));
                break;
            }
            case 1: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, MinecraftReflection.getMinecraftEntity(entity)));
                break;
            }
            case 2: {
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, entity.getEntityId(), sneaking, entityUseAction.original()));
                break;
            }
            default: {
                this.packetData = null;
                break;
            }
        }
        this.entityId = entity.getEntityId();
        this.sneaking = sneaking;
        this.entityUseAction = entityUseAction;
        this.vector = vector;
        this.hand = hand;
    }

    public PacketPlayInUseEntity(int entityId, boolean sneaking, EntityUseAction entityUseAction, @Nullable Vector3D vector, @Nullable Hand hand) {
        this(GlobalProvider.get().getServer().getEntityList().getEntity(entityId), sneaking, entityUseAction, vector, hand);
    }

    public PacketPlayInUseEntity(int entityId, EntityUseAction entityUseAction, @Nullable Vector3D vector) {
        this(GlobalProvider.get().getServer().getEntityList().getEntity(entityId), true, entityUseAction, vector, Hand.MAIN_HAND);
    }

    /*
     * 1.17 and bigger
     */
    public PacketPlayInUseEntity(int entityId, boolean sneaking, EntityUseAction entityUseAction) {
        this(GlobalProvider.get().getServer().getEntityList().getEntity(entityId), sneaking, entityUseAction, Vector3D.ZERO, Hand.MAIN_HAND);
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.get().getServer().getEntityList().getEntity(this.entityId);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(0, entityId);
        this.entityId = entityId;
    }

    public void setEntityId(@NotNull Entity entity) {
        this.packetData.writeInt(0, entity.getEntityId());
        this.entityId = entity.getEntityId();
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) this.packetData.writeBoolean(0, sneaking);
        this.sneaking = sneaking;
    }

    public EntityUseAction getEntityUseAction() {
        return entityUseAction;
    }

    public void setEntityUseAction(@NotNull EntityUseAction entityUseAction) {
        this.packetData.writeEnumConstant(0, entityUseAction.original());
        this.entityUseAction = entityUseAction;
    }

    @Nullable
    public Vector3D getVector() {
        return vector;
    }

    public void setVector(@Nullable Vector3D vector) {
        if(GlobalProvider.get().getServer().getVersion().lessThan(Version.v1_17)) this.packetData.writeObject(0, vector.createOriginal());
        this.vector = vector;
    }

    @Nullable
    public Hand getHand() {
        return hand;
    }

    public void setHand(@Nullable Hand hand) {
        if(GlobalProvider.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9) && GlobalProvider.get().getServer().getVersion().lessThan(Version.v1_17)) this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.USE_ENTITY;
    }

    @Override
    public PacketDataContainer getData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getData().getRawPacket();
    }

    public enum EntityUseAction {

        INTERACT(0),
        ATTACK(1),
        INTERACT_AT(2);

        private final int id;

        EntityUseAction(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Contract(pure = true)
        public static @Nullable EntityUseAction getById(int id) {
            for(EntityUseAction useAction : values()) {
                if(useAction.id == id) return useAction;
            }
            return null;
        }

        public @NotNull Enum<?> original() {
            return Reflect.readEnumValue((Class<? extends Enum<?>>) ClassAccessor.get().getEntityUseActionEnum(), this.id);
        }
    }

}
