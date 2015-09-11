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
package org.scify.icstudy;

import java.awt.Toolkit;
import java.util.Date;
import javax.swing.JFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.scify.icstudy.filters.ManualBinarizationFilter;
import org.scify.icstudy.filters.ManualInverseBinarizationFilter;
import org.scify.icstudy.gui.ICStudyCanvas;

/**
 *
 * @author peustr
 */
public class DesktopSetup {

    private static final int SCR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) throws FrameGrabber.Exception {

        // args[0] = The IP (i.e 192.168.1.1)
        // args[1] = The filename that holds the stream (i.e desktopRecording)
        // args[2] = The extension of the file (i.e mp4)
        // TODO: Restore
//        FrameGrabber grabber = new FFmpegFrameGrabber("http://" + args[0] + "/" + args[1] + "." + args[2]);
        String sPort;
        if (args.length > 0)
            sPort = args[0];
        else
            sPort = "25055";
        FrameGrabber grabber = new FFmpegFrameGrabber("udp://@:" + sPort);
        System.out.println("Listening on port " + sPort);
//        FrameGrabber grabber = new FFmpegFrameGrabber("x.sdp");
//        FrameGrabber grabber = new FFmpegFrameGrabber("http://192.168.1.4:5152/desktop.ogg");
//        grabber.setFormat(args[2]);
//        grabber.setFormat("mpeg4");
        grabber.setImageWidth(SCR_WIDTH);
        grabber.setImageHeight(SCR_HEIGHT);

        ICStudyCanvas canvas = new ICStudyCanvas("Desktop source");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLocation(0, 0);
        canvas.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Available filters
        canvas.addFilter(new ManualBinarizationFilter());
        canvas.addFilter(new ManualInverseBinarizationFilter());

        IplImage curImg;

        long lLastUpdate = 0L;
        grabber.start();
        while (canvas.isDisplayable()) {
            curImg = grabber.grab();
            // Drop if less than 0.1 have passed
            long lNow = new Date().getTime();
            if (lNow - lLastUpdate < 500)
                continue;
            lLastUpdate = lNow;
            
            if (curImg != null) {
                canvas.showImage(curImg);
                // Flush the bufer with a 2% chance
//                if (Math.random() < 0.02) {
//                    // DEBUG LINES
//                    System.out.println("Flushing buffer..");
//                    //////////////
//                    grabber.flush();
//                }
                System.err.println("Received data...");
            }
        }
        grabber.stop();

    }

}
