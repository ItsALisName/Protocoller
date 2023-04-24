package net.alis.protocoller;

import net.alis.protocoller.packet.Packet;
import net.alis.protocoller.plugin.enums.Version;
import net.alis.protocoller.samples.TitleData;
import net.alis.protocoller.samples.authlib.GameProfile;
import net.alis.protocoller.samples.command.CommandListenerWrapper;
import net.alis.protocoller.samples.core.ChunkCoordIntPair;
import net.alis.protocoller.samples.entity.player.PlayerAbilities;
import net.alis.protocoller.samples.network.PlayerConnection;
import net.alis.protocoller.samples.network.chat.ChatComponent;
import net.alis.protocoller.samples.server.world.level.WorldServer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.UUID;

public interface NetworkPlayer extends CommandSender {

    String getName();

    UUID getUniqueId();

    Version getVersion();

    GameProfile getGameProfile();

    WorldServer getWorld();

    @Nullable CommandListenerWrapper getCommandListenerWrapper();

    void setGameProfile(GameProfile profile);

    PlayerAbilities getPlayerAbilities();

    void setPlayerAbilities(@NotNull PlayerAbilities abilities);

    InetSocketAddress getInetSocketAddress();

    PlayerConnection getConnection();

    int getPacketsSentCount();

    int getPacketsReceivedCount();

    Player getBukkitPlayer();

    int getPing();

    void setPlayerInput(float sidewaysSpeed, float forwardSpeed, boolean jumping, boolean sneaking);

    void restoreFrom(NetworkPlayer player, boolean alive) throws NoSuchMethodException;

    void sendChatMessage(ChatComponent message);

    void sendChatMessageAsync(ChatComponent message);

    void sendActionbarText(ChatComponent text);

    void sendActionbarTextAsync(ChatComponent text);

    void sendTitle(TitleData title);

    void sendTitleAsync(TitleData title);

    void trackChunk(ChunkCoordIntPair pos, Packet packet) throws NoSuchMethodException;

    void untrackChunk(ChunkCoordIntPair pos) throws NoSuchMethodException;

}
