package dk.manaxi.unikpay.plugin.enums;

public enum Hook {
    SKRIPT(false);

    private final boolean isBuiltIn;

    Hook(boolean paramBoolean) {
        this.isBuiltIn = paramBoolean;
    }

    public boolean isBuiltIn() {
        return this.isBuiltIn;
    }
}
