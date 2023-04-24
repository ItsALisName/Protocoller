package net.alis.protocoller.samples.boss;

import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.util.ObjectSample;
import net.alis.protocoller.util.AccessedObject;

import java.util.UUID;

public abstract class BossBattle implements ObjectSample {
    private final UUID uuid;
    public ChatComponent title;
    protected float progress;
    public BarColor color;
    public BarStyle style;
    protected boolean darkenSky;
    protected boolean playMusic;
    protected boolean createFog;

    public BossBattle(UUID uuid, ChatComponent title, BarColor color, BarStyle style) {
        Utils.checkClassSupportability(clazz(), "BossBattle", false);
        this.uuid = uuid;
        this.title = title;
        this.color = color;
        this.style = style;
        this.progress = 1.0F;
    }

    public BossBattle(Object original) {
        Utils.checkClassSupportability(clazz(), "BossBattle", false);
        AccessedObject accessor = new AccessedObject(original);
        this.uuid = accessor.readField(0, UUID.class);
        this.title = new ChatComponent(ChatSerializer.fromComponent(accessor.readField(0, ChatComponent.clazz())));
        this.progress = accessor.readField(0, float.class);
        this.color = BarColor.getById(((Enum<?>)accessor.readField(0, ClassAccessor.get().getBarColorEnum())).ordinal());
        this.style = BarStyle.getById(((Enum<?>)accessor.readField(0, ClassAccessor.get().getBarStyleEnum())).ordinal());
        this.darkenSky = accessor.readField(0, boolean.class);
        this.playMusic = accessor.readField(1, boolean.class);
        this.createFog = accessor.readField(2, boolean.class);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public ChatComponent getTitle() {
        return this.title;
    }

    public void setTitle(ChatComponent title) {
        this.title = title;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public BarColor getColor() {
        return this.color;
    }

    public void setColor(BarColor var0) {
        this.color = var0;
    }

    public BarStyle getStyle() {
        return this.style;
    }

    public void setStyle(BarStyle style) {
        this.style = style;
    }

    public boolean isDarkenSky() {
        return this.darkenSky;
    }

    public BossBattle setDarkenSky(boolean darkenSky) {
        this.darkenSky = darkenSky;
        return this;
    }

    public boolean isPlayMusic() {
        return this.playMusic;
    }

    public BossBattle setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
        return this;
    }

    public BossBattle setCreateFog(boolean createFog) {
        this.createFog = createFog;
        return this;
    }

    public boolean isCreateFog() {
        return this.createFog;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, UUID.class, ChatComponent.clazz(), BarColor.clazz(), BarStyle.clazz()),
                this.uuid, this.title.asIChatBaseComponent(), this.color.original(), this.style.original()
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getBossBattleClass();
    }
}

