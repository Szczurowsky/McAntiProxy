package pl.szczurowsky.mcantiproxy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public final class HttpUtil {

    private HttpUtil() {

    }

    public static HttpURLConnection createConnection(String request) throws IOException {
        return (HttpURLConnection) new URL(request).openConnection();
    }

    public static HttpURLConnection prepareConnection(HttpURLConnection connection) {
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "PremiumChecker");
        return connection;
    }

    public static String readSourceCode(URLConnection connection) throws IOException {
        try (InputStream inputStream = connection.getInputStream();
             BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();

            String inputLine;
            while ((inputLine = inputStreamReader.readLine()) != null)
                builder.append(inputLine);

            return builder.toString();
        }
    }

}
