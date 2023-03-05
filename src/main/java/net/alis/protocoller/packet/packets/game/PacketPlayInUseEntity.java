package net.alis.protocoller.packet.packets.game;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.network.packet.IndexedParam;
import net.alis.protocoller.bukkit.network.packet.PacketBuilder;
import net.alis.protocoller.bukkit.network.packet.PacketDataSerializer;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.AlMinecraftReflection;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.packet.PacketDataContainer;
import net.alis.protocoller.packet.PacketType;
import net.alis.protocoller.parent.phys.Vector3D;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PacketPlayInUseEntity implements Packet {

    private final PacketDataContainer packetData;
    private int entityId;
    private boolean sneaking;
    private EntityUseAction entityUseAction;

    private @NotOnAllVersions Vector3D vector;
    private @NotOnAllVersions PacketPlayInArmAnimation.Hand hand;

    private final PacketBuilder creator = PacketBuilder.get(getPacketType());

    public PacketPlayInUseEntity(PacketDataContainer packetData) {
        this.packetData = packetData;
        this.entityId = packetData.readInt(0);
        this.entityUseAction = EntityUseAction.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getEntityUseActionEnum()).ordinal());
        if(creator.getConstructorIndicator().getLevel() < 2) {
            this.vector = new Vector3D(packetData.readObject(0, ClassesContainer.INSTANCE.getVector3dClass()));
            if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9)) {
                this.hand = PacketPlayInArmAnimation.Hand.getById(packetData.readEnumConstant(0, (Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getHandEnum()).ordinal());
                if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
                    this.sneaking = packetData.readBoolean(0);
                }
            } else {
                this.hand = PacketPlayInArmAnimation.Hand.MAIN_HAND;
            }
        } else {
            this.vector = Vector3D.ZERO;
            this.hand = PacketPlayInArmAnimation.Hand.MAIN_HAND;
            this.sneaking = packetData.readBoolean(0);
        }
    }

    public PacketPlayInUseEntity(Entity entity, boolean sneaking, EntityUseAction entityUseAction, @NotOnAllVersions Vector3D vector, @NotOnAllVersions PacketPlayInArmAnimation.Hand hand) {
        switch (creator.getConstructorIndicator().getLevel()) {
            case 0: {
                IndexedParam<?,?>[] params;
                if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) {
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
                this.packetData = new PacketDataSerializer(creator.buildPacket(null, AlMinecraftReflection.getMinecraftEntity(entity)));
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

    public PacketPlayInUseEntity(int entityId, boolean sneaking, EntityUseAction entityUseAction, @NotOnAllVersions Vector3D vector, @NotOnAllVersions PacketPlayInArmAnimation.Hand hand) {
        this(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId), sneaking, entityUseAction, vector, hand);
    }

    public PacketPlayInUseEntity(int entityId, EntityUseAction entityUseAction, @NotOnAllVersions Vector3D vector) {
        this(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId), true, entityUseAction, vector, PacketPlayInArmAnimation.Hand.MAIN_HAND);
    }

    /*
     * 1.17 and bigger
     */
    public PacketPlayInUseEntity(int entityId, boolean sneaking, EntityUseAction entityUseAction) {
        this(GlobalProvider.instance().getData().getEntitiesContainer().getEntity(entityId), sneaking, entityUseAction, Vector3D.ZERO, PacketPlayInArmAnimation.Hand.MAIN_HAND);
    }

    @Nullable
    public Entity getEntity() {
        return GlobalProvider.instance().getData().getEntitiesContainer().getEntity(this.entityId);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.packetData.writeInt(0, entityId);
        this.entityId = entityId;
    }

    public void setEntityId(Entity entity) {
        this.packetData.writeInt(0, entity.getEntityId());
        this.entityId = entity.getEntityId();
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_16)) this.packetData.writeBoolean(0, sneaking);
        this.sneaking = sneaking;
    }

    public EntityUseAction getEntityUseAction() {
        return entityUseAction;
    }

    public void setEntityUseAction(EntityUseAction entityUseAction) {
        this.packetData.writeEnumConstant(0, entityUseAction.original());
        this.entityUseAction = entityUseAction;
    }

    @NotOnAllVersions
    public Vector3D getVector() {
        return vector;
    }

    public void setVector(@NotOnAllVersions Vector3D vector) {
        if(GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_17)) this.packetData.writeObject(0, vector.createOriginal());
        this.vector = vector;
    }

    @NotOnAllVersions
    public PacketPlayInArmAnimation.Hand getHand() {
        return hand;
    }

    public void setHand(@NotOnAllVersions PacketPlayInArmAnimation.Hand hand) {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_9) && GlobalProvider.instance().getServer().getVersion().lessThan(Version.v1_17)) this.packetData.writeEnumConstant(0, hand.original());
        this.hand = hand;
    }

    @Override
    public MinecraftPacketType getPacketType() {
        return PacketType.Play.Client.USE_ENTITY;
    }

    @Override
    public PacketDataContainer getPacketData() {
        return packetData;
    }

    @Override
    public Object getRawPacket() {
        return getPacketData().getRawPacket();
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

        public static EntityUseAction getById(int id) {
            for(EntityUseAction useAction : values()) {
                if(useAction.id == id) return useAction;
            }
            return null;
        }

        public Enum<?> original() {
            return Reflection.getEnumValue((Class<? extends Enum<?>>) ClassesContainer.INSTANCE.getEntityUseActionEnum(), this.id);
        }
    }

}
