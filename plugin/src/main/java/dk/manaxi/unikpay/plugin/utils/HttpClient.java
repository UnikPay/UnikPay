package dk.manaxi.unikpay.plugin.utils;


import dk.manaxi.unikpay.plugin.Main;
import dk.manaxi.unikpay.plugin.configuration.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpClient {

    /**
     * Udfører en HTTP POST-anmodning til den angivne URL med det medsendte objekt.
     *
     * @param url  URL'en, som POST-anmodningen skal sendes til
     * @param obj  Objektet, der skal sendes som payload i anmodningen
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String post(String url, String obj) {
        return sendRequest(url, "POST", obj);
    }

    /**
     * Udfører en HTTP GET-anmodning til den angivne URL.
     *
     * @param url  URL'en, som GET-anmodningen skal sendes til
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String get(String url) {
        return sendRequest(url, "GET", null);
    }

    /**
     * Udfører en HTTP DELETE-anmodning til den angivne URL.
     *
     * @param url  URL'en, som DELETE-anmodningen skal sendes til
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String delete(String url) {
        return sendRequest(url, "DELETE", null);
    }
    /**
     * Udfører en HTTP PUT-anmodning til den angivne URL med det medsendte objekt.
     *
     * @param url  URL'en, som PUT-anmodningen skal sendes til
     * @param obj  Objektet, der skal sendes som payload i anmodningen
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String put(String url, String obj) {
        return sendRequest(url, "PUT", obj);
    }
    /**
     * Sender en HTTP-anmodning til den angivne URL med den angivne metode og det medsendte objekt.
     *
     * @param url     URL'en, som anmodningen skal sendes til
     * @param method  HTTP-metoden, f.eks. "GET", "POST", "PUT" eller "DELETE"
     * @param obj     Objektet, der skal sendes som payload i anmodningen
     * @return        Svar fra serveren som en streng, eller null ved fejl
     */
    private static String sendRequest(String url, String method, String obj) {
        if (Main.getAPIKEY() == null) {
            Main.log.sendMessage(ColorUtils.getColored("&cDu mangler at putte din apikey ind i config.yml"));
            Config.sendToPerms("apikey-missing", Main.configYML.getString("admin-permission"));
            return null;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Authorization", Main.getAPIKEY());
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            if (obj != null) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(obj.getBytes());
                }
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                System.out.println(method + " request failed. Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
