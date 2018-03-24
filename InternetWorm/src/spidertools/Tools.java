package spidertools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Tools {
    public static String getData(String strUrl) throws IOException {
        URL url = new URL(strUrl);
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream(), "UTF-8"
                )
        );

        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        connection.disconnect();
        return builder.toString();

    }

    public static void main(String[] args) {

    }
}
