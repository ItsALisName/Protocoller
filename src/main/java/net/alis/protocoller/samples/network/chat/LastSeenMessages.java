package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.enums.Version;
import net.alis.protocoller.bukkit.providers.GlobalProvider;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.annotations.NotOnAllVersions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LastSeenMessages {

    private final @NotOnAllVersions List<MessageSignature> messageSignatureList; // On 1.19.3 and highest
    private final @NotOnAllVersions List<Cache> cacheList; // On 1.19.2 and lowest

    public LastSeenMessages(Object lastSeenMessages) {
        AccessedObject object = new AccessedObject(lastSeenMessages);
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.messageSignatureList = new ArrayList<>();
            this.cacheList = null;
            for(Object obj : (List<?>) object.read(0, List.class)) {
                this.messageSignatureList.add(new MessageSignature(obj));
            }
        } else {
            this.messageSignatureList = null;
            this.cacheList = new ArrayList<>();
            for(Object obj : (List<?>) object.read(0, List.class)) {
                this.cacheList.add(new Cache(obj));
            }
        }
    }

    public LastSeenMessages(List<?> list) {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
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

    public static class Cache implements ObjectSample {

        private UUID uuid;
        private MessageSignature signature;

        public Cache(Object cache) {
            AccessedObject object = new AccessedObject(cache);
            this.uuid = object.read(0, UUID.class);
            this.signature = new MessageSignature(object.read(0, ClassesContainer.INSTANCE.getMessageSignatureClass()));
        }

        public Cache(UUID uuid, MessageSignature signature) {
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
            return Reflection.callConstructor(
                    Reflection.getConstructor(ClassesContainer.INSTANCE.getLastSeenMessagesCacheClass(), UUID.class, ClassesContainer.INSTANCE.getMessageSignatureClass()),
                    this.uuid, this.signature.createOriginal()
            );
        }
    }

}
