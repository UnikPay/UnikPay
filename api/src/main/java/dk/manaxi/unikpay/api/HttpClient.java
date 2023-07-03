package dk.manaxi.unikpay.api;

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
     * @param auth api-keyen til at tjekke om det er den rigtige server
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String post(String url, String obj, String auth) {
        return sendRequest(url, "POST", obj, auth);
    }

    /**
     * Udfører en HTTP GET-anmodning til den angivne URL.
     *
     * @param url  URL'en, som GET-anmodningen skal sendes til
     * @return     Svar fra serveren som en streng, eller null ved fejl
     * @param auth api-keyen til at tjekke om det er den rigtige server
     */
    public static String get(String url, String auth) {
        return sendRequest(url, "GET", null, auth);
    }

    /**
     * Udfører en HTTP DELETE-anmodning til den angivne URL.
     *
     * @param url  URL'en, som DELETE-anmodningen skal sendes til
     * @param auth api-keyen til at tjekke om det er den rigtige server
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String delete(String url, String auth) {
        return sendRequest(url, "DELETE", null, auth);
    }
    /**
     * Udfører en HTTP PUT-anmodning til den angivne URL med det medsendte objekt.
     *
     * @param url  URL'en, som PUT-anmodningen skal sendes til
     * @param obj  Objektet, der skal sendes som payload i anmodningen
     * @param auth api-keyen til at tjekke om det er den rigtige server
     * @return     Svar fra serveren som en streng, eller null ved fejl
     */
    public static String put(String url, String obj, String auth) {
        return sendRequest(url, "PUT", obj, auth);
    }
    /**
     * Sender en HTTP-anmodning til den angivne URL med den angivne metode og det medsendte objekt.
     *
     * @param url     URL'en, som anmodningen skal sendes til
     * @param method  HTTP-metoden, f.eks. "GET", "POST", "PUT" eller "DELETE"
     * @param obj     Objektet, der skal sendes som payload i anmodningen
     * @param auth api-keyen til at tjekke om det er den rigtige server
     * @return        Svar fra serveren som en streng, eller null ved fejl
     */
    private static String sendRequest(String url, String method, String obj, String auth) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", auth);
            //connection.setDoOutput(true);

            if (obj != null) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(obj.getBytes());
                }
            }

            int responseCode = connection.getResponseCode();
            String message = connection.getResponseMessage();
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
                System.out.println(method + " request failed. Response Message: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
