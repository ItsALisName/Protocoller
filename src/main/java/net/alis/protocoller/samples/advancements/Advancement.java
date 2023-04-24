package net.alis.protocoller.samples.advancements;

import com.google.common.collect.ImmutableMap;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.util.*;

public class Advancement implements ObjectSample {
    @Nullable
    private Advancement parent;
    @Nullable
    private AdvancementDisplay  display;
    private AdvancementRewards rewards;
    private MinecraftKey id;
    private Map<String, Criterion> criteria;
    private String[][] requirements;
    private Set<Advancement> children = new HashSet<>();
    private ChatComponent chatComponent;

    public Advancement(MinecraftKey id, @Nullable Advancement parent, @Nullable AdvancementDisplay  display, AdvancementRewards rewards, Map<String, Criterion> criteria, String[][] requirements) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.id = id;
        this.display = display;
        this.criteria = ImmutableMap.copyOf(criteria);
        this.parent = parent;
        this.rewards = rewards;
        this.requirements = requirements;
        if (parent != null) {
            parent.addChild(this);
        }
        if (display == null) {
            this.chatComponent = new ChatComponent(id.toString());
        }
    }

    public Advancement(Object advancement) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(advancement);
        Object keyId = object.readField(0, MinecraftKey.clazz());
        if(keyId != null) this.id = new MinecraftKey(keyId);
        Object parentAdv = object.readField(0, clazz());
        if(parentAdv != null) this.parent = new Advancement(parentAdv);
        Object advDisplay = object.readField(0, AdvancementDisplay.clazz());
        if(advDisplay != null) this.display = new AdvancementDisplay(advDisplay);
        Object advRewards = object.readField(0, AdvancementRewards.clazz());
        if(advRewards != null) this.rewards = new AdvancementRewards(advRewards);
        Map<String, Object> criterions = object.readField(0, Map.class);
        if(criterions != null && criterions.size() > 0) {
            this.criteria = new HashMap<>();
            for(Map.Entry<String, Object> c : criterions.entrySet()) {
                this.criteria.put(c.getKey(), new Criterion(c.getValue()));
            }
        }
        Object cComponent = object.readField(0, ChatComponent.clazz());
        if(cComponent != null) this.chatComponent = new ChatComponent(ChatSerializer.fromComponent(cComponent));
        Set<Object> chld = object.readField(0, Set.class);
        if(chld != null && chld.size() > 0) {
            for(Object adv : chld) this.children.add(new Advancement(adv));
        }
        this.requirements = object.readField(0, ClassAccessor.arrayOfClass(String[].class));
    }

    @Nullable
    public Advancement getParent() {
        return this.parent;
    }

    public Advancement getRoot() {
        return getRoot(this);
    }

    public static Advancement getRoot(Advancement from) {
        Advancement advancement = from;
        while (true) {
            Advancement advancement1 = advancement.getParent();
            if (advancement1 == null) {
                return advancement;
            }
            advancement = advancement1;
        }
    }

    public void setParent(@Nullable Advancement parent) {
        this.parent = parent;
    }

    public void setDisplay(@Nullable AdvancementDisplay display) {
        this.display = display;
    }

    public void setRewards(AdvancementRewards rewards) {
        this.rewards = rewards;
    }

    public void setId(MinecraftKey id) {
        this.id = id;
    }

    public void setCriteria(Map<String, Criterion> criteria) {
        this.criteria = criteria;
    }

    public void setRequirements(String[][] requirements) {
        this.requirements = requirements;
    }

    public void setChildren(Set<Advancement> children) {
        this.children = children;
    }

    public void setChatComponent(ChatComponent chatComponent) {
        this.chatComponent = chatComponent;
    }

    @Nullable
    public AdvancementDisplay getDisplay() {
        return this.display;
    }

    public AdvancementRewards getRewards() {
        return this.rewards;
    }

    public String toString() {
        return "SimpleAdvancement{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", rewards=" + this.rewards + ", criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + "}";
    }

    public Iterable<Advancement> getChildren() {
        return this.children;
    }

    public Map<String, Criterion> getCriteria() {
        return this.criteria;
    }

    public int getMaxCriteraRequired() {
        return this.requirements.length;
    }

    public void addChild(Advancement advancement) {
        this.children.add(advancement);
    }

    public MinecraftKey getId() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Advancement)) {
            return false;
        } else {
            Advancement advancement = (Advancement) obj;
            return this.id.equals(advancement.id);
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String[][] getRequirements() {
        return this.requirements;
    }

    public ChatComponent getChatComponent() {
        return this.chatComponent;
    }

    @Override
    public Object createOriginal() {
        Map<String, Object> crts = new HashMap<>();
        if(this.criteria != null){
            for (Map.Entry<String, Criterion> e : this.criteria.entrySet()) {
                crts.put(e.getKey(), e.getValue().createOriginal());
            }
        }
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, MinecraftKey.clazz(), Advancement.clazz(), AdvancementDisplay.clazz(), AdvancementRewards.clazz(), Map.class, ClassAccessor.arrayOfClass(String[].class)),
                this.id != null ? this.id.createOriginal() : null, this.parent != null ? this.parent.createOriginal() : null, this.display != null ? this.display.createOriginal() : null, crts, this.requirements
        );
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMinecraftAdvancementClass();
    }
}
