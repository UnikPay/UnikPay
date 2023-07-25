package dk.manaxi.unikpay.plugin.manager;

public class Semver {
    private final Integer major;
    private final Integer minor;
    private final Integer patch;

    public Semver(String semver) {
        this.major = Integer.valueOf(semver.split("\\.")[0]);
        this.minor = Integer.valueOf(semver.split("\\.")[1]);
        this.patch = Integer.valueOf(semver.split("\\.")[2].split("-")[0]);
    }

    public boolean isGreaterThan(String version) {
        return this.isGreaterThan(new Semver(version));
    }

    public boolean isGreaterThan(Semver version) {
        if (this.getMajor() > version.getMajor()) return true;
        else if (this.getMajor() < version.getMajor()) return false;

        if(this.getMinor() > version.getMinor()) return true;
        else if (this.getMinor() < version.getMinor()) return false;

        if(this.getPatch() > version.getPatch()) return true;
        else if (this.getPatch() < version.getPatch()) return false;

        return false;
    }

    public Integer getMajor() {
        return major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Integer getPatch() {
        return patch;
    }
}
