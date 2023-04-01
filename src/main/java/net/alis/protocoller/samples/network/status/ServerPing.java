package net.alis.protocoller.samples.network.status;

import com.google.gson.*;
import net.alis.protocoller.bukkit.data.ClassesContainer;
import net.alis.protocoller.bukkit.util.reflection.Reflection;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.network.chat.ChatSerializer;
import net.alis.protocoller.samples.util.ChatDeserializer;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public class ServerPing {
    @Nullable
    private ChatComponent description;
    @Nullable
    private ServerPingPlayerSample players;
    @Nullable
    private ServerData version;
    @Nullable
    private String favicon;
    private boolean secureChatEnforced;

    public ServerPing() { }
    
    public ServerPing(Object original) {
        AccessedObject accessor = new AccessedObject(original);
        this.description = new ChatComponent(ChatSerializer.fromComponent(accessor.read(0, ClassesContainer.INSTANCE.getIChatBaseComponentClass())));
        this.players = new ServerPingPlayerSample(accessor.read(0, ClassesContainer.INSTANCE.getServerPingPlayerSampleClass()));
        this.version = new ServerData(accessor.read(0, ClassesContainer.INSTANCE.getServerDataClass()));
        this.favicon = accessor.read(0, String.class);
    }

    @Nullable
    public ChatComponent getDescription() {
        return description;
    }

    public void setDescription(@Nullable ChatComponent description) {
        this.description = description;
    }

    @Nullable
    public ServerPingPlayerSample getPlayers() {
        return players;
    }

    public void setPlayers(@Nullable ServerPingPlayerSample players) {
        this.players = players;
    }

    @Nullable
    public ServerData getVersion() {
        return version;
    }

    public void setVersion(@Nullable ServerData version) {
        this.version = version;
    }

    @Nullable
    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(@Nullable String favicon) {
        this.favicon = favicon;
    }

    public boolean isSecureChatEnforced() {
        return secureChatEnforced;
    }

    public void setSecureChatEnforced(boolean secureChatEnforced) {
        this.secureChatEnforced = secureChatEnforced;
    }

    public static class Serializer implements JsonDeserializer<ServerPing>, JsonSerializer<ServerPing> {
        public Serializer() {
        }

        @Override
        public ServerPing deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = ChatDeserializer.elementAsJsonObject(jsonElement, "status");
            ServerPing serverStatus = new ServerPing();
            if (jsonObject.has("description")) {
                serverStatus.setDescription(new ChatComponent(ChatSerializer.fromComponent(jsonDeserializationContext.deserialize(jsonObject.get("description"), ClassesContainer.INSTANCE.getIChatBaseComponentClass()))));
            }

            if (jsonObject.has("players")) {
                serverStatus.setPlayers((ServerPingPlayerSample)jsonDeserializationContext.deserialize(jsonObject.get("players"), ServerPingPlayerSample.class));
            }

            if (jsonObject.has("version")) {
                serverStatus.setVersion((ServerData)jsonDeserializationContext.deserialize(jsonObject.get("version"), ServerData.class));
            }

            if (jsonObject.has("favicon")) {
                serverStatus.setFavicon(ChatDeserializer.getFrom(jsonObject, "favicon"));
            }

            if (jsonObject.has("enforcesSecureChat")) {
                serverStatus.setSecureChatEnforced(ChatDeserializer.containsString(jsonObject, "enforcesSecureChat"));
            }

            return serverStatus;
        }

        @Override
        public JsonElement serialize(@NotNull ServerPing serverStatus, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("enforcesSecureChat", serverStatus.secureChatEnforced);
            if (serverStatus.description != null) {
                jsonObject.add("description", jsonSerializationContext.serialize(serverStatus.description));
            }

            if (serverStatus.players != null) {
                jsonObject.add("players", jsonSerializationContext.serialize(serverStatus.players));
            }

            if (serverStatus.version != null) {
                jsonObject.add("version", jsonSerializationContext.serialize(serverStatus.version));
            }

            if (serverStatus.favicon != null) {
                jsonObject.addProperty("favicon", serverStatus.favicon);
            }

            return jsonObject;
        }
    }

    @Override
    public String toString() {
        return "ServerPing{" +
                "description=" + description +
                ", players=" + players +
                ", version=" + version +
                ", favicon='" + favicon + '\'' +
                ", secureChatEnforced=" + secureChatEnforced +
                '}';
    }

    public Object createOriginal() {
        AccessedObject accessor = new AccessedObject(Reflection.callConstructor(Reflection.getConstructor(ClassesContainer.INSTANCE.getServerPingClass())));
        if(this.favicon != null) accessor.write(0, this.favicon);
        if(this.description != null) accessor.writeSpecify(0, ClassesContainer.INSTANCE.getIChatBaseComponentClass(), this.description.asIChatBaseComponent());
        if(this.version != null) accessor.write(0, this.version.createOriginal());
        if(this.players != null) accessor.write(0, this.players.createOriginal());
        return accessor.getObject();
    }
}
