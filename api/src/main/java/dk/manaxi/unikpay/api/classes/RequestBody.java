package dk.manaxi.unikpay.api.classes;

public class RequestBody {
    private CustomPackage[] pakker;
    private String uuid;

    public CustomPackage[] getPakker() {
        return pakker;
    }

    public void setPakker(CustomPackage[] pakker) {
        this.pakker = pakker;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
