package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerChatMessage implements ObjectSample {

    private @NotOnAllVersions SignedMessageHeader signedMessageHeader;
    private @NotOnAllVersions SignedMessageLink link;
    private MessageSignature signature;
    private @NotOnAllVersions SignedMessageBody signedBody;
    private ChatComponent unsignedContent;
    private @NotOnAllVersions FilterMask filterMask;

    public PlayerChatMessage(Object playerChatMessage) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(playerChatMessage);
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            this.link = new SignedMessageLink(object.readField(0, SignedMessageLink.clazz()));
            this.signature = new MessageSignature((Object) object.readField(0, MessageSignature.clazz()));
            this.signedBody = new SignedMessageBody(object.readField(0, SignedMessageBody.clazz()));
            this.unsignedContent = new ChatComponent(ChatSerializer.fromComponent(object.readField(0, ChatComponent.clazz())));
        } else {
            this.signedMessageHeader = new SignedMessageHeader(object.readField(0, SignedMessageHeader.clazz()));
            this.signature = new MessageSignature((Object) object.readField(0, MessageSignature.clazz()));
            this.signedBody = new SignedMessageBody(object.readField(0, SignedMessageBody.clazz()));
            Optional<Object> unsCont = object.readField(0, Optional.class);
            if(unsCont != null && unsCont.isPresent())
                this.unsignedContent = new ChatComponent(ChatSerializer.fromComponent(unsCont.get()));
        }
        this.filterMask = new FilterMask(object.readField(0, FilterMask.clazz()));
    }

    // For 1.19.3 and higher
    public PlayerChatMessage(SignedMessageLink link, MessageSignature signature, SignedMessageBody signedBody, ChatComponent unsignedContent, FilterMask filterMask) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.link = link;
        this.signature = signature;
        this.signedBody = signedBody;
        this.unsignedContent = unsignedContent;
        this.filterMask = filterMask;
    }

    public PlayerChatMessage(SignedMessageHeader signedMessageHeader, MessageSignature messageSignature, SignedMessageBody signedMessageBody, Optional<ChatComponent> unsignedContent, FilterMask filterMask) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.signedMessageHeader = signedMessageHeader;
        this.signature = messageSignature;
        this.signedBody = signedMessageBody;
        this.unsignedContent = unsignedContent.get();
        this.filterMask = filterMask;
    }

    @NotOnAllVersions
    public SignedMessageLink getLink() {
        return link;
    }

    public void setLink(@NotOnAllVersions SignedMessageLink link) {
        this.link = link;
    }

    public MessageSignature getSignature() {
        return signature;
    }

    public void setSignature(MessageSignature signature) {
        this.signature = signature;
    }

    @NotOnAllVersions
    public SignedMessageBody getSignedBody() {
        return signedBody;
    }

    public void setSignedBody(@NotOnAllVersions SignedMessageBody signedBody) {
        this.signedBody = signedBody;
    }

    public ChatComponent getUnsignedContent() {
        return unsignedContent;
    }

    public void setUnsignedContent(ChatComponent unsignedContent) {
        this.unsignedContent = unsignedContent;
    }

    @NotOnAllVersions
    public FilterMask getFilterMask() {
        return filterMask;
    }

    public void setFilterMask(@NotOnAllVersions FilterMask filterMask) {
        this.filterMask = filterMask;
    }

    public static PlayerChatMessage system(String content) {
        return unsigned(Utils.NIL_UUID, content);
    }

    public static @NotNull PlayerChatMessage unsigned(UUID sender, String content) {
        SignedMessageBody signedmessagebody = SignedMessageBody.unsigned(content);
        SignedMessageLink signedmessagelink = SignedMessageLink.unsigned(sender);
        return new PlayerChatMessage(signedmessagelink, null, signedmessagebody, null, FilterMask.PASS_THROUGH);
    }

    @Override
    public Object createOriginal() {
        if(IProtocolAccess.get().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, SignedMessageLink.clazz(), MessageSignature.clazz(), SignedMessageBody.clazz(), ChatComponent.clazz(), FilterMask.clazz()),
                    this.link.createOriginal(), this.signature.createOriginal(), this.signedBody.createOriginal(), this.unsignedContent.asIChatBaseComponent(), this.filterMask.createOriginal()
            );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, SignedMessageHeader.clazz(), MessageSignature.clazz(), SignedMessageBody.clazz(), Optional.class, FilterMask.clazz()),
                    this.signedMessageHeader.createOriginal(), this.signature.createOriginal(), this.signedBody.createOriginal(), Optional.of(this.unsignedContent.asIChatBaseComponent()), this.filterMask.createOriginal()
            );
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getPlayerChatMessageClass();
    }
}
