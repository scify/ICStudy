/*
 * Copyright 2014 SciFY.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scify.icstudy.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class SciFyServer {

    Process pr;
    private static String connection_id = "scify_test";

    public static void main() {
    }

    public SciFyServer() {
    }

    public void startServer() {
        Runtime rt = Runtime.getRuntime();
        String osName = System.getProperty("os.name");
        System.out.println(osName);
        //SciFyServer sciFyServer = new SciFyServer();
        String ip = sendRequest(connection_id);
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

    public String sendRequest(String connection_id) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
