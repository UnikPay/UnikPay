package dk.manaxi.unikpay.api.classes;

import com.google.gson.annotations.JsonAdapter;
import dk.manaxi.unikpay.api.UnixTimestampAdapter;

import java.util.Date;

public class Subscription {
    private String _id;
    private String server;
    private Number duration;
    private String durationType;
    private String nextPayment;
    private Mcaccount mcaccount;
    private Pakke pakke;
    private float amount;

    public Subscription(String id) {
        _id = id;
    }

    public String get_id() {
        return _id;
    }

    public Number getDuration() {
        return duration;
    }

    public String getDurationType() {
        return durationType;
    }

    public Date getNextPayment() {
        return new Date(Long.parseLong(nextPayment));
    }

    public Mcaccount getMcaccount() {
        return mcaccount;
    }

    public Pakke getPakke() {
        return pakke;
    }

    public String toString() {
        return _id;
    }
}
