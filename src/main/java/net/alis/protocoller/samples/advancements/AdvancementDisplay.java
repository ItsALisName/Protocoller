package net.alis.protocoller.samples.advancements;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.MinecraftReflection;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class AdvancementDisplay implements ObjectSample {
    private ChatComponent title;
    private ChatComponent description;
    private ItemStack icon;
    @Nullable private MinecraftKey background;
    private AdvancementFrameType frame;
    private boolean showToast;
    private boolean announceChat;
    private boolean hidden;
    private float x;
    private float y;

    public AdvancementDisplay(Object display) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(display);
        Object titleC = object.readField(0, ChatComponent.clazz());
        Object descriptionC = object.readField(1, ChatComponent.clazz());
        Object backgroundKey = object.readField(0, MinecraftKey.clazz());
        if(titleC != null) this.title = new ChatComponent(ChatSerializer.fromComponent(titleC));
        if(descriptionC != null) this.description = new ChatComponent(ChatSerializer.fromComponent(descriptionC));
        if(backgroundKey != null) this.background = new MinecraftKey(backgroundKey);
        Object item = object.readField(0, ClassAccessor.get().getMinecraftItemStackClass());
        if(item != null) this.icon = MinecraftReflection.itemStackFromMinecraftItemStack(item);
        this.frame = AdvancementFrameType.getById(((Enum<?>)object.readField(0, AdvancementFrameType.clazz())).ordinal());
        this.showToast = object.readField(0, boolean.class);
        this.announceChat = object.readField(1, boolean.class);
        this.hidden = object.readField(2, boolean.class);
        this.x = object.readField(0, float.class);
        this.y = object.readField(1, float.class);
    }

    public AdvancementDisplay(ItemStack icon, ChatComponent title, ChatComponent description, @Nullable MinecraftKey background, AdvancementFrameType frame, boolean showToast, boolean announceChat, boolean hidden) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.background = background;
        this.frame = frame;
        this.showToast = showToast;
        this.announceChat = announceChat;
        this.hidden = hidden;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setTitle(ChatComponent title) {
        this.title = title;
    }

    public void setDescription(ChatComponent description) {
        this.description = description;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setBackground(@Nullable MinecraftKey background) {
        this.background = background;
    }

    public void setFrame(AdvancementFrameType frame) {
        this.frame = frame;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isAnnounceChat() {
        return announceChat;
    }

    public void setAnnounceChat(boolean announceChat) {
        this.announceChat = announceChat;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ChatComponent getTitle() {
        return this.title;
    }

    public ChatComponent getDescription() {
        return this.description;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    @Nullable
    public MinecraftKey getBackground() {
        return this.background;
    }

    public AdvancementFrameType getFrame() {
        return this.frame;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean shouldShowToast() {
        return this.showToast;
    }

    public boolean shouldAnnounceChat() {
        return this.announceChat;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, ClassAccessor.get().getMinecraftItemStackClass(), ChatComponent.clazz(), ChatComponent.clazz(), MinecraftKey.clazz(), AdvancementFrameType.clazz(), boolean.class, boolean.class, boolean.class),
                MinecraftReflection.getMinecraftItemStack(this.icon), this.title != null ? this.title.asIChatBaseComponent() : null, this.description != null ? this.description.asIChatBaseComponent() : null, this.background != null ? this.background.createOriginal() : null, this.frame.original(), this.showToast, this.announceChat, this.hidden
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getAdvancementDisplayClass();
    }
}