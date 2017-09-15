package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPManager {
    public String getIPFromServer(String connection_id) {
        //String urlToRead = "http://users.iit.demokritos.gr/~ggianna/ICStudy/ip.txt";
        String urlToRead = "http://icstudy.projects.development1.scify.org/www/ICStudy-server/public/api/getclientip?connection_id=" + connection_id;
        System.out.println(urlToRead);
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
