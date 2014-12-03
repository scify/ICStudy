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
        FrameGrabber grabber = new FFmpegFrameGrabber("http://" + args[0] + "/" + args[1] + "." + args[2]);
        grabber.setFormat(args[2]);
        grabber.setImageWidth(SCR_WIDTH);
        grabber.setImageHeight(SCR_HEIGHT);

        ICStudyCanvas canvas = new ICStudyCanvas("Desktop source");
        canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        canvas.setLocation(0, 0);

        // Available filters
        canvas.addFilter(new ManualBinarizationFilter());
        canvas.addFilter(new ManualInverseBinarizationFilter());

        IplImage curImg;

        grabber.start();
        while (canvas.isDisplayable()) {
            curImg = grabber.grab();
            if (curImg != null) {
                canvas.showImage(curImg);
                // flush the bufer with a 10% chance
                if (Math.random() < 0.1) {
                    // DEBUG LINES
                    System.out.println("Flushing buffer..");
                    //////////////
                    grabber.flush();
                }
            }
        }
        grabber.stop();

    }

}
