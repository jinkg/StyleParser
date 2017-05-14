package com.yalin.styleparser;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * jinyalin
 * on 2017/5/14.
 */
public class ImageDownloader {
    public static final int DEFAULT_DOWNLOAD_TIMEOUT = 120; // in seconds
    public static final int DEFAULT_READ_TIMEOUT = 30; // in seconds
    public static final int DEFAULT_CONNECT_TIMEOUT = 15; // in seconds

    public static boolean downloadWallpaper(String url, String saveName) {
        OutputStream os = null;
        InputStream is = null;
        try {
            File file = new File("images", saveName);
            os = new FileOutputStream(file);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_DOWNLOAD_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder().url(new URL(url)).build();

            Response response = httpClient.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode >= 200 && responseCode < 300) {
                is = response.body().byteStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) > 0) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
                return true;
            } else {
                System.out.println("Download wallpaper " + saveName + " failed.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Download wallpaper " + saveName + " failed.");
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
