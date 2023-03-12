package net.alis.protocoller.samples.authlib.properties;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

public class PropertyMapSerializer {

    public PropertyMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        PropertyMap result = new PropertyMap();
        if (json instanceof JsonObject) {
            JsonObject object = (JsonObject) json;
            Iterator<Map.Entry<String, JsonElement>> iterator = object.entrySet().iterator();

            while(true) {
                Map.Entry<?, ?> entry;
                do {
                    if (!iterator.hasNext()) {
                        return result;
                    }

                    entry = iterator.next();
                } while(!(entry.getValue() instanceof JsonArray));

                for (JsonElement element : (JsonArray) entry.getValue()) {
                    result.put((String) entry.getKey(), new Property((String) entry.getKey(), element.getAsString()));
                }
            }
        } else if (json instanceof JsonArray) {

            for (JsonElement element : (JsonArray) json) {
                if (element instanceof JsonObject) {
                    JsonObject object = (JsonObject) element;
                    String name = object.getAsJsonPrimitive("name").getAsString();
                    String value = object.getAsJsonPrimitive("value").getAsString();
                    if (object.has("signature")) {
                        result.put(name, new Property(name, value, object.getAsJsonPrimitive("signature").getAsString()));
                    } else {
                        result.put(name, new Property(name, value));
                    }
                }
            }
        }

        return result;
    }

    public JsonElement serialize(PropertyMap src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray result = new JsonArray();

        JsonObject object;
        for(Iterator<?> iterator = src.values().iterator(); iterator.hasNext(); result.add(object)) {
            Property property = (Property)iterator.next();
            object = new JsonObject();
            object.addProperty("name", property.getName());
            object.addProperty("value", property.getValue());
            if (property.hasSignature()) {
                object.addProperty("signature", property.getSignature());
            }
        }

        return result;
    }

}
