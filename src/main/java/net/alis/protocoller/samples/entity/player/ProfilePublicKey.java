package net.alis.protocoller.samples.entity.player;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

public class ProfilePublicKey implements ObjectSample {

    private ProfilePublicKeyData data;

    public ProfilePublicKey(Object profilePublicKey) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.data = new ProfilePublicKeyData(new AccessedObject(profilePublicKey).readField(0, ProfilePublicKeyData.clazz()));
    }

    public ProfilePublicKey(ProfilePublicKeyData data) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.data = data;
    }

    public ProfilePublicKeyData getData() {
        return data;
    }

    public void setData(ProfilePublicKeyData data) {
        this.data = data;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ProfilePublicKeyData.clazz()),
                this.data.createOriginal()
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getProfilePublicKeyClass();
    }

}
