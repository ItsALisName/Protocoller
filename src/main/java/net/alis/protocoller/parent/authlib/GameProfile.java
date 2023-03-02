package net.alis.protocoller.parent.authlib;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.parent.authlib.properties.PropertyMap;
import net.alis.protocoller.util.ObjectAccessor;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class GameProfile {

    private final UUID id;
    private final String name;
    private PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public GameProfile(UUID id, String name) {
        if (id == null && StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        } else {
            this.id = id;
            this.name = name;
        }
    }

    public GameProfile(Object gameProfile) {
        ObjectAccessor oa = new ObjectAccessor(gameProfile);
        this.id = oa.read(0, UUID.class);
        this.name = oa.read(0, String.class);
        this.properties = new PropertyMap(oa.read(0, ClassesContainer.INSTANCE.getPropertyMapClass()));
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public PropertyMap getProperties() {
        return this.properties;
    }

    public boolean isComplete() {
        return this.id != null && StringUtils.isNotBlank(this.getName());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            GameProfile that = (GameProfile) o;
            if (this.id != null) {
                if (!this.id.equals(that.id)) {
                    return false;
                }
            } else if (that.id != null) {
                return false;
            }

            if (this.name != null) {
                return this.name.equals(that.name);
            } else return that.name == null;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", legacy=" + legacy +
                '}';
    }

    public boolean isLegacy() {
        return this.legacy;
    }

    public Object createOriginal() {
        return Reflection.callConstructor(
            Reflection.getConstructor(ClassesContainer.INSTANCE.getGameProfileClass(), UUID.class, String.class),
            this.id, this.name
        );
    }

}
