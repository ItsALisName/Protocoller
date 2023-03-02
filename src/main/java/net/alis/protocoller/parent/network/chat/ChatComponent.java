package net.alis.protocoller.parent.network.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static net.alis.protocoller.bukkit.util.Utils.setColors;

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
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] String cannot be null"));
            return;
        }
        this.component = new TextComponent(setColors(text));
        this.string = component.getText();
    }

    public ChatComponent(TextComponent text) {
        this.component = text;
        this.string = text.getText();
    }

    public ChatComponent(BaseComponent text) {
        if (text == null) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] BaseComponent cannot be null"));
            return;
        }
        this.component = new TextComponent(text);
        this.string = text.toPlainText();
    }

    public ChatComponent(BaseComponent... texts) {
        if (texts == null) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] BaseComponents cannot be null"));
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
            throw new IllegalArgumentException("[FunctionalServerControl] ChatComponent cannot be null");
        }
        this.string = text.string;
        this.component = text.component;
    }

    public ChatComponent(ChatComponent... texts) {
        if (texts == null) {
            throw new IllegalArgumentException("[FunctionalServerControl] ChatComponents cannot be null");
        }
        ChatComponent component = new ChatComponent("");
        for (ChatComponent c : texts) {
            component.append(c);
        }
        this.string = component.string;
        this.component = component.component;
    }

    public ChatComponent appendOnStart(String extra) {
        TextComponent n = new TextComponent(setColors(extra));
        n.addExtra(component);
        this.string = extra + this.string;
        this.component = n;
        return this;
    }

    public ChatComponent setHoverEvent(HoverEvent.Action action, @NotNull String extra) {
        if (hasHoverEvents()) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] Component already has hover event"));
            return this;
        }
        HoverEvent event = new HoverEvent(action, new Text(setColors(extra)));
        this.component.setHoverEvent(event);
        this.hoverEvents.add(event);
        return this;
    }

    public List<HoverEvent> getHoverEvents() {
        if (!hasHoverEvents()) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] Component has no hover events"));
            return new ArrayList<HoverEvent>();
        }
        return this.hoverEvents;
    }

    public List<ClickEvent> getClickEvents() {
        if (!hasClickEvents()) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] Component has no click events"));
            return new ArrayList<ClickEvent>();
        }
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
        if (hasClickEvents()) {
            Bukkit.getConsoleSender().sendMessage(setColors("&c[FunctionalServerControl] Component already has click event"));
            return this;
        }
        ClickEvent event = new ClickEvent(action, extra);
        this.component.setClickEvent(event);
        this.clickEvents.add(event);
        return this;
    }

    public ChatComponent append(ChatComponent extra) {
        this.component.addExtra(extra.component);
        this.string = this.string + extra.string;
        return this;
    }

    public ChatComponent append(String delimiter, ChatComponent... extra) {
        ChatComponent component = new ChatComponent();
        StringBuilder builder = new StringBuilder(this.string);
        for(ChatComponent c : extra) {
            component.append(delimiter).append(c);
            builder.append(delimiter).append(c.string);
        }
        this.component.addExtra(component.component);
        this.string = builder.toString();
        return this;
    }

    public ChatComponent append(String extra) {
        this.component.addExtra(extra);
        this.string = this.string + extra;
        return this;
    }

    public ChatComponent append(String delimiter, String... extra) {
        ChatComponent component = new ChatComponent();
        StringBuilder builder = new StringBuilder(this.string);
        for(String s : extra) {
            component.append(delimiter).append(s);
            builder.append(delimiter).append(s);
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
            builder.append(delimiter).append(c.toPlainText());
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

    public ChatComponent translateDefaultColorCodes() {
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
        return newBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ChatComponent && ((ChatComponent) o).component.equals(component);
    }

    public Object createIChatBaseComponent() {
        return ChatSerializer.fromJSONOrString(ComponentSerializer.toString(get()), true, true);
    }

}

