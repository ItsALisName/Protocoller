package net.alis.protocoller.plugin.network.packet;

import net.alis.protocoller.packet.MinecraftPacketType;
import net.alis.protocoller.packet.PacketType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PacketTypesInitializer extends PacketType {

    private static final List<MinecraftPacketType> packets = new ArrayList<>();

    public static class Play extends PacketType.Play {
        public static class Client extends PacketType.Play.Client { }
        public static class Server extends PacketType.Play.Server { }
    }

    public static class Handshake extends PacketType.Handshake {
        public static class Client extends PacketType.Handshake.Client { }
        public static class Server extends PacketType.Handshake.Server { }
    }

    public static class Login {
        public static class Client extends PacketType.Login.Client { }
        public static class Server extends PacketType.Login.Server { }
    }

    public static class Status extends PacketType.Status {
        public static class Client extends PacketType.Status.Client { }
        public static class Server extends PacketType.Status.Server { }
    }

    public static @Nullable MinecraftPacketType setPacketType(Object obj) {
        for(MinecraftPacketType type : packets) {
            if(obj.getClass() == type.getPacketClass()) return type;
        }
        return null;
    }

    public static @Nullable MinecraftPacketType setPacketType(Class<?> clazz) {
        for(MinecraftPacketType type : packets) {
            if(clazz == type.getPacketClass()) return type;
        }
        new RuntimeException("Failed to find packet type for \"" + clazz.getSimpleName() + "\"").printStackTrace();
        return null;
    }

    public static void init() {
        Login.Client.CUSTOM_PAYLOAD = new ProtocollerPacketType("CustomPayload", State.LOGIN_CLIENTBOUND, (byte) -66); packets.add(Login.Client.CUSTOM_PAYLOAD);
        Login.Client.START = new ProtocollerPacketType("Start", State.LOGIN_CLIENTBOUND, (byte) -65); packets.add(Login.Client.START);
        Login.Client.ENCRYPTION_BEGIN = new ProtocollerPacketType("EncryptionBegin", State.LOGIN_CLIENTBOUND, (byte) -64); packets.add(Login.Client.ENCRYPTION_BEGIN);

        Login.Server.CUSTOM_PAYLOAD = new ProtocollerPacketType("CustomPayload", State.LOGIN_SERVERBOUND, (byte) -63); packets.add(Login.Server.CUSTOM_PAYLOAD);
        Login.Server.DISCONNECT = new ProtocollerPacketType("Disconnect", State.LOGIN_SERVERBOUND, (byte) -62); packets.add(Login.Server.DISCONNECT);
        Login.Server.ENCRYPTION_BEGIN = new ProtocollerPacketType("EncryptionBegin", State.LOGIN_SERVERBOUND, (byte) -61); packets.add(Login.Server.ENCRYPTION_BEGIN);
        Login.Server.SET_COMPRESSION = new ProtocollerPacketType("SetCompression", State.LOGIN_SERVERBOUND, (byte) -60); packets.add(Login.Server.SET_COMPRESSION);
        Login.Server.SUCCESS = new ProtocollerPacketType("Success", State.LOGIN_SERVERBOUND, (byte) -59); packets.add(Login.Server.SUCCESS);

        Handshake.Client.SET_PROTOCOL = new ProtocollerPacketType("SetProtocol", State.HANDSHAKE_CLIENTBOUND, (byte) -58); packets.add(Handshake.Client.SET_PROTOCOL);

        Status.Client.PING = new ProtocollerPacketType("Ping", State.STATUS_CLIENTBOUND, (byte) -57); packets.add(Status.Client.PING);
        Status.Client.START = new ProtocollerPacketType("Start", State.STATUS_CLIENTBOUND, (byte) -56); packets.add(Status.Client.START);

        Status.Server.PONG = new ProtocollerPacketType("Pong", State.STATUS_SERVERBOUND, (byte) -55); packets.add(Status.Server.PONG);
        Status.Server.SERVER_INFO = new ProtocollerPacketType("ServerInfo", State.STATUS_SERVERBOUND, (byte) -54); packets.add(Status.Server.SERVER_INFO);

        Play.Client.ABILITIES = new ProtocollerPacketType("Abilities", State.PLAY_CLIENTBOUND, (byte) -128); packets.add(Play.Client.ABILITIES);
        Play.Client.ARM_ANIMATION = new ProtocollerPacketType("ArmAnimation", State.PLAY_CLIENTBOUND, (byte) -127); packets.add(Play.Client.ARM_ANIMATION);
        Play.Client.ADVANCEMENTS = new ProtocollerPacketType("Advancements", State.PLAY_CLIENTBOUND, (byte) -126); packets.add(Play.Client.ADVANCEMENTS);
        Play.Client.BEACON = new ProtocollerPacketType("Beacon", State.PLAY_CLIENTBOUND, (byte) -125); packets.add(Play.Client.BEACON);
        Play.Client.BEDIT = new ProtocollerPacketType("BEdit", State.PLAY_CLIENTBOUND, (byte) -124); packets.add(Play.Client.BEDIT);
        Play.Client.BLOCK_DIG = new ProtocollerPacketType("BlockDig", State.PLAY_CLIENTBOUND, (byte) -123); packets.add(Play.Client.BLOCK_DIG);
        Play.Client.WINDOW_CLICK = new ProtocollerPacketType("WindowClick", State.PLAY_CLIENTBOUND, (byte) -122); packets.add(Play.Client.WINDOW_CLICK);
        Play.Client.BLOCK_PLACE = new ProtocollerPacketType("BlockPlace", State.PLAY_CLIENTBOUND, (byte) -121); packets.add(Play.Client.BLOCK_PLACE);
        Play.Client.AUTO_RECIPE = new ProtocollerPacketType("AutoRecipe", State.PLAY_CLIENTBOUND, (byte) -120); packets.add(Play.Client.AUTO_RECIPE);
        Play.Client.BOAT_MOVE = new ProtocollerPacketType("BoatMove", State.PLAY_CLIENTBOUND, (byte) -119); packets.add(Play.Client.BOAT_MOVE);
        Play.Client.CHAT = new ProtocollerPacketType("Chat", State.PLAY_CLIENTBOUND, (byte) -118); packets.add(Play.Client.CHAT);
        Play.Client.CLIENT_COMMAND = new ProtocollerPacketType("ClientCommand", State.PLAY_CLIENTBOUND, (byte) -117); packets.add(Play.Client.CLIENT_COMMAND);
        Play.Client.CLOSE_WINDOW = new ProtocollerPacketType("CloseWindow", State.PLAY_CLIENTBOUND, (byte) -116); packets.add(Play.Client.CLOSE_WINDOW);
        Play.Client.ENTITY_ACTION = new ProtocollerPacketType("EntityAction", State.PLAY_CLIENTBOUND, (byte) -115); packets.add(Play.Client.ENTITY_ACTION);
        Play.Client.FLYING = new ProtocollerPacketType("Flying", State.PLAY_CLIENTBOUND, (byte) -114); packets.add(Play.Client.FLYING);
        Play.Client.DIFFICULTY_LOCK = new ProtocollerPacketType("DifficultyLock", State.PLAY_CLIENTBOUND, (byte) -113); packets.add(Play.Client.DIFFICULTY_LOCK);
        Play.Client.KEEP_ALIVE = new ProtocollerPacketType("KeepAlive", State.PLAY_CLIENTBOUND, (byte) -112); packets.add(Play.Client.KEEP_ALIVE);
        Play.Client.ENCHANT_ITEM = new ProtocollerPacketType("EnchantItem", State.PLAY_CLIENTBOUND, (byte) -111); packets.add(Play.Client.ENCHANT_ITEM);
        Play.Client.RECIPE_SETTINGS = new ProtocollerPacketType("RecipeSettings", State.PLAY_CLIENTBOUND, (byte) -110); packets.add(Play.Client.RECIPE_SETTINGS);
        Play.Client.SET_CREATIVE_SLOT = new ProtocollerPacketType("SetCreativeSlot", State.PLAY_CLIENTBOUND, (byte) -109); packets.add(Play.Client.SET_CREATIVE_SLOT);
        Play.Client.RESOURCEPACK_STATUS = new ProtocollerPacketType("ResourcePackStatus", State.PLAY_CLIENTBOUND, (byte) -108); packets.add(Play.Client.RESOURCEPACK_STATUS);
        Play.Client.SET_COMMAND_MINECART = new ProtocollerPacketType("SetCommandMinecart", State.PLAY_CLIENTBOUND, (byte) 107); packets.add(Play.Client.SET_COMMAND_MINECART);
        Play.Client.SETTINGS = new ProtocollerPacketType("Settings", State.PLAY_CLIENTBOUND, (byte) -106); packets.add(Play.Client.SETTINGS);
        Play.Client.SET_COMMAND_BLOCK = new ProtocollerPacketType("SetCommandBlock", State.PLAY_CLIENTBOUND, (byte) -105); packets.add(Play.Client.SET_COMMAND_BLOCK);
        Play.Client.TR_SEL = new ProtocollerPacketType("TrSel", State.PLAY_CLIENTBOUND, (byte) -104); packets.add(Play.Client.TR_SEL);
        Play.Client.STEER_VEHICLE = new ProtocollerPacketType("SteerVehicle", State.PLAY_CLIENTBOUND, (byte) -103); packets.add(Play.Client.STEER_VEHICLE);
        Play.Client.SET_JIGSAW = new ProtocollerPacketType("SetJigsaw", State.PLAY_CLIENTBOUND, (byte) -102); packets.add(Play.Client.SET_JIGSAW);
        Play.Client.JIGSAW_GENERATE = new ProtocollerPacketType("JigsawGenerate", State.PLAY_CLIENTBOUND, (byte) -101); packets.add(Play.Client.JIGSAW_GENERATE);
        Play.Client.TAB_COMPLETE = new ProtocollerPacketType("TabComplete", State.PLAY_CLIENTBOUND, (byte) -100); packets.add(Play.Client.TAB_COMPLETE);
        Play.Client.PICK_ITEM = new ProtocollerPacketType("PickItem", State.PLAY_CLIENTBOUND, (byte) -99); packets.add(Play.Client.PICK_ITEM);
        Play.Client.ITEM_NAME = new ProtocollerPacketType("ItemName", State.PLAY_CLIENTBOUND, (byte) -98); packets.add(Play.Client.ITEM_NAME);
        Play.Client.VEHICLE_MOVE = new ProtocollerPacketType("VehicleMove", State.PLAY_CLIENTBOUND, (byte) -97); packets.add(Play.Client.VEHICLE_MOVE);
        Play.Client.USE_ITEM = new ProtocollerPacketType("UseItem", State.PLAY_CLIENTBOUND, (byte) -96); packets.add(Play.Client.USE_ITEM);
        Play.Client.USE_ENTITY = new ProtocollerPacketType("UseEntity", State.PLAY_CLIENTBOUND, (byte) -95); packets.add(Play.Client.USE_ENTITY);
        Play.Client.UPDATE_SIGN = new ProtocollerPacketType("UpdateSign", State.PLAY_CLIENTBOUND, (byte) -94); packets.add(Play.Client.UPDATE_SIGN);
        Play.Client.TILE_NBT_QUERY = new ProtocollerPacketType("TileNBTQuery", State.PLAY_CLIENTBOUND, (byte) -93); packets.add(Play.Client.TILE_NBT_QUERY);
        Play.Client.TELEPORT_ACCEPT = new ProtocollerPacketType("TeleportAccept", State.PLAY_CLIENTBOUND, (byte) -92); packets.add(Play.Client.TELEPORT_ACCEPT);
        Play.Client.STRUCT = new ProtocollerPacketType("Struct", State.PLAY_CLIENTBOUND, (byte) -91); packets.add(Play.Client.STRUCT);
        Play.Client.SPECTATE = new ProtocollerPacketType("Spectate", State.PLAY_CLIENTBOUND, (byte) -90); packets.add(Play.Client.SPECTATE);
        Play.Client.RECIPE_DISPLAYED = new ProtocollerPacketType("RecipeDisplayed", State.PLAY_CLIENTBOUND, (byte) -89); packets.add(Play.Client.RECIPE_DISPLAYED);
        Play.Client.HELD_ITEM_SLOT = new ProtocollerPacketType("HeldItemSlot", State.PLAY_CLIENTBOUND, (byte) -88); packets.add(Play.Client.HELD_ITEM_SLOT);
        Play.Client.ENTITY_NBT_QUERY = new ProtocollerPacketType("EntityNBTQuery", State.PLAY_CLIENTBOUND, (byte) -87); packets.add(Play.Client.ENTITY_NBT_QUERY);
        Play.Client.DIFFICULTY_CHANGE = new ProtocollerPacketType("DifficultyChange", State.PLAY_CLIENTBOUND, (byte) -86); packets.add(Play.Client.DIFFICULTY_CHANGE);
        Play.Client.CUSTOM_PAYLOAD = new ProtocollerPacketType("CustomPayload", State.PLAY_CLIENTBOUND, (byte) -85); packets.add(Play.Client.CUSTOM_PAYLOAD);

        Play.Server.ABILITIES = new ProtocollerPacketType("Abilities", State.PLAY_SERVERBOUND, (byte) -84); packets.add(Play.Server.ABILITIES);
        Play.Server.WORLD_PARTICLES = new ProtocollerPacketType("WorldParticles", State.PLAY_SERVERBOUND, (byte) -83); packets.add(Play.Server.WORLD_PARTICLES);
        Play.Server.WORLD_EVENT = new ProtocollerPacketType("WorldEvent", State.PLAY_SERVERBOUND, (byte) -82); packets.add(Play.Server.WORLD_EVENT);
        Play.Server.WINDOW_ITEMS = new ProtocollerPacketType("WindowItems", State.PLAY_SERVERBOUND, (byte) -81); packets.add(Play.Server.WINDOW_ITEMS);
        Play.Server.WINDOW_DATA = new ProtocollerPacketType("WindowData", State.PLAY_SERVERBOUND, (byte) -80); packets.add(Play.Server.WINDOW_DATA);
        Play.Server.VIEW_DISTANCE = new ProtocollerPacketType("ViewDistance", State.PLAY_SERVERBOUND, (byte) -79); packets.add(Play.Server.VIEW_DISTANCE);
        Play.Server.VIEW_CENTRE = new ProtocollerPacketType("ViewCentre", State.PLAY_SERVERBOUND, (byte) -78); packets.add(Play.Server.VIEW_CENTRE);
        Play.Server.VEHICLE_MOVE = new ProtocollerPacketType("VehicleMove", State.PLAY_SERVERBOUND, (byte) -77); packets.add(Play.Server.VEHICLE_MOVE);
        Play.Server.UPDATE_TIME = new ProtocollerPacketType("UpdateTime", State.PLAY_SERVERBOUND, (byte) -76); packets.add(Play.Server.UPDATE_TIME);
        Play.Server.UPDATE_HEALTH = new ProtocollerPacketType("UpdateHealth", State.PLAY_SERVERBOUND, (byte) -75); packets.add(Play.Server.UPDATE_HEALTH);
        Play.Server.UPDATE_ATTRIBUTES = new ProtocollerPacketType("UpdateAttributes", State.PLAY_SERVERBOUND, (byte) -74); packets.add(Play.Server.UPDATE_ATTRIBUTES);
        Play.Server.UNLOAD_CHUNK = new ProtocollerPacketType("UnloadChunk", State.PLAY_SERVERBOUND, (byte) -73); packets.add(Play.Server.UNLOAD_CHUNK);
        Play.Server.TILE_ENTITY_DATA = new ProtocollerPacketType("TileEntityData", State.PLAY_SERVERBOUND, (byte) -72); packets.add(Play.Server.TILE_ENTITY_DATA);
        Play.Server.TAGS = new ProtocollerPacketType("Tags", State.PLAY_SERVERBOUND, (byte) -71); packets.add(Play.Server.TAGS);
        Play.Server.TAB_COMPLETE = new ProtocollerPacketType("TabComplete", State.PLAY_SERVERBOUND, (byte) -70); packets.add(Play.Server.TAB_COMPLETE);
        Play.Server.STOP_SOUND = new ProtocollerPacketType("StopSound", State.PLAY_SERVERBOUND, (byte) -69); packets.add(Play.Server.STOP_SOUND);
        Play.Server.STATISTIC = new ProtocollerPacketType("Statistic", State.PLAY_SERVERBOUND, (byte) -68); packets.add(Play.Server.STATISTIC);
        Play.Server.SPAWN_POSITION = new ProtocollerPacketType("SpawnPosition", State.PLAY_SERVERBOUND, (byte) -67); packets.add(Play.Server.SPAWN_POSITION);
        Play.Server.SPAWN_ENTITY_PAINTING = new ProtocollerPacketType("SpawnEntityPainting", State.PLAY_SERVERBOUND, (byte) -66); packets.add(Play.Server.SPAWN_ENTITY_PAINTING);
        Play.Server.SPAWN_ENTITY_LIVING = new ProtocollerPacketType("SpawnEntityLiving", State.PLAY_SERVERBOUND, (byte) -65); packets.add(Play.Server.SPAWN_ENTITY_LIVING);
        Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB = new ProtocollerPacketType("SpawnEntityExperienceOrb", State.PLAY_SERVERBOUND, (byte) -64); packets.add(Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB);
        Play.Server.SET_SLOT = new ProtocollerPacketType("SetSlot", State.PLAY_SERVERBOUND, (byte) -63); packets.add(Play.Server.SET_SLOT);
        Play.Server.SET_COOLDOWN = new ProtocollerPacketType("SetCooldown", State.PLAY_SERVERBOUND, (byte) -62); packets.add(Play.Server.SET_COOLDOWN);
        Play.Server.SERVER_DIFFICULTY = new ProtocollerPacketType("ServerDifficulty", State.PLAY_SERVERBOUND, (byte) -61); packets.add(Play.Server.SERVER_DIFFICULTY);
        Play.Server.SELECT_ADVANCEMENT_TAB = new ProtocollerPacketType("SelectAdvancementTab", State.PLAY_SERVERBOUND, (byte) -60); packets.add(Play.Server.SELECT_ADVANCEMENT_TAB);
        Play.Server.SCOREBOARD_TEAM = new ProtocollerPacketType("ScoreboardTeam", State.PLAY_SERVERBOUND, (byte) -59); packets.add(Play.Server.SCOREBOARD_TEAM);
        Play.Server.SCOREBOARD_SCORE = new ProtocollerPacketType("ScoreboardScore", State.PLAY_SERVERBOUND, (byte) -58); packets.add(Play.Server.SCOREBOARD_SCORE);
        Play.Server.SCOREBOARD_OBJECTIVE = new ProtocollerPacketType("ScoreboardObjective", State.PLAY_SERVERBOUND, (byte) -57); packets.add(Play.Server.SCOREBOARD_OBJECTIVE);
        Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE = new ProtocollerPacketType("ScoreboardDisplayObjective", State.PLAY_SERVERBOUND, (byte) -56); packets.add(Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        Play.Server.RESPAWN = new ProtocollerPacketType("Respawn", State.PLAY_SERVERBOUND, (byte) -55); packets.add(Play.Server.RESPAWN);
        Play.Server.RESOURCE_PACK_SEND = new ProtocollerPacketType("ResourcePackSend", State.PLAY_SERVERBOUND, (byte) -54); packets.add(Play.Server.RESOURCE_PACK_SEND);
        Play.Server.REMOVE_ENTITY_EFFECT = new ProtocollerPacketType("RemoveEntityEffect", State.PLAY_SERVERBOUND, (byte) -53); packets.add(Play.Server.REMOVE_ENTITY_EFFECT);
        Play.Server.RECIPES = new ProtocollerPacketType("Recipes", State.PLAY_SERVERBOUND, (byte) -52); packets.add(Play.Server.RECIPES);
        Play.Server.RECIPE_UPDATE = new ProtocollerPacketType("RecipeUpdate", State.PLAY_SERVERBOUND, (byte) -51); packets.add(Play.Server.RECIPE_UPDATE);
        Play.Server.POSITION = new ProtocollerPacketType("Position", State.PLAY_SERVERBOUND, (byte) -50); packets.add(Play.Server.POSITION);
        Play.Server.PLAYER_LIST_HEADER_FOOTER = new ProtocollerPacketType("PlayerListHeaderFooter", State.PLAY_SERVERBOUND, (byte) -49); packets.add(Play.Server.PLAYER_LIST_HEADER_FOOTER);
        Play.Server.PLAYER_INFO = new ProtocollerPacketType("PlayerInfo", State.PLAY_SERVERBOUND, (byte) -48); packets.add(Play.Server.PLAYER_INFO);
        Play.Server.OPEN_WINDOW_MERCHANT = new ProtocollerPacketType("OpenWindowMerchant", State.PLAY_SERVERBOUND, (byte) -47); packets.add(Play.Server.OPEN_WINDOW_MERCHANT);
        Play.Server.OPEN_WINDOW_HORSE = new ProtocollerPacketType("OpenWindowHorse", State.PLAY_SERVERBOUND, (byte) -46); packets.add(Play.Server.OPEN_WINDOW_HORSE);
        Play.Server.OPEN_SIGN_EDITOR = new ProtocollerPacketType("OpenSignEditor", State.PLAY_SERVERBOUND, (byte) -45); packets.add(Play.Server.OPEN_SIGN_EDITOR);
        Play.Server.OPEN_BOOK = new ProtocollerPacketType("OpenBook", State.PLAY_SERVERBOUND, (byte) -44); packets.add(Play.Server.OPEN_BOOK);
        Play.Server.NBT_QUERY = new ProtocollerPacketType("NBTQuery", State.PLAY_SERVERBOUND, (byte) -43); packets.add(Play.Server.NBT_QUERY);
        Play.Server.NAMED_SOUND_EFFECT = new ProtocollerPacketType("NamedSoundEffect", State.PLAY_SERVERBOUND, (byte) -42); packets.add(Play.Server.NAMED_SOUND_EFFECT);
        Play.Server.NAMED_ENTITY_SPAWN = new ProtocollerPacketType("NamedEntitySpawn", State.PLAY_SERVERBOUND, (byte) -41); packets.add(Play.Server.NAMED_ENTITY_SPAWN);
        Play.Server.MULTI_BLOCK_CHANGE = new ProtocollerPacketType("MultiBlockChange", State.PLAY_SERVERBOUND, (byte) -40); packets.add(Play.Server.MULTI_BLOCK_CHANGE);
        Play.Server.MOUNT = new ProtocollerPacketType("Mount", State.PLAY_SERVERBOUND, (byte) -39); packets.add(Play.Server.MOUNT);
        Play.Server.MAP_CHUNK = new ProtocollerPacketType("MapChunk", State.PLAY_SERVERBOUND, (byte) -38); packets.add(Play.Server.MAP_CHUNK);
        Play.Server.LOOK_AT = new ProtocollerPacketType("LookAt", State.PLAY_SERVERBOUND, (byte) -37); packets.add(Play.Server.LOOK_AT);
        Play.Server.LOGIN = new ProtocollerPacketType("Login", State.PLAY_SERVERBOUND, (byte) -36); packets.add(Play.Server.LOGIN);
        Play.Server.LIGHT_UPDATE = new ProtocollerPacketType("LightUpdate", State.PLAY_SERVERBOUND, (byte) -35); packets.add(Play.Server.LIGHT_UPDATE);
        Play.Server.KICK_DISCONNECT = new ProtocollerPacketType("KickDisconnect", State.PLAY_SERVERBOUND, (byte) -34); packets.add(Play.Server.KICK_DISCONNECT);
        Play.Server.KEEP_ALIVE = new ProtocollerPacketType("KeepAlive", State.PLAY_SERVERBOUND, (byte) -33); packets.add(Play.Server.KEEP_ALIVE);
        Play.Server.HELD_ITEM_SLOT = new ProtocollerPacketType("HeldItemSlot", State.PLAY_SERVERBOUND, (byte) -32); packets.add(Play.Server.HELD_ITEM_SLOT);
        Play.Server.GAME_STATE_CHANGE = new ProtocollerPacketType("GameStateChange", State.PLAY_SERVERBOUND, (byte) -31); packets.add(Play.Server.GAME_STATE_CHANGE);
        Play.Server.EXPLOSION = new ProtocollerPacketType("Explosion", State.PLAY_SERVERBOUND, (byte) -30); packets.add(Play.Server.EXPLOSION);
        Play.Server.EXPERIENCE = new ProtocollerPacketType("Experience", State.PLAY_SERVERBOUND, (byte) -29); packets.add(Play.Server.EXPERIENCE);
        Play.Server.ENTITY_VELOCITY = new ProtocollerPacketType("EntityVelocity", State.PLAY_SERVERBOUND, (byte) -28); packets.add(Play.Server.ENTITY_VELOCITY);
        Play.Server.ENTITY_TELEPORT = new ProtocollerPacketType("EntityTeleport", State.PLAY_SERVERBOUND, (byte) -27); packets.add(Play.Server.ENTITY_TELEPORT);
        Play.Server.ENTITY_STATUS = new ProtocollerPacketType("EntityStatus", State.PLAY_SERVERBOUND, (byte) -26); packets.add(Play.Server.ENTITY_STATUS);
        Play.Server.ENTITY_SOUND = new ProtocollerPacketType("EntitySound", State.PLAY_SERVERBOUND, (byte) -25); packets.add(Play.Server.ENTITY_SOUND);
        Play.Server.ENTITY_METADATA = new ProtocollerPacketType("EntityMetadata", State.PLAY_SERVERBOUND, (byte) -24); packets.add(Play.Server.ENTITY_METADATA);
        Play.Server.ENTITY_HEAD_ROTATION = new ProtocollerPacketType("EntityHeadRotation", State.PLAY_SERVERBOUND, (byte) -23); packets.add(Play.Server.ENTITY_HEAD_ROTATION);
        Play.Server.ENTITY_EQUIPMENT = new ProtocollerPacketType("EntityEquipment", State.PLAY_SERVERBOUND, (byte) -22); packets.add(Play.Server.ENTITY_EQUIPMENT);
        Play.Server.ENTITY_EFFECT = new ProtocollerPacketType("EntityEffect", State.PLAY_SERVERBOUND, (byte) -21); packets.add(Play.Server.ENTITY_EFFECT);
        Play.Server.ENTITY_DESTROY = new ProtocollerPacketType("EntityDestroy", State.PLAY_SERVERBOUND, (byte) -20); packets.add(Play.Server.ENTITY_DESTROY);
        Play.Server.CUSTOM_SOUND_EFFECT = new ProtocollerPacketType("CustomSoundEffect", State.PLAY_SERVERBOUND, (byte) -19); packets.add(Play.Server.CUSTOM_SOUND_EFFECT);
        Play.Server.CUSTOM_PAYLOAD = new ProtocollerPacketType("CustomPayload", State.PLAY_SERVERBOUND, (byte) -18); packets.add(Play.Server.CUSTOM_PAYLOAD);
        Play.Server.COMMANDS = new ProtocollerPacketType("Commands", State.PLAY_SERVERBOUND, (byte) -17); packets.add(Play.Server.COMMANDS);
        Play.Server.COLLECT = new ProtocollerPacketType("Collect", State.PLAY_SERVERBOUND, (byte) -16); packets.add(Play.Server.COLLECT);
        Play.Server.CLOSE_WINDOW = new ProtocollerPacketType("CloseWindow", State.PLAY_SERVERBOUND, (byte) -15); packets.add(Play.Server.CLOSE_WINDOW);
        Play.Server.CHAT = new ProtocollerPacketType("Chat", State.PLAY_SERVERBOUND, (byte) -14); packets.add(Play.Server.CHAT);
        Play.Server.CAMERA = new ProtocollerPacketType("Camera", State.PLAY_SERVERBOUND, (byte) -13); packets.add(Play.Server.CAMERA);
        Play.Server.BOSS = new ProtocollerPacketType("Boss", State.PLAY_SERVERBOUND, (byte) -12); packets.add(Play.Server.BOSS);
        Play.Server.BLOCK_CHANGE = new ProtocollerPacketType("BlockChange", State.PLAY_SERVERBOUND, (byte) -11); packets.add(Play.Server.BLOCK_CHANGE);
        Play.Server.BLOCK_BREAK_ANIMATION = new ProtocollerPacketType("BlockBreakAnimation", State.PLAY_SERVERBOUND, (byte) -10); packets.add(Play.Server.BLOCK_BREAK_ANIMATION);
        Play.Server.BLOCK_ACTION = new ProtocollerPacketType("BlockAction", State.PLAY_SERVERBOUND, (byte) -9); packets.add(Play.Server.BLOCK_ACTION);
        Play.Server.AUTO_RECIPE = new ProtocollerPacketType("AutoRecipe", State.PLAY_SERVERBOUND, (byte) -8); packets.add(Play.Server.AUTO_RECIPE);
        Play.Server.ATTACH_ENTITY = new ProtocollerPacketType("AttachEntity", State.PLAY_SERVERBOUND, (byte) -7); packets.add(Play.Server.ATTACH_ENTITY);
        Play.Server.ANIMATION = new ProtocollerPacketType("Animation", State.PLAY_SERVERBOUND, (byte) -6); packets.add(Play.Server.ANIMATION);
        Play.Server.ADVANCEMENTS = new ProtocollerPacketType("Advancements", State.PLAY_SERVERBOUND, (byte) -5); packets.add(Play.Server.ADVANCEMENTS);
        Play.Server.MAP = new ProtocollerPacketType("Map", State.PLAY_SERVERBOUND, (byte) -4); packets.add(Play.Server.MAP);
        Play.Server.SPAWN_ENTITY = new ProtocollerPacketType("SpawnEntity", State.PLAY_SERVERBOUND, (byte) -3); packets.add(Play.Server.SPAWN_ENTITY);
        Play.Server.OPEN_WINDOW = new ProtocollerPacketType("OpenWindow", State.PLAY_SERVERBOUND, (byte) -2); packets.add(Play.Server.OPEN_WINDOW);
        Play.Server.ENTITY = new ProtocollerPacketType("Entity", State.PLAY_SERVERBOUND, (byte) -1); packets.add(Play.Server.ENTITY);
        Play.Server.BLOCK_BREAK = new ProtocollerPacketType("BlockBreak", State.PLAY_SERVERBOUND, (byte) 0); packets.add(Play.Server.BLOCK_BREAK);
        Play.Server.ADD_VIBRATION_SIGNAL_PACKET = new ProtocollerPacketType("AddVibrationSignalPacket", State.CLIENTBOUND, (byte) 1); packets.add(Play.Server.ADD_VIBRATION_SIGNAL_PACKET);
        Play.Server.CLEAR_TITLES_PACKET = new ProtocollerPacketType("ClearTitlesPacket", State.CLIENTBOUND, (byte) 2); packets.add(Play.Server.CLEAR_TITLES_PACKET);
        Play.Server.INITIALIZE_BORDER_PACKET = new ProtocollerPacketType("InitializeBorderPacket", State.CLIENTBOUND, (byte) 3); packets.add(Play.Server.INITIALIZE_BORDER_PACKET);
        Play.Server.PING_PACKET = new ProtocollerPacketType("PingPacket", State.CLIENTBOUND, (byte) 4); packets.add(Play.Server.PING_PACKET);
        Play.Server.PLAYER_COMBAT_END_PACKET = new ProtocollerPacketType("PlayerCombatEndPacket", State.CLIENTBOUND, (byte) 5); packets.add(Play.Server.PLAYER_COMBAT_END_PACKET);
        Play.Server.PLAYER_COMBAT_ENTER_PACKET = new ProtocollerPacketType("PlayerCombatEnterPacket", State.CLIENTBOUND, (byte) 6); packets.add(Play.Server.PLAYER_COMBAT_ENTER_PACKET);
        Play.Server.PLAYER_COMBAT_KILL_PACKET = new ProtocollerPacketType("PlayerCombatKillPacket", State.CLIENTBOUND, (byte) 7); packets.add(Play.Server.PLAYER_COMBAT_KILL_PACKET);
        Play.Server.SET_ACTIONBAR_TEXT_PACKET = new ProtocollerPacketType("SetActionBarTextPacket", State.CLIENTBOUND, (byte) 8); packets.add(Play.Server.SET_ACTIONBAR_TEXT_PACKET);
        Play.Server.SET_BORDER_CENTER_PACKET = new ProtocollerPacketType("SetBorderCenterPacket", State.CLIENTBOUND, (byte) 9); packets.add(Play.Server.SET_BORDER_CENTER_PACKET);
        Play.Server.SET_BORDER_LERP_SIZE_PACKET = new ProtocollerPacketType("SetBorderLerpSizePacket", State.CLIENTBOUND, (byte) 10); packets.add(Play.Server.SET_BORDER_LERP_SIZE_PACKET);
        Play.Server.SET_BORDER_SIZE_PACKET = new ProtocollerPacketType("SetBorderSizePacket", State.CLIENTBOUND, (byte) 11); packets.add(Play.Server.SET_BORDER_SIZE_PACKET);
        Play.Server.SET_BORDER_WARNING_DELAY_PACKET = new ProtocollerPacketType("SetBorderWarningDelayPacket", State.CLIENTBOUND, (byte) 12); packets.add(Play.Server.SET_BORDER_WARNING_DELAY_PACKET);
        Play.Server.SET_BORDER_WARNING_DISTANCE_PACKET = new ProtocollerPacketType("SetBorderWarningDistancePacket", State.CLIENTBOUND, (byte) 13); packets.add(Play.Server.SET_BORDER_WARNING_DISTANCE_PACKET);
        Play.Server.SET_SUBTITLE_TEXT_PACKET = new ProtocollerPacketType("SetSubtitleTextPacket", State.CLIENTBOUND, (byte) 14); packets.add(Play.Server.SET_SUBTITLE_TEXT_PACKET);
        Play.Server.SET_TITLE_TEXT_PACKET = new ProtocollerPacketType("SetTitleTextPacket", State.CLIENTBOUND, (byte) 15); packets.add(Play.Server.SET_TITLE_TEXT_PACKET);
        Play.Server.SET_TITLES_ANIMATION_PACKET = new ProtocollerPacketType("SetTitlesAnimationPacket", State.CLIENTBOUND, (byte) 16); packets.add(Play.Server.SET_TITLES_ANIMATION_PACKET);

        Play.Client.POSITION_LOOK = new ProtocollerPacketType("Flying$PacketPlayInPositionLook", State.PLAY_CLIENTBOUND, (byte) 17);  packets.add(Play.Client.POSITION_LOOK);
        Play.Client.POSITION = new ProtocollerPacketType("Flying$PacketPlayInPosition", State.PLAY_CLIENTBOUND, (byte) 18);  packets.add(Play.Client.POSITION);
        Play.Client.LOOK = new ProtocollerPacketType("Flying$PacketPlayInLook", State.PLAY_CLIENTBOUND, (byte) 19);  packets.add(Play.Client.LOOK);
        Play.Server.REL_ENTITY_MOVE = new ProtocollerPacketType("Entity$PacketPlayOutRelEntityMove", State.PLAY_SERVERBOUND, (byte) 20);  packets.add(Play.Server.REL_ENTITY_MOVE);
        Play.Server.ENTITY_LOOK = new ProtocollerPacketType("Entity$PacketPlayOutEntityLook", State.PLAY_SERVERBOUND, (byte) 21);  packets.add(Play.Server.ENTITY_LOOK);
        Play.Server.REL_ENTITY_MOVE_LOOK = new ProtocollerPacketType("Entity$PacketPlayOutRelEntityMoveLook", State.PLAY_SERVERBOUND, (byte) 22);  packets.add(Play.Server.REL_ENTITY_MOVE_LOOK);

        Play.Client.CHAT_ACK_PACKET = new ProtocollerPacketType("ChatAckPacket", State.SERVERBOUND, (byte) 23); packets.add(Play.Client.CHAT_ACK_PACKET);
        Play.Client.CHAT_COMMAND_PACKET = new ProtocollerPacketType("ChatCommandPacket", State.SERVERBOUND, (byte) 24); packets.add(Play.Client.CHAT_COMMAND_PACKET);
        Play.Client.CHAT_SESSION_UPDATE_PACKET = new ProtocollerPacketType("ChatSessionUpdatePacket", State.SERVERBOUND, (byte) 25); packets.add(Play.Client.CHAT_SESSION_UPDATE_PACKET);
        Play.Client.PONG_PACKET = new ProtocollerPacketType("PongPacket", State.SERVERBOUND, (byte) 26); packets.add(Play.Client.PONG_PACKET);

        Play.Server.BLOCK_CHANGED_ACK_PACKET = new ProtocollerPacketType("BlockChangedAckPacket", State.CLIENTBOUND, (byte) 27); packets.add(Play.Server.BLOCK_CHANGED_ACK_PACKET);
        Play.Server.CUSTOM_CHAT_COMPLETIONS = new ProtocollerPacketType("CustomChatCompletions", State.CLIENTBOUND, (byte) 28); packets.add(Play.Server.CUSTOM_CHAT_COMPLETIONS);
        Play.Server.DELETE_CHAT_PACKET = new ProtocollerPacketType("DeleteChatPacket", State.CLIENTBOUND, (byte) 29); packets.add(Play.Server.DELETE_CHAT_PACKET);
        Play.Server.DISGUISED_CHAT_PACKET = new ProtocollerPacketType("DisguisedChatPacket", State.CLIENTBOUND, (byte) 30); packets.add(Play.Server.DISGUISED_CHAT_PACKET);
        Play.Server.LEVEL_CHUNK_PACKET_DATA = new ProtocollerPacketType("LevelChunkPacketData", State.CLIENTBOUND, (byte) 31); packets.add(Play.Server.LEVEL_CHUNK_PACKET_DATA);
        Play.Server.LEVEL_CHUNK_WITH_LIGHT_PACKET = new ProtocollerPacketType("LevelChunkWithLightPacket", State.CLIENTBOUND, (byte) 32); packets.add(Play.Server.LEVEL_CHUNK_WITH_LIGHT_PACKET);
        Play.Server.LIGHT_UPDATE_PACKET_DATA = new ProtocollerPacketType("LightUpdatePacketData", State.CLIENTBOUND, (byte) 33); packets.add(Play.Server.LIGHT_UPDATE_PACKET_DATA);
        Play.Server.PLAYER_CHAT_PACKET = new ProtocollerPacketType("PlayerChatPacket", State.CLIENTBOUND, (byte) 34); packets.add(Play.Server.PLAYER_CHAT_PACKET);
        Play.Server.PLAYER_INFO_REMOVE_PACKET = new ProtocollerPacketType("PlayerInfoRemovePacket", State.CLIENTBOUND, (byte) 35); packets.add(Play.Server.PLAYER_INFO_REMOVE_PACKET);
        Play.Server.PLAYER_INFO_UPDATE_PACKET = new ProtocollerPacketType("PlayerInfoUpdatePacket", State.CLIENTBOUND, (byte) 36); packets.add(Play.Server.PLAYER_INFO_UPDATE_PACKET);
        Play.Server.SERVER_DATA_PACKET = new ProtocollerPacketType("ServerDataPacket", State.CLIENTBOUND, (byte) 37); packets.add(Play.Server.SERVER_DATA_PACKET);
        Play.Server.SET_SIMULATION_DISTANCE_PACKET = new ProtocollerPacketType("SetSimulationDistancePacket", State.CLIENTBOUND, (byte) 38); packets.add(Play.Server.SET_SIMULATION_DISTANCE_PACKET);
        Play.Server.SYSTEM_CHAT_PACKET = new ProtocollerPacketType("SystemChatPacket", State.CLIENTBOUND, (byte) 39); packets.add(Play.Server.SYSTEM_CHAT_PACKET);
        Play.Server.UPDATE_ENABLED_FEATURES = new ProtocollerPacketType("UpdateEnabledFeatures", State.CLIENTBOUND, (byte) 40); packets.add(Play.Server.UPDATE_ENABLED_FEATURES);
        Play.Server.BED = new ProtocollerPacketType("Bed", State.PLAY_SERVERBOUND, (byte) 41); packets.add(Play.Server.BED);
    }
    
}
