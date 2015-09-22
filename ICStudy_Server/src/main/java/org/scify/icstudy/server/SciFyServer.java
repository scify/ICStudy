package org.scify.icstudy.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by pisaris on 11/9/2015.
 */
public class SciFyServer {

    Process pr;

    public static void main() {
    }

    public SciFyServer() {
    }

    public void startServer() {
        Runtime rt = Runtime.getRuntime();
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        //SciFyServer sciFyServer = new SciFyServer();
        String ip = sendRequest();
        System.out.println(ip);

        try {

            if(new String("Linux").equals(osName) ) {
                System.out.println("running linux");
                this.pr = rt.exec("ffmpeg -s 1024x768 -f x11grab -i :0.0 -r 30 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055");
                Scanner scanner = new Scanner(this.pr.getInputStream());
                while (scanner.hasNext()) {
                    System.out.println(scanner.nextLine());
                }
            } else {
                System.out.println("running windows");
                //Runtime.getRuntime().exec("ffmpeg -f dshow  -i video=\"UScreenCapture\"  -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055");
                String query = "ffmpeg -f dshow  -i video=\"UScreenCapture\"  -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055";
                try
                {
                    this.pr=Runtime.getRuntime().exec(query);
                    pr.waitFor();
                    BufferedReader reader=new BufferedReader(
                            new InputStreamReader(pr.getInputStream())
                    );
                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        System.out.println(line);
                    }

                }
                catch(IOException e1) {}
                catch(InterruptedException e2) {}

                System.out.println("Done");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        this.pr.destroy();
        /*OutputStream ostream = this.pr.getOutputStream(); //Get the output stream of the process, which translates to what would be user input for the commandline
        try {
            ostream.write("q\n".getBytes());       //write out the character Q, followed by a newline or carriage return so it registers that Q has been 'typed' and 'entered'.
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ostream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public String sendRequest() {
        String urlToRead = "http://users.iit.demokritos.gr/~ggianna/ICStudy/ip.txt";
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
