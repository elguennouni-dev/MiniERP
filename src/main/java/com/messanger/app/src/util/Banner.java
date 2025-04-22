package com.messanger.app.src.util;

import java.io.FileInputStream;

public class Banner {
    private static String file;
    private static FileInputStream inputStream = null;


    public static void loadBanner() throws Exception {
        file = "src/main/resources/banner.txt";
        inputStream = new FileInputStream(file);
        int fileSize = inputStream.available();
        byte[] buffer = new byte[fileSize];
        inputStream.read(buffer);
        String banner = new String(buffer);
        System.out.println(banner);
    }

}
