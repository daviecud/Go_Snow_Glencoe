package com.example.gosnow_glencoe.SnowReport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpCall  {

    public static String executeGet(String url) {
        URL jsonUrl;
        HttpURLConnection connection = null;

        try {
            jsonUrl = new URL(url);
            connection = (HttpURLConnection) jsonUrl.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputS;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                inputS = connection.getErrorStream();
            else
                inputS = connection.getInputStream();

            BufferedReader buffRead = new BufferedReader(new InputStreamReader(inputS));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = buffRead.readLine())!= null) {
                response.append(line);
                response.append('\r');
            }
            buffRead.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
