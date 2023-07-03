package dk.manaxi.unikpay.api.classes;

import java.lang.reflect.Array;

public class Betaling {

    private String _id;

    private String server;

    private Mcaccount mcaccount;

    private Array pakke;

    private float amount;

    public String get_id() {
        return this._id;
    }

    public Mcaccount getMcaccount() {
        return this.mcaccount;
    }

    public Array getPakke() {
        return this.pakke;
    }

    public float getAmount() {
        return this.amount;
    }
}
