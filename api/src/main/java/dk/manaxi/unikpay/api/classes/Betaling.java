package dk.manaxi.unikpay.api.classes;

public class Betaling {

    private String _id;

    private String server;

    private Mcaccount mcaccount;

    private Package[] packages;

    private float amount;

    private Subscription subscription;

    public String get_id() {
        return this._id;
    }

    public Mcaccount getMcaccount() {
        return this.mcaccount;
    }

    public Package[] getPackages() {
        return this.packages;
    }

    public float getAmount() {
        return this.amount;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
