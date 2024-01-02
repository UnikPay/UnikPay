package dk.manaxi.unikpay.api;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public class HttpsClient {
    private static final OkHttpClient client = new OkHttpClient();
    public static String sendRequest(String url, String method, String obj, String auth, Map<String, String> params) {
        try {

            RequestBody requestBody = null;
            if (obj != null) {
                MediaType mediaType = MediaType.parse("application/json");
                requestBody = RequestBody.create(obj, mediaType);
            }

            // Create a new HttpUrl.Builder to modify the request URL if there are parameters
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

            // Add parameters to the URL builder
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    urlBuilder.addQueryParameter(key, value);
                }
            }

            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .method(method, requestBody)
                    .header("Authorization", auth)
                    .build();

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();
            Future<Response> future = executor.submit(callable);
            Response response = future.get();
            int responseCode = response.code();

            assert response.body() != null;

            return response.body().string();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRequest(String url, String auth) {
        try {
            Request request = new Request.Builder()
                    .url(url).get()
                    .header("Authorization", auth)
                    .build();

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<Response> callable = () -> client.newCall(request).execute();
            Future<Response> future = executor.submit(callable);
            Response response = future.get();
            int responseCode = response.code();

            assert response.body() != null;
            return response.body().string();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
