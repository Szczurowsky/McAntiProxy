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

    public static String readSourceCode(URLConnection conn) throws IOException {
        InputStream is = conn.getInputStream();
        InputStreamReader re = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(re);

        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }

}
