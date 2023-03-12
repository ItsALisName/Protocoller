package net.alis.protocoller.samples.resources;

import com.google.gson.*;
import net.alis.protocoller.samples.util.ChatDeserializer;

import java.lang.reflect.Type;

public class MinecraftKeySerializer implements JsonDeserializer<MinecraftKey>, JsonSerializer<MinecraftKey> {

    @Override
    public MinecraftKey deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new MinecraftKey(ChatDeserializer.getFrom(jsonElement, "location"));
    }

    @Override
    public JsonElement serialize(MinecraftKey minecraftKey, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(minecraftKey.toString());
    }

}
