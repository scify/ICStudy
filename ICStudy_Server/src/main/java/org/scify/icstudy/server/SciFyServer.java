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

import helper.PropertyHandler;
import network.IPManager;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SciFyServer {

    Process pr;
    private static String connection_id = "prod_test";
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

    public void startServer() {
        Runtime rt = Runtime.getRuntime();
        String osName = System.getProperty("os.name");
        PropertyHandler propertyHandler = new PropertyHandler();
        String ip = propertyHandler.getValue("client_ip");
        if(ip == null) {
            IPManager ipManager = new IPManager();
            ip = ipManager.getIPFromServer(connection_id);
        }
        System.out.println(ip);
        String screenResolution = width + "x" + height;
        try {
            File encodingFile = new File(".encoding");
            if(new String("Linux").equals(osName) ) {
                /*OLD WAY, it works, but overloads the OutputStream*/
                /*That is why we used the encodingFile*/
                //this.pr = rt.exec("ffmpeg -loglevel debug -v verbose -s 1024x768 -f x11grab -i :0.0 -r 30 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055");

                try {
                    // Use a ProcessBuilder
                    ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-s", screenResolution, "-f", "x11grab", "-i", ":0.0", "-r", "30", "-vcodec", "mpeg4", "-q", "1", "-f", "mpegts","udp://" + ip + ":25055");
                    encodingFile.createNewFile();
                    pb.redirectErrorStream(true);
                    pb.redirectInput(ProcessBuilder.Redirect.PIPE); //optional, default behavior
                    pb.redirectOutput(encodingFile);
                    pr = pb.start();
                    pr.waitFor();
                    encodingFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                //Runtime.getRuntime().exec("ffmpeg -f dshow  -i video=\"UScreenCapture\"  -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055");
                //String query = "ffmpeg -f dshow  -i video=\"UScreenCapture\"  -r 10 -vcodec mpeg4 -q 1 -f mpegts udp://" + ip + ":25055";
                try
                {
                    /*OLD WAY, it works, but overloads the OutputStream*/
                    /*That is why we used the encodingFile*/

                    /*this.pr=Runtime.getRuntime().exec(query);
                    pr.waitFor();
                    BufferedReader reader=new BufferedReader(
                            new InputStreamReader(pr.getInputStream())
                    );
                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        System.out.println(line);
                    }*/
                    ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-s", screenResolution, "-f", "dshow","-i", "video=\"UScreenCapture\"", "-r", "10", "-vcodec", "mpeg4", "-q", "1", "-f", "mpegts","udp://" + ip + ":25055");
                    encodingFile.createNewFile();
                    pb.redirectErrorStream(true);
                    pb.redirectInput(ProcessBuilder.Redirect.PIPE); //optional, default behavior
                    pb.redirectOutput(encodingFile);
                    pr = pb.start();
                    pr.waitFor();
                    encodingFile.delete();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Done");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        this.pr.destroy();
    }

}
