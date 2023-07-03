package dk.manaxi.unikpay.api.classes;

import java.lang.reflect.Array;

public class Pakke {
    private String name;
    private String id;
    private float price;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
