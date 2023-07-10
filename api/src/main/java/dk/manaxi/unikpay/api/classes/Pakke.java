package dk.manaxi.unikpay.api.classes;

public class Pakke {
    private String name;
    private String id;
    private float price;

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
        return id.toString();
    }
}
