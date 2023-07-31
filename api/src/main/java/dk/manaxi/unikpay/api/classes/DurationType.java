package dk.manaxi.unikpay.api.classes;

public enum DurationType {
    MINUT("m"),
    HOUR("h"),
    DAY("d"),
    WEEK("w"),
    MONTH("M"),
    ;

    public final String shortName;

    DurationType(String shortName) {
        this.shortName = shortName;
    }
}