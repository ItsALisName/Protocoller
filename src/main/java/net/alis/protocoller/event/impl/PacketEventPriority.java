package net.alis.protocoller.event.impl;

public enum PacketEventPriority {

    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4),
    MONITOR(5);

    private final int id;

    public final int getId() {
        return this.id;
    }

    PacketEventPriority(int id) {
        this.id = id;
    }

}
