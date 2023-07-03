package dk.manaxi.unikpay.api.classes;

import java.lang.reflect.Array;

public class Betaling {

    private String spiller;

    private String server;

    private String uuid;

    private Array pakke;

    private float amount;

    public String getUuid() {
        return this.uuid;
    }

    public Array getPakke() {
        return this.pakke;
    }

    public float getAmount() {
        return this.amount;
    }
}
