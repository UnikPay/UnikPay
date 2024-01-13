package dk.manaxi.unikpay.api.classes;

import com.google.gson.JsonObject;

public class Pakke {
    private final String name;
    private final String id;
    private final float price;

    public Pakke(String id) {
        this.id = id;
        this.name = id;
        this.price = 0;
    }

    public Pakke(float price, String name, String id) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

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
        return id;
    }

    public JsonObject toJSON() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("id", getId() != null ? getId() : getName());
        jsonObject.addProperty("price", getPrice());
        return jsonObject;
    }
}
