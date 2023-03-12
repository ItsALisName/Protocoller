package net.alis.protocoller.samples.network;

public enum ProtocolDirection {

    SERVERBOUND(0), CLIENTBOUND(1);

    private final int id;

    ProtocolDirection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ProtocolDirection reverse() {
        return this == CLIENTBOUND ? SERVERBOUND : CLIENTBOUND;
    }

    public static ProtocolDirection getById(int id) {
        for(ProtocolDirection s : ProtocolDirection.values())
            if(s.id == id) return s;
        return null;
    }
}
