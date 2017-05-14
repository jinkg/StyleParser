package com.yalin.styleparser;

import java.io.IOException;

/**
 * jinyalin
 * on 2017/5/14.
 */
public class ResponseParser {
    private static final int TITLE_LENGTH = 48;
    private static final int FILE_NAME_LENGTH = 48;
    private static final int AUTHOR_LENGTH = 48;

    public static void parseResponse(String response) throws IOException {
        if (response == null || response.length() == 0) {
            throw new IllegalArgumentException("Response is NULL.");
        }

        String titleText = "<title>";
        String titleClosure = "</title>";
        int titleStart = response.indexOf(titleText) + titleText.length();
        int titleEnd = response.indexOf(titleClosure);
        String title = response.substring(titleStart, titleEnd);
        String[] detail = parseTitle(title);
        title = detail[0];
        String author = detail[1];
        System.out.println("Title: " + title + " Author: " + author);

        String imageText = "<meta property=\"og:image\"";
        int imageStart = response.indexOf(imageText);
        imageText = response.substring(imageStart);
        imageText = imageText.substring(0, imageText.indexOf("/>"));
        System.out.println("ImageText: " + imageText);

        String imageContent = "content=";
        int imageUrlStart = imageText.indexOf(imageContent) + imageContent.length();
        String imageUrl = parseImageUrl(imageText.substring(imageUrlStart));
        System.out.println("ImageUrl: " + imageUrl);

        String imageSaveName = getImageSaveName(imageUrl);
        System.out.println("ImageSaveName: " + imageSaveName);

        String wallpaperStr = getWallpaperString(correction(imageSaveName, FILE_NAME_LENGTH),
                correction(title, TITLE_LENGTH), correction(author, AUTHOR_LENGTH), "kinglloy.com");
        System.out.println(wallpaperStr);

        boolean downloadSuccess = ImageDownloader.downloadWallpaper(imageUrl, imageSaveName);
        if (downloadSuccess) {
            System.out.println("Download success.");
        } else {
            System.out.println("Download failed.");
        }
    }

    private static String[] parseTitle(String titleAll) {
        String[] result = new String[2];
        if (titleAll.contains(",")) {
            String[] titles = titleAll.split(",");
            result[0] = titles[0].trim();

            String[] detail = titles[1].trim().split("-");
            String date;
            String author;
            if (detail.length == 4) {
                date = detail[0].trim() + "-" + detail[1].trim();
                author = detail[2].trim();
            } else {
                date = detail[0].trim();
                author = detail[1].trim();
            }
            author = author + ", " + date;
            result[1] = author;
        } else {
            String[] titles = titleAll.split("-");
            result[0] = titles[0].trim();
            result[1] = titles[1].trim();
        }
        return result;
    }

    private static String parseImageUrl(String imageUrl) {
        imageUrl = imageUrl.trim();
        int quoteStart = imageUrl.indexOf("\"");
        if (quoteStart < 0) {
            return imageUrl;
        }
        int quoteEnd = imageUrl.lastIndexOf("\"");
        return imageUrl.substring(quoteStart + "\"".length(), quoteEnd);
    }

    private static String getImageSaveName(String imageUrl) {
        int nameStart = imageUrl.lastIndexOf("/") + "/".length();
        String imageName = imageUrl.substring(nameStart);

        int nameEnd = imageName.indexOf("!");
        if (nameEnd > 0) {
            imageName = imageName.substring(0, nameEnd);
        }
        return imageName;
    }

    private static String getWallpaperString(String imageName, String imageTitle, String imageAuthor, String source) {
        StringBuilder sb = new StringBuilder();
        sb.append(imageName)
                .append("|")
                .append(imageTitle)
                .append("|")
                .append(imageAuthor)
                .append("|")
                .append(source);
        return sb.toString();
    }

    private static String correction(String str, int length) {
        if (str.length() > length) {
            str = str.substring(0, length);
        } else if (str.length() < length) {
            for (int i = str.length(); i < length; i++) {
                str += " ";
            }
        }
        return str;
    }
}
