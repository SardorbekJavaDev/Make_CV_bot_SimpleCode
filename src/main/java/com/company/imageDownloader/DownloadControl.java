package com.company.imageDownloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DownloadControl {

    public static void getImage(String filePath, String name) {
        try {
            URL url = new URL("https://api.telegram.org/file/bot5188722391:AAFuO-Pvm15g666JoNUFgIgZd8Z8ACFEL-E/" + filePath);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            OutputStream outputStream = null;
            File file = new File("src/main/UserImg/" + name + ".jpg");
            System.out.println(file);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNewLink(String fileID) {
        String line = null;
        try {
            URL url = new URL("https://api.telegram.org/bot5188722391:AAFuO-Pvm15g666JoNUFgIgZd8Z8ACFEL-E/getFile?file_id="+fileID);
            URLConnection urlConnection = url.openConnection();
            InputStream stream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            line = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(line);
        return line;
    }
}
