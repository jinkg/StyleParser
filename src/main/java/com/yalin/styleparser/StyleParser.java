package com.yalin.styleparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * jinyalin
 * on 2017/5/14.
 */
public class StyleParser {
    public static void main(String[] args) {
        assert args.length == 1;
        try {
            String response = getResponse(args[0]);
            ResponseParser.parseResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getResponse(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        if (urlConnection.getResponseCode() == 200) {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                sb.append(decodedString)
                        .append("\n");
            }
            in.close();
            return sb.toString();
        }
        return "";
    }
}
