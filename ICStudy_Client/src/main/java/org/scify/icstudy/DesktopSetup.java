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
import java.lang.Exception;
import java.util.Date;
import javax.swing.*;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scify.icstudy.filters.ManualBinarizationFilter;
import org.scify.icstudy.filters.ManualInverseBinarizationFilter;
import org.scify.icstudy.gui.ICStudyCanvas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author peustr
 */
public class DesktopSetup {

    private static final int SCR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) throws FrameGrabber.Exception {

        final String sPort;
        if (args.length > 0)
            sPort = args[0];
        else
            sPort = "25055";

        createICStudyCanvas(sPort);
    }

    private static void createICStudyCanvas(String sPort) throws FrameGrabber.Exception {
        FrameGrabber grabber = new FFmpegFrameGrabber("udp://@:" + sPort);
        System.out.println("Listening on port " + sPort);

        grabber.setImageWidth(SCR_WIDTH);
        grabber.setImageHeight(SCR_HEIGHT);

        ICStudyCanvas canvas = new ICStudyCanvas("Desktop source");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLocation(0, 0);
        canvas.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Available filters
        canvas.addFilter(new ManualBinarizationFilter());
        canvas.addFilter(new ManualInverseBinarizationFilter());
        canvas.setVisible(true);
        canvas.validate();
        IplImage curImg;

        long lLastUpdate = 0L;
        System.out.println("before grabber.start()");
        grabber.start();
        System.out.println("after grabber.start()");
        while (canvas.isDisplayable()) {

            //curImg = grabber.grab();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            Frame img = grabber.grab();
            curImg = converter.convert(img);
            System.out.println("img: " + curImg);
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
            } else {
                System.out.println("received a null img!");
            }
        }
        grabber.stop();
    }
}
