package net.alis.protocoller.samples.authlib;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.samples.authlib.properties.PropertyMap;
import net.alis.protocoller.util.AccessedObject;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class GameProfile {

    private UUID id;
    private String name;
    private PropertyMap properties = new PropertyMap();
    private boolean legacy;

    public GameProfile(UUID id, String name) {
        if (id == null && StringUtils.isBlank(name)) {
            ExceptionBuilder.throwException(new IllegalArgumentException("Name and ID cannot both be blank"), true);
        } else {
            this.id = id;
            this.name = name;
        }
    }

    public GameProfile(Object gameProfile) {
        AccessedObject oa = new AccessedObject(gameProfile);
        this.id = oa.read(0, UUID.class);
        this.name = oa.read(0, String.class);
        this.properties = new PropertyMap(oa.read(0, ClassesContainer.get().getPropertyMapClass()));
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
        return BaseReflection.callConstructor(
            BaseReflection.getConstructor(ClassesContainer.get().getGameProfileClass(), UUID.class, String.class),
            this.id, this.name
        );
    }

}
