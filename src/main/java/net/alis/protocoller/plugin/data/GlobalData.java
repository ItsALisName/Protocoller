package net.alis.protocoller.plugin.data;

public class GlobalData {

    private final ClientsList clientsList;
    private final PlayersContainer playersContainer;
    private final EntitiesContainer entitiesContainer;

    public GlobalData() {
        this.clientsList = new ClientsList();
        this.playersContainer = new PlayersContainer();
        this.entitiesContainer = new EntitiesContainer();
    }

    public EntitiesContainer getEntitiesContainer() {
        return entitiesContainer;
    }

    public ClientsList getClients() {
        return clientsList;
    }

    public PlayersContainer getPlayersContainer() {
        return playersContainer;
    }
}
