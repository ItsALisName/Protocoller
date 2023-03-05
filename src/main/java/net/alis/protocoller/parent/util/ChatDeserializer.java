package net.alis.protocoller.parent.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class ChatDeserializer {

    private static final Gson gson = (new GsonBuilder()).create();

    public ChatDeserializer() { }

    public static boolean isJsonPrimitiveString(JsonObject object, String element) {
        return isJsonPrimitive(object, element) && object.getAsJsonPrimitive(element).isString();
    }

    public static int intFromObject(JsonObject object, String element, int defaultInt) {
        return object.has(element) ? intFromElement(object.get(element), element) : defaultInt;
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonArray arrayFromObject(JsonObject object, String name, @Nullable JsonArray defaultArray) {
        return object.has(name) ? jsonArrayFromElement(object.get(name), name) : defaultArray;
    }

    public static boolean isJsonPrimitiveString(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    public static boolean isJsonPrimitiveNumber(JsonObject object, String element) {
        return isJsonPrimitive(object, element) && object.getAsJsonPrimitive(element).isNumber();
    }

    public static boolean isJsonPrimitiveNumber(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    public static boolean isJsonPrimitiveBoolean(JsonObject object, String element) {
        return isJsonPrimitive(object, element) && object.getAsJsonPrimitive(element).isBoolean();
    }

    public static boolean isJsonPrimitiveBoolean(JsonElement object) {
        return object.isJsonPrimitive() && object.getAsJsonPrimitive().isBoolean();
    }

    public static boolean isJsonArray(JsonObject object, String element) {
        return containsString(object, element) && object.get(element).isJsonArray();
    }

    public static boolean isJsonObject(JsonObject object, String element) {
        return containsString(object, element) && object.get(element).isJsonObject();
    }

    public static boolean isJsonPrimitive(JsonObject object, String element) {
        return containsString(object, element) && object.get(element).isJsonPrimitive();
    }

    public static boolean containsString(JsonObject object, String element) {
        if (object == null) {
            return false;
        } else {
            return object.get(element) != null;
        }
    }

    public static int intFromElement(JsonElement element, String name) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be Skip$1 Int, was " + elementToString(element));
        }
    }

    public static String getFrom(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be Skip$1 string, was " + isJsonPrimitiveNumber(element));
        }
    }

    public static JsonArray jsonArrayFromObject(JsonObject object, String element) {
        if (object.has(element)) {
            return jsonArrayFromElement(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find Skip$1 JsonArray");
        }
    }

    public static JsonArray jsonArrayFromElement(JsonElement element, String name) {
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be Skip$1 JsonArray, was " + elementToString(element));
        }
    }

    public static String toDefaultString(JsonElement json) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);

        try {
            writeString(jsonWriter, json, Comparator.naturalOrder());
        } catch (IOException var4) {
            throw new AssertionError(var4);
        }

        return stringWriter.toString();
    }

    public static JsonObject elementAsJsonObject(JsonElement element, String name) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be Skip$1 JsonObject, was " + elementToString(element));
        }
    }

    public static int intFromJson(JsonObject object, String element) {
        if (object.has(element)) {
            return intFromElement(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find Skip$1 Int");
        }
    }

    public static String elementToString(@Nullable JsonElement element) {
        String string = StringUtils.abbreviateMiddle(String.valueOf(element), "...", 10);
        if (element == null) {
            return "null (missing)";
        } else if (element.isJsonNull()) {
            return "null (json)";
        } else if (element.isJsonArray()) {
            return "an array (" + string + ")";
        } else if (element.isJsonObject()) {
            return "an object (" + string + ")";
        } else {
            if (element.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    return "Skip$1 number (" + string + ")";
                }

                if (jsonPrimitive.isBoolean()) {
                    return "Skip$1 boolean (" + string + ")";
                }
            }

            return string;
        }
    }

    public static String getFrom(JsonObject object, String element) {
        if (object.has(element)) {
            return getFrom(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find Skip$1 string");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static String getFromOr(JsonObject object, String element, @Nullable String defaultStr) {
        return object.has(element) ? getFrom(object.get(element), element) : defaultStr;
    }

    @Nullable
    public static <T> T createIChatBaseComponent(Gson gson, String content, Class<T> type, boolean lenient) {
        return readGson(gson, (Reader)(new StringReader(content)), type, lenient);
    }

    @Nullable
    public static <T> T readGson(Gson gson, Reader reader, Class<T> type, boolean lenient) {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(lenient);
            return gson.getAdapter(type).read(jsonReader);
        } catch (IOException var5) {
            throw new JsonParseException(var5);
        }
    }

    public static void writeString(JsonWriter writer, @Nullable JsonElement json, @Nullable Comparator<String> comparator) throws IOException {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
                if (jsonPrimitive.isNumber()) {
                    writer.value(jsonPrimitive.getAsNumber());
                } else if (jsonPrimitive.isBoolean()) {
                    writer.value(jsonPrimitive.getAsBoolean());
                } else {
                    writer.value(jsonPrimitive.getAsString());
                }
            } else {
                Iterator<JsonElement> jsonElementIterator;
                Iterator<Map.Entry<String, JsonElement>> entryIterator;
                if (json.isJsonArray()) {
                    writer.beginArray();
                    jsonElementIterator = json.getAsJsonArray().iterator();

                    while(jsonElementIterator.hasNext()) {
                        JsonElement jsonElement = jsonElementIterator.next();
                        writeString(writer, jsonElement, comparator);
                    }

                    writer.endArray();
                } else {
                    if (!json.isJsonObject()) {
                        throw new IllegalArgumentException("Couldn't write " + json.getClass());
                    }

                    writer.beginObject();
                    entryIterator = compareElements(json.getAsJsonObject().entrySet(), comparator).iterator();

                    while(entryIterator.hasNext()) {
                        Map.Entry<String, JsonElement> entry = entryIterator.next();
                        writer.name(entry.getKey());
                        writeString(writer, entry.getValue(), comparator);
                    }

                    writer.endObject();
                }
            }
        } else {
            writer.nullValue();
        }

    }

    private static Collection<Map.Entry<String, JsonElement>> compareElements(Collection<Map.Entry<String, JsonElement>> entries, @Nullable Comparator<String> comparator) {
        if (comparator == null) {
            return entries;
        } else {
            List<Map.Entry<String, JsonElement>> list = new ArrayList<>(entries);
            list.sort(Map.Entry.comparingByKey(comparator));
            return list;
        }
    }

}
