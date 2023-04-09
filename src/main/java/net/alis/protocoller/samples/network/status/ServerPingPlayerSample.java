package net.alis.protocoller.samples.network.status;

import com.google.gson.*;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.alis.protocoller.samples.util.ChatDeserializer;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.UUID;

public class ServerPingPlayerSample {
    private int max;
    private int online;
    @Nullable
    private GameProfile[] sample;

    public ServerPingPlayerSample(int max, int online) {
        this.max = max;
        this.online = online;
    }

    public ServerPingPlayerSample(Object pingPlayerSample) {
        AccessedObject accessor = new AccessedObject(pingPlayerSample);
        this.max = accessor.read(0, int.class);
        this.online = accessor.read(1, int.class);
        Object[] profiles = accessor.read(0, Array.newInstance(ClassAccessor.get().getGameProfileClass(), 1).getClass());
        if(profiles != null && profiles.length > 0) {
            this.sample = new GameProfile[profiles.length];
            for(int i = 0; i < profiles.length; i++) {
                this.sample[i] = new GameProfile(profiles[i]);
            }
        }
    }

    public int getMax() {
        return this.max;
    }

    public int getOnline() {
        return this.online;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    @Nullable
    public GameProfile[] getSample() {
        return this.sample;
    }

    public void setSample(GameProfile @Nullable [] sample) {
        this.sample = sample;
    }

    public static class Serializer implements JsonDeserializer<ServerPingPlayerSample>, JsonSerializer<ServerPingPlayerSample> {
        public Serializer() { }

        @Override
        public ServerPingPlayerSample deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = ChatDeserializer.elementAsJsonObject(jsonElement, "players");
            ServerPingPlayerSample players = new ServerPingPlayerSample(ChatDeserializer.intFromJson(jsonObject, "max"), ChatDeserializer.intFromJson(jsonObject, "online"));
            if (ChatDeserializer.isJsonArray(jsonObject, "sample")) {
                JsonArray jsonArray = ChatDeserializer.jsonArrayFromObject(jsonObject, "sample");
                if (jsonArray.size() > 0) {
                    GameProfile[] gameProfiles = new GameProfile[jsonArray.size()];

                    for(int i = 0; i < gameProfiles.length; ++i) {
                        JsonObject jsonObject2 = ChatDeserializer.elementAsJsonObject(jsonArray.get(i), "player[" + i + "]");
                        String string = ChatDeserializer.getFrom(jsonObject2, "id");
                        gameProfiles[i] = new GameProfile(UUID.fromString(string), ChatDeserializer.getFrom(jsonObject2, "name"));
                    }

                    players.setSample(gameProfiles);
                }
            }

            return players;
        }

        @Override
        public JsonElement serialize(@NotNull ServerPingPlayerSample players, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("max", players.max);
            jsonObject.addProperty("online", players.online);
            GameProfile[] gameProfiles = players.sample;
            if (gameProfiles != null && gameProfiles.length > 0) {
                JsonArray jsonArray = new JsonArray();

                for(int i = 0; i < gameProfiles.length; ++i) {
                    JsonObject jsonObject2 = new JsonObject();
                    UUID uUID = gameProfiles[i].getId();
                    jsonObject2.addProperty("id", uUID == null ? "" : uUID.toString());
                    jsonObject2.addProperty("name", gameProfiles[i].getName());
                    jsonArray.add(jsonObject2);
                }

                jsonObject.add("sample", jsonArray);
            }

            return jsonObject;
        }
    }

    public Object createOriginal() {
        Object response = Reflect.callConstructor(
                Reflect.getConstructor(ClassAccessor.get().getServerPingPlayerSampleClass(), int.class, int.class),
                this.max, this.online
        );
        if(this.sample != null && this.sample.length > 0) {
            Object[] profiles = new Object[this.sample.length];
            for(int i = 0; i < this.sample.length; i++) {
                profiles[i] = ((GameProfile)this.sample[i]).createOriginal();
            }
            AccessedObject accessor = new AccessedObject(response);
            accessor.write(0, Object[].class);
            return accessor.getObject();
        }
        return response;
    }

}
