package net.alis.protocoller.samples;

import net.alis.protocoller.samples.network.chat.ChatComponent;

public class TitleData {

    private ChatComponent title;
    private ChatComponent subtitle;

    private int fadeIn = 20;
    private int stay = 60;
    private int fadeOut = 20;

    public TitleData() {
        this.title = new ChatComponent("");
        this.subtitle = new ChatComponent("");
    }

    public TitleData(ChatComponent title, ChatComponent subtitle) {
        this.title = title != null ? title : new ChatComponent("");
        this.subtitle = subtitle != null ? subtitle : new ChatComponent("");
    }

    public TitleData(ChatComponent title, ChatComponent subtitle, int fadeIn, int stay, int fadeOut) {
        this.title = title != null ? title : new ChatComponent("");
        this.subtitle = subtitle != null ? subtitle : new ChatComponent("");
        if(fadeIn > 0) this.fadeIn = fadeIn;
        if(stay > 0) this.stay = stay;
        if(fadeOut > 0) this.fadeOut = fadeOut;
    }

    public ChatComponent getTitle() {
        return title;
    }

    public TitleData setTitle(ChatComponent title) {
        this.title = title != null ? title : new ChatComponent("");
        return this;
    }

    public ChatComponent getSubtitle() {
        return subtitle;
    }

    public TitleData setSubtitle(ChatComponent subtitle) {
        this.subtitle = subtitle != null ? subtitle : new ChatComponent("");
        return this;
    }

    public int getFadeInTicks() {
        return fadeIn;
    }

    public TitleData setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn > 0 ? fadeIn : 20;
        return this;
    }

    public int getStayTicks() {
        return stay;
    }

    public TitleData setStay(int stay) {
        this.stay = stay > 0 ? stay : 60;
        return this;
    }

    public int getFadeOutTicks() {
        return fadeOut;
    }

    public TitleData setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut > 0 ? fadeOut : 20;
        return this;
    }
}
