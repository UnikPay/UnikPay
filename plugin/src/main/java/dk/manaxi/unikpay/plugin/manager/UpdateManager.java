package dk.manaxi.unikpay.plugin.manager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class UpdateManager {
    public static String ApiUrl = "https://api.github.com/repos/UnikPay/UnikPay/releases/latest";

    public static Boolean isNewestVersionAvailable(String currentVersion) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ApiUrl)
                    .header("User-Agent", "Mozilla/5.0")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            String latestVersion = json.getString("tag_name");

            return latestVersion.equals(currentVersion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
