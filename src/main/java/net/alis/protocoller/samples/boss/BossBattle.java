package net.alis.protocoller.samples.boss;

import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.util.CopiedObject;
import net.alis.protocoller.util.ObjectAccessor;

import java.util.UUID;

public abstract class BossBattle implements CopiedObject {
    private final UUID uuid;
    public ChatComponent title;
    protected float progress;
    public BarColor color;
    public BarStyle style;
    protected boolean darkenSky;
    protected boolean playMusic;
    protected boolean createFog;

    public BossBattle(UUID uuid, ChatComponent title, BarColor color, BarStyle style) {
        this.uuid = uuid;
        this.title = title;
        this.color = color;
        this.style = style;
        this.progress = 1.0F;
    }

    public BossBattle(Object original) {
        ObjectAccessor accessor = new ObjectAccessor(original);
        this.uuid = accessor.read(0, UUID.class);
        this.title = new ChatComponent(ChatSerializer.fromComponent(accessor.read(0, ClassesContainer.INSTANCE.getIChatBaseComponentClass())));
        this.progress = accessor.read(0, float.class);
        this.color = BarColor.getById(((Enum<?>)accessor.read(0, ClassesContainer.INSTANCE.getBarColorEnum())).ordinal());
        this.style = BarStyle.getById(((Enum<?>)accessor.read(0, ClassesContainer.INSTANCE.getBarStyleEnum())).ordinal());
        this.darkenSky = accessor.read(0, boolean.class);
        this.playMusic = accessor.read(1, boolean.class);
        this.createFog = accessor.read(2, boolean.class);
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
        return Reflection.callConstructor(
                Reflection.getConstructor(ClassesContainer.INSTANCE.getBossBattleClass(), UUID.class, ClassesContainer.INSTANCE.getIChatBaseComponentClass(), ClassesContainer.INSTANCE.getBarColorEnum(), ClassesContainer.INSTANCE.getBarStyleEnum()),
                this.uuid, this.title.asIChatBaseComponent(), this.color.original(), this.style.original()
        );
    }
}

