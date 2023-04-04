package net.alis.protocoller.plugin.network.packet;

public class IndexedParam<O, I extends Integer> {

    private final O object;
    private final I index;

    public IndexedParam(O object, I index) {
        this.object = object;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Object getObject() {
        return object;
    }
}
