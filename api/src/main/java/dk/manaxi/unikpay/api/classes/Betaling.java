package dk.manaxi.unikpay.api.classes;

public class Betaling {

    private String _id;

    private String server;

    private Mcaccount mcaccount;

    private Pakke[] packages;

    private float amount;

    public String get_id() {
        return this._id;
    }

    public Mcaccount getMcaccount() {
        return this.mcaccount;
    }

    public Pakke[] getPackages() {
        return this.packages;
    }

    public float getAmount() {
        return this.amount;
    }
}
