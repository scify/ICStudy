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
import org.bytedeco.javacv.FrameGrabber;
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

        // args[0] = The IP (i.e 192.168.1.1)
        // args[1] = The filename that holds the stream (i.e desktopRecording)
        // args[2] = The extension of the file (i.e mp4)
        // TODO: Restore
//        FrameGrabber grabber = new FFmpegFrameGrabber("http://" + args[0] + "/" + args[1] + "." + args[2]);
        final String sPort;
        if (args.length > 0)
            sPort = args[0];
        else
            sPort = "25055";

        /*final JFrame frame = new JFrame("ICStudy");

        frame.setSize(500, 250);

        final JTextField txt = new JTextField("");
        javax.swing.JLabel label = new JLabel("Πληκτρολογήστε το όνομα του τμήματος και πατήστε OK");

        label.setBounds(20, 20, 500, 30);
        txt.setBounds(20, 60, 300, 30);
        JButton button = new JButton("OK");
        button.setBounds(100, 100, 200, 40);
        frame.add(label);
        frame.add(txt);
        frame.add(button);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String getTxt = txt.getText();
                //TODO: verify string and connect to new service
                try {
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                createICStudyCanvas(sPort);
                            }catch (Exception exc) {
                                System.out.println("Exception: " + exc.getMessage());
                            }
                        }
                    };
                    thread.start();

                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
                frame.setVisible(false);
            }
        });*/
        createICStudyCanvas(sPort);
    }

    private static void createICStudyCanvas(String sPort) throws FrameGrabber.Exception {
        FrameGrabber grabber = new FFmpegFrameGrabber("udp://@:" + sPort);
        System.out.println("Listening on port " + sPort);
//        FrameGrabber grabber = new FFmpegFrameGrabber("x.sdp");
//        FrameGrabber grabber = new FFmpegFrameGrabber("http://192.168.1.4:5152/desktop.ogg");
//        grabber.setFormat(args[2]);
//        grabber.setFormat("mpeg4");
        System.out.println("Screen height: " + SCR_HEIGHT);
        System.out.println("Screen width: " + SCR_WIDTH);
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
        System.out.println("before grabber.start()");
        grabber.start();
        System.out.println("after grabber.start()");
        System.out.println("canvas: " + canvas.isDisplayable());
        while (canvas.isDisplayable()) {

            curImg = grabber.grab();
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
            }
        }
        grabber.stop();
    }
}
