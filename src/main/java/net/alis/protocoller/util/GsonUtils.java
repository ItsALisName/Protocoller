package net.alis.protocoller.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class GsonUtils {
    private static final Gson GSON = (new GsonBuilder()).create();

    public static boolean isStringValue(JsonObject object, String s) {
        return !isValidPrimitive(object, s) ? false : object.getAsJsonPrimitive(s).isString();
    }

    public static boolean isStringValue(JsonElement element) {
        return !element.isJsonPrimitive() ? false : element.getAsJsonPrimitive().isString();
    }

    public static boolean isNumberValue(JsonObject object, String s) {
        return !isValidPrimitive(object, s) ? false : object.getAsJsonPrimitive(s).isNumber();
    }

    public static boolean isNumberValue(JsonElement element) {
        return !element.isJsonPrimitive() ? false : element.getAsJsonPrimitive().isNumber();
    }

    public static boolean isBooleanValue(JsonObject object, String s) {
        return !isValidPrimitive(object, s) ? false : object.getAsJsonPrimitive(s).isBoolean();
    }

    public static boolean isBooleanValue(JsonElement element) {
        return !element.isJsonPrimitive() ? false : element.getAsJsonPrimitive().isBoolean();
    }

    public static boolean isArrayNode(JsonObject object, String s) {
        return !isValidNode(object, s) ? false : object.get(s).isJsonArray();
    }

    public static boolean isObjectNode(JsonObject object, String s) {
        return !isValidNode(object, s) ? false : object.get(s).isJsonObject();
    }

    public static boolean isValidPrimitive(JsonObject object, String s) {
        return !isValidNode(object, s) ? false : object.get(s).isJsonPrimitive();
    }

    public static boolean isValidNode(JsonObject object, String s) {
        if (object == null) {
            return false;
        } else {
            return object.get(s) != null;
        }
    }

    public static String convertToString(JsonElement element, String s) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a string, was " + getType(element));
        }
    }

    public static String getAsString(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToString(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a string");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static String getAsString(JsonObject object, String s, @Nullable String s1) {
        return object.has(s) ? convertToString(object.get(s), s) : s1;
    }

    public static boolean convertToBoolean(JsonElement element, String s) {
        if (element.isJsonPrimitive()) {
            return element.getAsBoolean();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Boolean, was " + getType(element));
        }
    }

    public static boolean getAsBoolean(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToBoolean(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Boolean");
        }
    }

    public static boolean getAsBoolean(JsonObject object, String s, boolean b) {
        return object.has(s) ? convertToBoolean(object.get(s), s) : b;
    }

    public static double convertToDouble(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsDouble();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Double, was " + getType(element));
        }
    }

    public static double getAsDouble(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToDouble(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Double");
        }
    }

    public static double getAsDouble(JsonObject object, String s, double d) {
        return object.has(s) ? convertToDouble(object.get(s), s) : d;
    }

    public static float convertToFloat(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsFloat();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Float, was " + getType(element));
        }
    }

    public static float getAsFloat(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToFloat(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Float");
        }
    }

    public static float getAsFloat(JsonObject jsonObject, String s, float f) {
        return jsonObject.has(s) ? convertToFloat(jsonObject.get(s), s) : f;
    }

    public static long convertToLong(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsLong();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Long, was " + getType(element));
        }
    }

    public static long getAsLong(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToLong(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Long");
        }
    }

    public static long getAsLong(JsonObject object, String s, long l) {
        return object.has(s) ? convertToLong(object.get(s), s) : l;
    }

    public static int convertToInt(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Int, was " + getType(element));
        }
    }

    public static int getAsInt(JsonObject jsonObject, String s) {
        if (jsonObject.has(s)) {
            return convertToInt(jsonObject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Int");
        }
    }

    public static int getAsInt(JsonObject jsonObject, String s, int i) {
        return jsonObject.has(s) ? convertToInt(jsonObject.get(s), s) : i;
    }

    public static byte convertToByte(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsByte();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Byte, was " + getType(element));
        }
    }

    public static byte getAsByte(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToByte(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Byte");
        }
    }

    public static byte getAsByte(JsonObject object, String s, byte b) {
        return object.has(s) ? convertToByte(object.get(s), s) : b;
    }

    public static char convertToCharacter(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsCharacter();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Character, was " + getType(element));
        }
    }

    public static char getAsCharacter(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToCharacter(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Character");
        }
    }

    public static char getAsCharacter(JsonObject object, String s, char ch) {
        return object.has(s) ? convertToCharacter(object.get(s), s) : ch;
    }

    public static BigDecimal convertToBigDecimal(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsBigDecimal();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a BigDecimal, was " + getType(element));
        }
    }

    public static BigDecimal getAsBigDecimal(JsonObject jsonObject, String s) {
        if (jsonObject.has(s)) {
            return convertToBigDecimal(jsonObject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a BigDecimal");
        }
    }

    public static BigDecimal getAsBigDecimal(JsonObject object, String s, BigDecimal bigDecimal) {
        return object.has(s) ? convertToBigDecimal(object.get(s), s) : bigDecimal;
    }

    public static BigInteger convertToBigInteger(JsonElement element, String s) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsBigInteger();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a BigInteger, was " + getType(element));
        }
    }

    public static BigInteger getAsBigInteger(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToBigInteger(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a BigInteger");
        }
    }

    public static BigInteger getAsBigInteger(JsonObject object, String s, BigInteger bigInteger) {
        return object.has(s) ? convertToBigInteger(object.get(s), s) : bigInteger;
    }

    public static short convertToShort(JsonElement jsonElement, String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsShort();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a Short, was " + getType(jsonElement));
        }
    }

    public static short getAsShort(JsonObject jsonObject, String s) {
        if (jsonObject.has(s)) {
            return convertToShort(jsonObject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a Short");
        }
    }

    public static short getAsShort(JsonObject object, String s, short sh) {
        return object.has(s) ? convertToShort(object.get(s), s) : sh;
    }

    public static JsonObject convertToJsonObject(JsonElement element, String s) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a JsonObject, was " + getType(element));
        }
    }

    public static JsonObject getAsJsonObject(JsonObject jsonObject, String s) {
        if (jsonObject.has(s)) {
            return convertToJsonObject(jsonObject.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonObject");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonObject getAsJsonObject(JsonObject jsonObject, String s, @Nullable JsonObject object) {
        return jsonObject.has(s) ? convertToJsonObject(jsonObject.get(s), s) : object;
    }

    public static JsonArray convertToJsonArray(JsonElement element, String s) {
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + s + " to be a JsonArray, was " + getType(element));
        }
    }

    public static JsonArray getAsJsonArray(JsonObject object, String s) {
        if (object.has(s)) {
            return convertToJsonArray(object.get(s), s);
        } else {
            throw new JsonSyntaxException("Missing " + s + ", expected to find a JsonArray");
        }
    }

    @Nullable
    @Contract("_,_,!null->!null;_,_,null->_")
    public static JsonArray getAsJsonArray(JsonObject object, String s, @Nullable JsonArray jsonArray) {
        return object.has(s) ? convertToJsonArray(object.get(s), s) : jsonArray;
    }

    public static <T> T convertToObject(@Nullable JsonElement element, String s, JsonDeserializationContext context, Class<? extends T> cls) {
        if (element != null) {
            return context.deserialize(element, cls);
        } else {
            throw new JsonSyntaxException("Missing " + s);
        }
    }

    public static <T> T getAsObject(JsonObject object, String s, JsonDeserializationContext context, Class<? extends T> cls) {
        if (object.has(s)) {
            return convertToObject(object.get(s), s, context, cls);
        } else {
            throw new JsonSyntaxException("Missing " + s);
        }
    }

    @Nullable
    @Contract("_,_,!null,_,_->!null;_,_,null,_,_->_")
    public static <T> T getAsObject(JsonObject object, String s, @Nullable T t, JsonDeserializationContext context, Class<? extends T> cls) {
        return (T)(object.has(s) ? convertToObject(object.get(s), s, context, cls) : t);
    }

    public static String getType(@Nullable JsonElement element) {
        String s = StringUtils.abbreviateMiddle(String.valueOf((Object)element), "...", 10);
        if (element == null) {
            return "null (missing)";
        } else if (element.isJsonNull()) {
            return "null (json)";
        } else if (element.isJsonArray()) {
            return "an array (" + s + ")";
        } else if (element.isJsonObject()) {
            return "an object (" + s + ")";
        } else {
            if (element.isJsonPrimitive()) {
                JsonPrimitive jsonprimitive = element.getAsJsonPrimitive();
                if (jsonprimitive.isNumber()) {
                    return "a number (" + s + ")";
                }

                if (jsonprimitive.isBoolean()) {
                    return "a boolean (" + s + ")";
                }
            }

            return s;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson gson, Reader reader, Class<T> cls, boolean lenient) {
        try {
            JsonReader jsonreader = new JsonReader(reader);
            jsonreader.setLenient(lenient);
            return gson.getAdapter(cls).read(jsonreader);
        } catch (IOException ioexception) {
            throw new JsonParseException(ioexception);
        }
    }

    public static <T> T fromJson(Gson gson, Reader reader, Class<T> cls, boolean lenient) {
        T t = fromNullableJson(gson, reader, cls, lenient);
        if (t == null) {
            throw new JsonParseException("JSON data was null or empty");
        } else {
            return t;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson gson, Reader reader, TypeToken<T> token, boolean lenient) {
        try {
            JsonReader jsonreader = new JsonReader(reader);
            jsonreader.setLenient(lenient);
            return gson.getAdapter(token).read(jsonreader);
        } catch (IOException ioexception) {
            throw new JsonParseException(ioexception);
        }
    }

    public static <T> T fromJson(Gson gson, Reader reader, TypeToken<T> token, boolean lenient) {
        T t = fromNullableJson(gson, reader, token, lenient);
        if (t == null) {
            throw new JsonParseException("JSON data was null or empty");
        } else {
            return t;
        }
    }

    @Nullable
    public static <T> T fromNullableJson(Gson gson, String s, TypeToken<T> token, boolean lenient) {
        return fromNullableJson(gson, new StringReader(s), token, lenient);
    }

    public static <T> T fromJson(Gson gson, String s, Class<T> cls, boolean lenient) {
        return fromJson(gson, new StringReader(s), cls, lenient);
    }

    @Nullable
    public static <T> T fromNullableJson(Gson gson, String s, Class<T> cls, boolean lenient) {
        return fromNullableJson(gson, new StringReader(s), cls, lenient);
    }

    public static <T> T fromJson(Gson gson, Reader reader, TypeToken<T> typeToken) {
        return fromJson(gson, reader, typeToken, false);
    }

    @Nullable
    public static <T> T fromNullableJson(Gson gson, String s, TypeToken<T> token) {
        return fromNullableJson(gson, s, token, false);
    }

    public static <T> T fromJson(Gson gson, Reader reader, Class<T> cls) {
        return fromJson(gson, reader, cls, false);
    }

    public static <T> T fromJson(Gson gson, String s, Class<T> cls) {
        return fromJson(gson, s, cls, false);
    }

    public static JsonObject parse(String s, boolean b) {
        return parse(new StringReader(s), b);
    }

    public static JsonObject parse(Reader reader, boolean b) {
        return fromJson(GSON, reader, JsonObject.class, b);
    }

    public static JsonObject parse(String s) {
        return parse(s, false);
    }

    public static JsonObject parse(Reader reader) {
        return parse(reader, false);
    }

    public static JsonArray parseArray(String s) {
        return parseArray(new StringReader(s));
    }

    public static JsonArray parseArray(Reader reader) {
        return fromJson(GSON, reader, JsonArray.class, false);
    }

    public static String toStableString(JsonElement element) {
        StringWriter stringwriter = new StringWriter();
        JsonWriter jsonwriter = new JsonWriter(stringwriter);

        try {
            writeValue(jsonwriter, element, Comparator.naturalOrder());
        } catch (IOException ioexception) {
            throw new AssertionError(ioexception);
        }

        return stringwriter.toString();
    }

    public static void writeValue(JsonWriter writer, @Nullable JsonElement element, @Nullable Comparator<String> comparator) throws IOException {
        if (element != null && !element.isJsonNull()) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive jsonprimitive = element.getAsJsonPrimitive();
                if (jsonprimitive.isNumber()) {
                    writer.value(jsonprimitive.getAsNumber());
                } else if (jsonprimitive.isBoolean()) {
                    writer.value(jsonprimitive.getAsBoolean());
                } else {
                    writer.value(jsonprimitive.getAsString());
                }
            } else if (element.isJsonArray()) {
                writer.beginArray();

                for(JsonElement jsonelement : element.getAsJsonArray()) {
                    writeValue(writer, jsonelement, comparator);
                }

                writer.endArray();
            } else {
                if (!element.isJsonObject()) {
                    throw new IllegalArgumentException("Couldn't write " + element.getClass());
                }

                writer.beginObject();

                for(Map.Entry<String, JsonElement> entry : sortByKeyIfNeeded(element.getAsJsonObject().entrySet(), comparator)) {
                    writer.name(entry.getKey());
                    writeValue(writer, entry.getValue(), comparator);
                }

                writer.endObject();
            }
        } else {
            writer.nullValue();
        }

    }

    private static Collection<Map.Entry<String, JsonElement>> sortByKeyIfNeeded(Collection<Map.Entry<String, JsonElement>> to, @Nullable Comparator<String> comparator) {
        if (comparator == null) {
            return to;
        } else {
            List<Map.Entry<String, JsonElement>> list = new ArrayList<>(to);
            list.sort(Map.Entry.comparingByKey(comparator));
            return list;
        }
    }
}
