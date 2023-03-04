package net.alis.protocoller.bukkit.data;

public class GlobalData {

    private final UsersContainer usersContainer;
    private final PlayersContainer playersContainer;
    private final EntitiesContainer entitiesContainer;

    public GlobalData() {
        this.usersContainer = new UsersContainer();
        this.playersContainer = new PlayersContainer();
        this.entitiesContainer = new EntitiesContainer();
    }

    public EntitiesContainer getEntitiesContainer() {
        return entitiesContainer;
    }

    public UsersContainer getUsersContainer() {
        return usersContainer;
    }

    public PlayersContainer getPlayersContainer() {
        return playersContainer;
    }
}
