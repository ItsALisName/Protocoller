package net.alis.protocoller.bukkit.network.packet;

public class IndexedParam<O, I> {

    private final O object;
    private final I index;

    public IndexedParam(O object, I index) {
        this.object = object;
        this.index = index;
    }

    public short getIndex() {
        return (short) index;
    }

    public Object getObject() {
        return object;
    }
}
