package dk.manaxi.unikpay.api.classes;

import java.lang.reflect.Array;

public class Pakke {
    private Array pakke;

    public Pakke(Array pakke) {
        this.pakke = pakke;
    }

    public Array getPakke() {
        return this.pakke;
    }
}
