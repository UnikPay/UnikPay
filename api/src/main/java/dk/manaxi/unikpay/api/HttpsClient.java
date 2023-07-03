package dk.manaxi.unikpay.api;

import okhttp3.*;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class HttpsClient {
    public static String sendRequest(String url, String method, String obj, String auth) {
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = null;
            if (obj != null) {
                MediaType mediaType = MediaType.parse("application/json");
                requestBody = RequestBody.create(mediaType, obj);
            }

            Request request = new Request.Builder()
                    .url(url)
                    .method(method, requestBody)
                    .header("Authorization", auth)
                    .build();

            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            System.out.println("Code: " + responseCode);

            String responseBody = response.body().string();
            System.out.println("Response: " + responseBody);

            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
