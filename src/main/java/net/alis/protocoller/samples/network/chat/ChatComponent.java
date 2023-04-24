package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.providers.IProtocolAccess;
import libraries.net.md_5.bungee.api.chat.BaseComponent;
import libraries.net.md_5.bungee.api.chat.ClickEvent;
import libraries.net.md_5.bungee.api.chat.HoverEvent;
import libraries.net.md_5.bungee.api.chat.TextComponent;
import libraries.net.md_5.bungee.api.chat.hover.content.Text;
import libraries.net.md_5.bungee.chat.ComponentSerializer;
import net.alis.protocoller.plugin.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatComponent {

    private final Pattern ampersandPattern = Pattern.compile("&[0-9-a-z]");
    private final Pattern paragraphPattern = Pattern.compile("§[0-9-a-z]");

    private final List<HoverEvent> hoverEvents = new ArrayList<>();
    private final List<ClickEvent> clickEvents = new ArrayList<>();

    private TextComponent component;
    private String string;

    public ChatComponent() {
        this.component = new TextComponent("");
    }

    public ChatComponent(String text) {
        if(text == null) {
            this.component = new TextComponent("");
            return;
        }
        this.component = new TextComponent(Utils.colors(text));
        this.string = component.getText();
    }

    public ChatComponent(TextComponent text) {
        this.component = text;
        this.string = text.getText();
    }

    public ChatComponent(BaseComponent text) {
        if (text == null) {
            this.component = new TextComponent("");
            return;
        }
        this.component = new TextComponent(text);
        this.string = text.toPlainText();
    }

    public ChatComponent(BaseComponent... texts) {
        if (texts == null) {
            this.component = new TextComponent("");
            return;
        }
        TextComponent components = new TextComponent("");
        for (BaseComponent c : texts) {
            components.addExtra(c);
        }
        this.string = components.toPlainText();
        this.component = components;
    }

    public ChatComponent(ChatComponent text) {
        if (text == null) {
            this.component = new TextComponent("");
            return;
        }
        this.string = text.string;
        this.component = text.component;
    }

    public ChatComponent(ChatComponent... texts) {
        if (texts == null) {
            this.component = new TextComponent("");
            return;
        }
        ChatComponent component = new ChatComponent("");
        for (ChatComponent c : texts) {
            component.append(c);
        }
        this.string = component.string;
        this.component = component.component;
    }

    public ChatComponent appendOnStart(String extra) {
        TextComponent n = new TextComponent(Utils.colors(extra));
        n.addExtra(component);
        this.string = extra + this.string;
        this.component = n;
        return this;
    }

    public ChatComponent setHoverEvent(HoverEvent.Action action, @NotNull String extra) {
        if (hasHoverEvents()) return this;
        HoverEvent event = new HoverEvent(action, new Text(Utils.colors(extra)));
        this.component.setHoverEvent(event);
        this.hoverEvents.add(event);
        return this;
    }

    public List<HoverEvent> getHoverEvents() {
        if (!hasHoverEvents()) return new ArrayList<>();
        return this.hoverEvents;
    }

    public List<ClickEvent> getClickEvents() {
        if (!hasClickEvents()) return new ArrayList<>();
        return this.clickEvents;
    }

    public ChatComponent setClickEvent(ClickEvent event) {
        this.component.setClickEvent(event);
        return this;
    }

    public ChatComponent setHoverEvent(HoverEvent event) {
        this.component.setHoverEvent(event);
        return this;
    }

    public ChatComponent duplicate() {
        return new ChatComponent(component);
    }

    public boolean hasHoverEvents() {
        return !this.hoverEvents.isEmpty();
    }

    public boolean hasClickEvents() {
        return !this.clickEvents.isEmpty();
    }

    public ChatComponent setClickEvent(ClickEvent.Action action, @NotNull String extra) {
        if (hasClickEvents()) return this;
        ClickEvent event = new ClickEvent(action, extra);
        this.component.setClickEvent(event);
        this.clickEvents.add(event);
        return this;
    }

    public ChatComponent append(@NotNull ChatComponent extra) {
        this.component.addExtra(extra.component);
        this.string = this.string + extra.string;
        return this;
    }

    public ChatComponent append(String delimiter, ChatComponent @NotNull ... extra) {
        ChatComponent component = new ChatComponent();
        StringBuilder builder = new StringBuilder(this.string);
        for(ChatComponent c : extra) {
            component.append(delimiter).append(c);
            builder.append(delimiter).append(Utils.colors(c.string));
        }
        this.component.addExtra(component.component);
        this.string = builder.toString();
        return this;
    }

    public ChatComponent append(String extra) {
        this.component.addExtra(Utils.colors(extra));
        this.string = this.string + extra;
        return this;
    }

    public ChatComponent newLine() {
        this.component.addExtra("\n");
        this.string = this.string + "\n";
        return this;
    }

    public ChatComponent append(String delimiter, String... extra) {
        ChatComponent component = new ChatComponent();
        StringBuilder builder = new StringBuilder(this.string);
        for(String s : extra) {
            component.append(delimiter).append(Utils.colors(s));
            builder.append(delimiter).append(Utils.colors(s));
        }
        this.string = builder.toString();
        this.component.addExtra(component.component);
        return this;
    }

    public ChatComponent append(TextComponent extra) {
        this.component.addExtra(extra);
        this.string = this.string + extra.toPlainText();
        return this;
    }

    public ChatComponent append(String delimiter, TextComponent... extra) {
        ChatComponent component1 = new ChatComponent();
        StringBuilder builder = new StringBuilder(this.string);
        for(TextComponent c : extra) {
            component1.append(delimiter).append(c);
            builder.append(delimiter).append(Utils.colors(c.toPlainText()));
        }
        this.string = builder.toString();
        this.component.addExtra(component1.component);
        return this;
    }

    public ChatComponent appendOnStart(TextComponent extra) {
        extra.addExtra(component);
        this.component = extra;
        this.string = extra.toPlainText();
        return this;
    }

    public ChatComponent appendOnStart(ChatComponent extra) {
        this.component = extra.append(component).get();
        return this;
    }

    @Deprecated
    private ChatComponent translateDefaultColorCodes() {
        if(getString().contains("&") || getString().contains("§")) this.component.setText(pasteColorCodes(getString()));
        return this;
    }

    public TextComponent get() {
        return this.component;
    }

    public String getString() {
        return this.string;
    }

    private String pasteColorCodes(String param) {
        boolean is = false; String code = "";
        StringBuilder old = new StringBuilder(param);
        StringBuilder newBuilder = new StringBuilder();
        for (int i = 0; i < param.length(); i++) {
            if (i + 1 < param.length() && this.paragraphPattern.matcher(Character.toString(old.charAt(i)) + Character.toString(old.charAt(i + 1))).find()) {
                code = Character.toString(old.charAt(i)) + Character.toString(old.charAt(i + 1));
                is = true; continue;
            }
            if (i + 1 < param.length() && this.ampersandPattern.matcher(Character.toString(old.charAt(i)) + Character.toString(old.charAt(i + 1))).find()) {
                code = Character.toString(old.charAt(i)) + Character.toString(old.charAt(i + 1));
                is = true; continue;
            }
            if(is) {is = false; continue;}
            newBuilder.append(code + Character.toString(old.charAt(i)));
        }
        return Utils.colors(newBuilder.toString());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ChatComponent && ((ChatComponent) o).component.equals(component);
    }

    public Object asIChatBaseComponent() {
        Object output = ChatSerializer.fromJSON0(ComponentSerializer.toString(get()));
        /*output = ChatSerializer.fromJSONOrString(ComponentSerializer.toString(get()), true, true);
        if(IProtocolAccess.get().getServer().getVersion().lessThan(Version.v1_16_4n5)) {
            output = ChatSerializer.fromJSON0(ComponentSerializer.toString(get()));
        } else {
            output = ChatSerializer.fromJSONOrString(ComponentSerializer.toString(get()), true, true);
        }*/
        if(output.getClass() == ClassAccessor.arrayOfClass(clazz())) return ((Object[])output)[0];
        return output;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getIChatBaseComponentClass();
    }

}

