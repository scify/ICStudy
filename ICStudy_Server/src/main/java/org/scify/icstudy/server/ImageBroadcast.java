package org.scify.icstudy.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImageBroadcast {

    String clientIP;
    String screenResolution;
    Process pr;

    public ImageBroadcast(String clientIP, String screenResolution) {
        this.clientIP = clientIP;
        this.screenResolution = screenResolution;
    }

    protected void startBroadcasting() {
        try {
            File encodingFile = new File(".encoding");
            ProcessBuilder pb = getProcessBuilderForPlatform();
            System.out.println(getCommandForBroadcastAsString());

            pb.redirectErrorStream(true);
            pb.redirectInput(ProcessBuilder.Redirect.PIPE); //optional, default behavior
            //pb.redirectOutput(encodingFile);
            pr = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            pr.waitFor();
            encodingFile.delete();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    protected boolean isLinux() {
        String osName = System.getProperty("os.name");
        return "Linux".equals(osName);
    }

    protected ProcessBuilder getProcessBuilderForPlatform() {
        if (isLinux()) {
            return new ProcessBuilder("ffmpeg", "-s", screenResolution, "-f", "x11grab", "-i", ":0.0", "-r", "30", "-vcodec", "mpeg4", "-q", "1", "-f", "mpegts", "udp://" + clientIP + ":25055");
        }
        return new ProcessBuilder("ffmpeg", "-s", screenResolution, "-f", "dshow", "-i", "video=\"UScreenCapture\"", "-r", "10", "-vcodec", "mpeg4", "-q", "1", "-f", "mpegts", "udp://" + clientIP + ":25055");
    }

    protected String getCommandForBroadcastAsString() {
        if (isLinux()) {
            return "ffmpeg " + "-s " + screenResolution + " -f " + "x11grab " + "-i " + ":0.0 " + "-r " + "30 " + "-vcodec " + "mpeg4 " + "-q " + "1 " + "-f " + "mpegts " + "udp://" + clientIP + ":25055";
        }
        return "ffmpeg " + "-s " + screenResolution + " -f " + "dshow " + "-i " + "video=\"UScreenCapture\"" + " -r " + "10 " + "-vcodec " + "mpeg4 " + "-q " + "1 " + "-f " + "mpegts " + "udp://" + clientIP + ":25055";
    }

    public void stopBroadcasting() {
        if(pr != null)
            pr.destroy();
    }
}
