package net.alis.protocoller.packet;

public abstract class PacketType {

    public abstract static class Play {

        public abstract static class Client {
            public static MinecraftPacketType ABILITIES;
            public static MinecraftPacketType ADVANCEMENTS;
            public static MinecraftPacketType ARM_ANIMATION;
            public static MinecraftPacketType AUTO_RECIPE;
            public static MinecraftPacketType BEDIT;
            public static MinecraftPacketType BEACON;
            public static MinecraftPacketType BLOCK_DIG;
            public static MinecraftPacketType BLOCK_PLACE;
            public static MinecraftPacketType BOAT_MOVE;
            public static MinecraftPacketType CHAT;
            public static MinecraftPacketType CLIENT_COMMAND;
            public static MinecraftPacketType CLOSE_WINDOW;
            public static MinecraftPacketType CUSTOM_PAYLOAD;
            public static MinecraftPacketType DIFFICULTY_CHANGE;
            public static MinecraftPacketType DIFFICULTY_LOCK;
            public static MinecraftPacketType ENCHANT_ITEM;
            public static MinecraftPacketType ENTITY_ACTION;
            public static MinecraftPacketType ENTITY_NBT_QUERY;
            public static MinecraftPacketType FLYING;
            public static MinecraftPacketType HELD_ITEM_SLOT;
            public static MinecraftPacketType ITEM_NAME;
            public static MinecraftPacketType JIGSAW_GENERATE;
            public static MinecraftPacketType KEEP_ALIVE;
            public static MinecraftPacketType PICK_ITEM;
            public static MinecraftPacketType RECIPE_DISPLAYED;
            public static MinecraftPacketType RECIPE_SETTINGS;
            public static MinecraftPacketType RESOURCEPACK_STATUS;
            public static MinecraftPacketType SET_COMMAND_BLOCK;
            public static MinecraftPacketType SET_COMMAND_MINECART;
            public static MinecraftPacketType SET_CREATIVE_SLOT;
            public static MinecraftPacketType SET_JIGSAW;
            public static MinecraftPacketType SETTINGS;
            public static MinecraftPacketType SPECTATE;
            public static MinecraftPacketType STEER_VEHICLE;
            public static MinecraftPacketType STRUCT;
            public static MinecraftPacketType TAB_COMPLETE;
            public static MinecraftPacketType TELEPORT_ACCEPT;
            public static MinecraftPacketType TILE_NBT_QUERY;
            public static MinecraftPacketType TR_SEL;
            public static MinecraftPacketType UPDATE_SIGN;
            public static MinecraftPacketType USE_ENTITY;
            public static MinecraftPacketType USE_ITEM;
            public static MinecraftPacketType VEHICLE_MOVE;
            public static MinecraftPacketType WINDOW_CLICK;
            public static MinecraftPacketType POSITION_LOOK;
            public static MinecraftPacketType POSITION;
            public static MinecraftPacketType LOOK;
            public static MinecraftPacketType CHAT_ACK_PACKET;
            public static MinecraftPacketType CHAT_COMMAND_PACKET;
            public static MinecraftPacketType CHAT_SESSION_UPDATE_PACKET;
            public static MinecraftPacketType PONG_PACKET;

        }

        public abstract static class Server {
            public static MinecraftPacketType BED;
            public static MinecraftPacketType ABILITIES;
            public static MinecraftPacketType ADVANCEMENTS;
            public static MinecraftPacketType ANIMATION;
            public static MinecraftPacketType ATTACH_ENTITY;
            public static MinecraftPacketType AUTO_RECIPE;
            public static MinecraftPacketType BLOCK_ACTION;
            public static MinecraftPacketType BLOCK_BREAK;
            public static MinecraftPacketType BLOCK_BREAK_ANIMATION;
            public static MinecraftPacketType BLOCK_CHANGE;
            public static MinecraftPacketType BOSS;
            public static MinecraftPacketType REL_ENTITY_MOVE;
            public static MinecraftPacketType CAMERA;
            public static MinecraftPacketType CHAT;
            public static MinecraftPacketType CLOSE_WINDOW;
            public static MinecraftPacketType COLLECT;
            public static MinecraftPacketType COMMANDS;
            public static MinecraftPacketType CUSTOM_PAYLOAD;
            public static MinecraftPacketType CUSTOM_SOUND_EFFECT;
            public static MinecraftPacketType ENTITY;
            public static MinecraftPacketType ENTITY_DESTROY;
            public static MinecraftPacketType ENTITY_EFFECT;
            public static MinecraftPacketType ENTITY_LOOK;
            public static MinecraftPacketType REL_ENTITY_MOVE_LOOK;
            public static MinecraftPacketType ENTITY_EQUIPMENT;
            public static MinecraftPacketType ENTITY_HEAD_ROTATION;
            public static MinecraftPacketType ENTITY_METADATA;
            public static MinecraftPacketType ENTITY_SOUND;
            public static MinecraftPacketType ENTITY_STATUS;
            public static MinecraftPacketType ENTITY_TELEPORT;
            public static MinecraftPacketType ENTITY_VELOCITY;
            public static MinecraftPacketType EXPERIENCE;
            public static MinecraftPacketType EXPLOSION;
            public static MinecraftPacketType GAME_STATE_CHANGE;
            public static MinecraftPacketType HELD_ITEM_SLOT;
            public static MinecraftPacketType KEEP_ALIVE;
            public static MinecraftPacketType KICK_DISCONNECT;
            public static MinecraftPacketType LIGHT_UPDATE;
            public static MinecraftPacketType LOGIN;
            public static MinecraftPacketType LOOK_AT;
            public static MinecraftPacketType MAP;
            public static MinecraftPacketType MAP_CHUNK;
            public static MinecraftPacketType MOUNT;
            public static MinecraftPacketType MULTI_BLOCK_CHANGE;
            public static MinecraftPacketType NBT_QUERY;
            public static MinecraftPacketType NAMED_ENTITY_SPAWN;
            public static MinecraftPacketType NAMED_SOUND_EFFECT;
            public static MinecraftPacketType OPEN_BOOK;
            public static MinecraftPacketType OPEN_SIGN_EDITOR;
            public static MinecraftPacketType OPEN_WINDOW;
            public static MinecraftPacketType OPEN_WINDOW_HORSE;
            public static MinecraftPacketType OPEN_WINDOW_MERCHANT;
            public static MinecraftPacketType PLAYER_INFO;
            public static MinecraftPacketType PLAYER_LIST_HEADER_FOOTER;
            public static MinecraftPacketType POSITION;
            public static MinecraftPacketType RECIPE_UPDATE;
            public static MinecraftPacketType RECIPES;
            public static MinecraftPacketType REMOVE_ENTITY_EFFECT;
            public static MinecraftPacketType RESOURCE_PACK_SEND;
            public static MinecraftPacketType RESPAWN;
            public static MinecraftPacketType SCOREBOARD_DISPLAY_OBJECTIVE;
            public static MinecraftPacketType SCOREBOARD_OBJECTIVE;
            public static MinecraftPacketType SCOREBOARD_SCORE;
            public static MinecraftPacketType SCOREBOARD_TEAM;
            public static MinecraftPacketType SELECT_ADVANCEMENT_TAB;
            public static MinecraftPacketType SERVER_DIFFICULTY;
            public static MinecraftPacketType SET_COOLDOWN;
            public static MinecraftPacketType SET_SLOT;
            public static MinecraftPacketType SPAWN_ENTITY;
            public static MinecraftPacketType SPAWN_ENTITY_EXPERIENCE_ORB;
            public static MinecraftPacketType SPAWN_ENTITY_LIVING;
            public static MinecraftPacketType SPAWN_ENTITY_PAINTING;
            public static MinecraftPacketType SPAWN_POSITION;
            public static MinecraftPacketType STATISTIC;
            public static MinecraftPacketType STOP_SOUND;
            public static MinecraftPacketType TAB_COMPLETE;
            public static MinecraftPacketType TAGS;
            public static MinecraftPacketType TILE_ENTITY_DATA;
            public static MinecraftPacketType UNLOAD_CHUNK;
            public static MinecraftPacketType UPDATE_ATTRIBUTES;
            public static MinecraftPacketType UPDATE_HEALTH;
            public static MinecraftPacketType UPDATE_TIME;
            public static MinecraftPacketType VEHICLE_MOVE;
            public static MinecraftPacketType VIEW_CENTRE;
            public static MinecraftPacketType VIEW_DISTANCE;
            public static MinecraftPacketType WINDOW_DATA;
            public static MinecraftPacketType WINDOW_ITEMS;
            public static MinecraftPacketType WORLD_EVENT;
            public static MinecraftPacketType WORLD_PARTICLES;
            public static MinecraftPacketType INITIALIZE_BORDER_PACKET;
            public static MinecraftPacketType ADD_VIBRATION_SIGNAL_PACKET;
            public static MinecraftPacketType CLEAR_TITLES_PACKET;
            public static MinecraftPacketType PING_PACKET;
            public static MinecraftPacketType PLAYER_COMBAT_END_PACKET;
            public static MinecraftPacketType PLAYER_COMBAT_ENTER_PACKET;
            public static MinecraftPacketType PLAYER_COMBAT_KILL_PACKET;
            public static MinecraftPacketType SET_ACTIONBAR_TEXT_PACKET;
            public static MinecraftPacketType SET_BORDER_CENTER_PACKET;
            public static MinecraftPacketType SET_BORDER_LERP_SIZE_PACKET;
            public static MinecraftPacketType SET_BORDER_SIZE_PACKET;
            public static MinecraftPacketType SET_BORDER_WARNING_DELAY_PACKET;
            public static MinecraftPacketType SET_BORDER_WARNING_DISTANCE_PACKET;
            public static MinecraftPacketType SET_SUBTITLE_TEXT_PACKET;
            public static MinecraftPacketType SET_TITLE_TEXT_PACKET;
            public static MinecraftPacketType SET_TITLES_ANIMATION_PACKET;
            public static MinecraftPacketType BLOCK_CHANGED_ACK_PACKET;
            public static MinecraftPacketType CUSTOM_CHAT_COMPLETIONS;
            public static MinecraftPacketType DELETE_CHAT_PACKET;
            public static MinecraftPacketType DISGUISED_CHAT_PACKET;
            public static MinecraftPacketType LEVEL_CHUNK_PACKET_DATA;
            public static MinecraftPacketType LEVEL_CHUNK_WITH_LIGHT_PACKET;
            public static MinecraftPacketType LIGHT_UPDATE_PACKET_DATA;
            public static MinecraftPacketType PLAYER_CHAT_PACKET;
            public static MinecraftPacketType PLAYER_INFO_REMOVE_PACKET;
            public static MinecraftPacketType PLAYER_INFO_UPDATE_PACKET;
            public static MinecraftPacketType SERVER_DATA_PACKET;
            public static MinecraftPacketType SET_SIMULATION_DISTANCE_PACKET;
            public static MinecraftPacketType SYSTEM_CHAT_PACKET;
            public static MinecraftPacketType UPDATE_ENABLED_FEATURES;
        }

    }

    public abstract static class Handshake {

        public abstract static class Client {
            public static MinecraftPacketType SET_PROTOCOL;
        }

        public abstract static class Server {
            //...
        }

    }

    public abstract static class Login {

        public abstract static class Client {
            public static MinecraftPacketType CUSTOM_PAYLOAD;
            public static MinecraftPacketType ENCRYPTION_BEGIN;
            public static MinecraftPacketType START;
        }

        public abstract static class Server {
            public static MinecraftPacketType DISCONNECT;
            public static MinecraftPacketType CUSTOM_PAYLOAD;
            public static MinecraftPacketType ENCRYPTION_BEGIN;
            public static MinecraftPacketType SET_COMPRESSION;
            public static MinecraftPacketType SUCCESS;
        }

    }

    public abstract static class Status {

        public abstract static class Client {
            public static MinecraftPacketType PING;
            public static MinecraftPacketType START;
        }

        public abstract static class Server {
            public static MinecraftPacketType PONG;
            public static MinecraftPacketType SERVER_INFO;
        }

    }

    public enum State {
        PLAY_CLIENTBOUND,
        PLAY_SERVERBOUND,
        LOGIN_CLIENTBOUND,
        LOGIN_SERVERBOUND,
        HANDSHAKE_CLIENTBOUND,
        HANDSHAKE_SERVERBOUND,
        STATUS_CLIENTBOUND,
        STATUS_SERVERBOUND,
        CLIENTBOUND,
        SERVERBOUND
    }

}

