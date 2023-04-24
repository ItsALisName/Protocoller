package net.alis.protocoller.samples.network.status;

import com.google.gson.*;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.util.ChatDeserializer;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ServerData {
    private String gameVersion;
    private int protocolVersion;

    public ServerData(String gameVersion, int protocolVersion) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        this.gameVersion = gameVersion;
        this.protocolVersion = protocolVersion;
    }

    public ServerData(Object serverData) {
        Utils.checkClassSupportability(clazz(), super.getClass().getSimpleName(), false);
        AccessedObject accessor = new AccessedObject(serverData);
        this.gameVersion = accessor.readField(0, String.class);
        this.protocolVersion = accessor.readField(0, int.class);
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
        return Reflect.callConstructor(Reflect.getConstructor(clazz(), false, String.class, int.class), this.gameVersion, this.protocolVersion);
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getServerDataClass();
    }

}
