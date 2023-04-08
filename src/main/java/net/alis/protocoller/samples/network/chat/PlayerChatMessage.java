package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.providers.GlobalProvider;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.annotations.NotOnAllVersions;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerChatMessage implements ObjectSample {

    private @NotOnAllVersions SignedMessageLink link;
    private MessageSignature signature;
    private @NotOnAllVersions SignedMessageBody signedBody;
    private ChatComponent unsignedContent;
    private @NotOnAllVersions FilterMask filterMask;

    public PlayerChatMessage(Object playerChatMessage) {
        AccessedObject object = new AccessedObject(playerChatMessage);
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_1n2)) {
            this.link = new SignedMessageLink(object.read(0, ClassesContainer.get().getSignedMessageLinkClass()));
            this.signature = new MessageSignature((Object) object.read(0, ClassesContainer.get().getMessageSignatureClass()));
            this.signedBody = new SignedMessageBody(object.read(0, ClassesContainer.get().getSignedMessageBodyClass()));
            if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
                this.unsignedContent = new ChatComponent(ChatSerializer.fromComponent(object.read(0, ClassesContainer.get().getIChatBaseComponentClass())));
            } else {
                this.unsignedContent = new ChatComponent(ChatSerializer.fromComponent(((Optional<?>)object.read(0, Optional.class)).get()));
            }
            this.filterMask = new FilterMask(object.read(0, ClassesContainer.get().getFilterMaskClass()));
        }
    }

    public PlayerChatMessage(SignedMessageLink link, MessageSignature signature, SignedMessageBody signedBody, ChatComponent unsignedContent, FilterMask filterMask) {
        this.link = link;
        this.signature = signature;
        this.signedBody = signedBody;
        this.unsignedContent = unsignedContent;
        this.filterMask = filterMask;
    }

    @Override
    public Object createOriginal() {
        if(GlobalProvider.instance().getServer().getVersion().greaterThanOrEqualTo(Version.v1_19_3)) {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassesContainer.get().getPlayerChatMessageClass(), ClassesContainer.get().getSignedMessageLinkClass(), ClassesContainer.get().getMessageSignatureClass(), ClassesContainer.get().getSignedMessageBodyClass(), ClassesContainer.get().getIChatBaseComponentClass(), ClassesContainer.get().getFilterMaskClass()),
                        this.link.createOriginal(), this.signature.createOriginal(), this.signedBody.createOriginal(), this.unsignedContent.asIChatBaseComponent(), this.filterMask.createOriginal()
                    );
        } else {
            return Reflect.callConstructor(
                    Reflect.getConstructor(ClassesContainer.get().getPlayerChatMessageClass(), ClassesContainer.get().getSignedMessageLinkClass(), ClassesContainer.get().getMessageSignatureClass(), ClassesContainer.get().getSignedMessageBodyClass(), Optional.class, ClassesContainer.get().getFilterMaskClass()),
                    this.link.createOriginal(), this.signature.createOriginal(), this.signedBody.createOriginal(), Optional.of(this.unsignedContent.asIChatBaseComponent()), this.filterMask.createOriginal()
            );
        }
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
}
