package dk.manaxi.unikpay.plugin.manager;

import dk.manaxi.unikpay.plugin.Main;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateManager {

    public static final String ApiUrl = "https://api.github.com/repos/UnikPay/UnikPay/releases/latest";
    public static final String DownloadUrl = "https://github.com/UnikPay/UnikPay/releases/latest/download/UnikPay.jar";

    public static void Update() throws IOException {
        downloadAndExtractFile(new URL(DownloadUrl), Main.getInstance().getFile().getName(), Main.getInstance().getDataFolder().getParentFile().toPath());
        Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        Bukkit.getPluginManager().enablePlugin(Main.getInstance());
    }

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

    private static void downloadAndExtractFile(URL url, String fileName, Path outputDirectory) throws IOException {

        Path outputFile = outputDirectory.resolve(fileName);


        if (Files.isDirectory(outputFile)) {
            throw new IllegalArgumentException("Output file is a directory: " + outputFile);
        }

        /*
        If the file exist, it deletes the file
         */
        if (Files.exists(outputFile)) {
            Files.delete(outputFile);
        }

        try (InputStream in = new BufferedInputStream(url.openStream());
             OutputStream out = Files.newOutputStream(outputFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }


}
