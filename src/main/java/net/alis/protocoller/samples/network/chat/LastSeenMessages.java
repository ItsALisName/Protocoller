package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

import java.util.*;

public class LastSeenMessages implements ObjectSample {

    private final @NotOnAllVersions List<MessageSignature> messageSignatureList; // On 1.19.3 and highest
    private final @NotOnAllVersions List<Cache> cacheList; // On 1.19.2 and lowest

    public LastSeenMessages(Object lastSeenMessages) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(lastSeenMessages);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.messageSignatureList = new ArrayList<>();
            this.cacheList = null;
            for(Object obj : (List<?>) object.readField(0, List.class)) {
                this.messageSignatureList.add(new MessageSignature(obj));
            }
        } else {
            this.messageSignatureList = null;
            this.cacheList = new ArrayList<>();
            for(Object obj : (List<?>) object.readField(0, List.class)) {
                this.cacheList.add(new Cache(obj));
            }
        }
    }

    public LastSeenMessages(List<?> list) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.messageSignatureList = (List<MessageSignature>) list;
            this.cacheList = null;
        } else {
            this.messageSignatureList = null;
            this.cacheList = (List<Cache>) list;
        }
    }

    @NotOnAllVersions
    public List<Cache> getCacheList() {
        return cacheList;
    }

    @NotOnAllVersions
    public List<MessageSignature> getMessageSignatureList() {
        return messageSignatureList;
    }

    @Override
    public Object createOriginal() {
        List<Object> list = new ArrayList<>();
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            for(MessageSignature s : this.messageSignatureList) list.add(s.createOriginal());
        } else {
            for(Cache cache : this.cacheList) list.add(cache.createOriginal());
        }
        return Reflect.callConstructor(Reflect.getConstructor(clazz(), false, List.class), list);
    }
    
    public static Class<?> clazz() {
        return ClassAccessor.get().getLastSeenMessagesClass();
    }

    public static class Cache implements ObjectSample {

        private UUID uuid;
        private MessageSignature signature;

        public Cache(Object cache) {
            Utils.checkClassSupportability(Cache.clazz(), super.getClass().getSimpleName(), false);
            AccessedObject object = new AccessedObject(cache);
            this.uuid = object.readField(0, UUID.class);
            this.signature = new MessageSignature(object.readField(0, MessageSignature.clazz()));
        }

        public Cache(UUID uuid, MessageSignature signature) {
            Utils.checkClassSupportability(Cache.clazz(), super.getClass().getSimpleName(), false);
            this.uuid = uuid;
            this.signature = signature;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public MessageSignature getSignature() {
            return signature;
        }

        public void setSignature(MessageSignature signature) {
            this.signature = signature;
        }

        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(Cache.clazz(), false, UUID.class, MessageSignature.clazz()),
                    this.uuid, this.signature.createOriginal()
            );
        }
        
        public static Class<?> clazz() {
            return ClassAccessor.get().getLastSeenMessagesCacheClass();
        }
    }

    public static class Updater implements ObjectSample {
        private @NotOnAllVersions LastSeenMessages lastSeenMessages; // On 1.19.2 and lower
        private @NotOnAllVersions Optional<Updater> updater; // On 1.19.2 and lower

        private @NotOnAllVersions int i; // On 1.19.3 and highest
        private @NotOnAllVersions BitSet bitSet; // On 1.19.3 and highest

        public Updater(Object updater) {
            Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
            AccessedObject object = new AccessedObject(updater);
            if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
                this.i = object.readField(0, Integer.TYPE);
                this.bitSet = object.readField(0, BitSet.class);
            } else {
                this.lastSeenMessages = new LastSeenMessages((Object) object.readField(0, LastSeenMessages.clazz()));
                this.updater = Optional.of(new Updater(object.readField(0, Updater.clazz())));
            }
        }

        //For 1.19.2 and lower

        public Updater(LastSeenMessages lastSeenMessages, Optional<Updater> updater) {
            Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
            this.lastSeenMessages = lastSeenMessages;
            this.updater = updater;
        }
        //For 1.19.3 and highest

        public Updater(int i, @NotOnAllVersions BitSet bitSet) {
            Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
            this.i = i;
            this.bitSet = bitSet;
        }
        @NotOnAllVersions
        public LastSeenMessages getLastSeenMessages() {
            return lastSeenMessages;
        }

        @NotOnAllVersions
        public Optional<Updater> getUpdater() {
            return updater;
        }

        public int getInt() {
            return i;
        }

        @NotOnAllVersions
        public BitSet getBitSet() {
            return bitSet;
        }

        public void setLastSeenMessages(@NotOnAllVersions LastSeenMessages lastSeenMessages) {
            this.lastSeenMessages = lastSeenMessages;
        }

        public void setUpdater(@NotOnAllVersions Optional<Updater> updater) {
            this.updater = updater;
        }

        public void setInt(int i) {
            this.i = i;
        }

        public void setBitSet(@NotOnAllVersions BitSet bitSet) {
            this.bitSet = bitSet;
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getLastSeenMessagesUpdaterClass();
        }

        @Override
        public Object createOriginal() {
            Object response = null;
            if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
                response = Reflect.callConstructor(Reflect.getConstructor(Updater.clazz(), false, Integer.TYPE, BitSet.class), this.i, this.bitSet);
            } else {
                response = Reflect.callConstructor(Reflect.getConstructor(Updater.clazz(), false, LastSeenMessages.clazz(), Optional.class), this.lastSeenMessages, Optional.of(this.updater.get().createOriginal()));
            }
            return response;
        }

    }
}
