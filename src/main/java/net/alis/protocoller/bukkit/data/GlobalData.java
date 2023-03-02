package net.alis.protocoller.bukkit.data;

public class GlobalData {

    private final UsersContainer usersContainer;
    private final PlayersContainer playersContainer;

    public GlobalData() {
        this.usersContainer = new UsersContainer();
        this.playersContainer = new PlayersContainer();
    }

    public UsersContainer getUsersContainer() {
        return usersContainer;
    }

    public PlayersContainer getPlayersContainer() {
        return playersContainer;
    }
}
