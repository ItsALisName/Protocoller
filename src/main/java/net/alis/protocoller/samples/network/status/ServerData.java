package net.alis.protocoller.samples.network.status;

import com.google.gson.*;
import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.BaseReflection;
import net.alis.protocoller.samples.util.ChatDeserializer;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ServerData {
    private String gameVersion;
    private int protocolVersion;

    public ServerData(String gameVersion, int protocolVersion) {
        this.gameVersion = gameVersion;
        this.protocolVersion = protocolVersion;
    }

    public ServerData(Object serverData) {
        AccessedObject accessor = new AccessedObject(serverData);
        this.gameVersion = accessor.read(0, String.class);
        this.protocolVersion = accessor.read(0, int.class);
    }

    public String getGameVersion() {
        return this.gameVersion;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public static class Serializer implements JsonDeserializer<ServerData>, JsonSerializer<ServerData> {
        public Serializer() { }

        @Override
        public ServerData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = ChatDeserializer.elementAsJsonObject(jsonElement, "version");
            return new ServerData(ChatDeserializer.getFrom(jsonObject, "name"), ChatDeserializer.intFromJson(jsonObject, "protocol"));
        }

        @Override
        public JsonElement serialize(@NotNull ServerData version, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", version.getGameVersion());
            jsonObject.addProperty("protocol", version.getProtocolVersion());
            return jsonObject;
        }
    }

    public Object createOriginal() {
        return BaseReflection.callConstructor(
                BaseReflection.getConstructor(ClassesContainer.get().getServerDataClass(), String.class, int.class),
                this.gameVersion, this.protocolVersion);
    }
}
