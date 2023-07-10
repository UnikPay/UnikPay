package dk.manaxi.unikpay.plugin.skript.classes;

public class AcceptId {
    private final String id;

    public AcceptId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
