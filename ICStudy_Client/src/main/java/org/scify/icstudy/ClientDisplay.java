package org.scify.icstudy;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scify.icstudy.gui.ICStudyCanvas;

import java.awt.*;
import java.util.Date;

public class ClientDisplay {

    private static final int SCR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    ICStudyCanvas canvas;
    String sPort;
    String ip;

    public ClientDisplay(String ip, String sPort, ICStudyCanvas canvas) {
        this.ip = ip;
        this.sPort = sPort;
        this.canvas = canvas;
    }

    public void listenAndDisplay() throws FrameGrabber.Exception {
        FrameGrabber grabber = new FFmpegFrameGrabber("udp://" + ip + ":" + sPort);
        System.out.println("Listening on port " + sPort);

        grabber.setImageWidth(SCR_WIDTH);
        grabber.setImageHeight(SCR_HEIGHT);

        opencv_core.IplImage curImg;

        long lLastUpdate = 0L;
        System.out.println("before grabber.start()");
        grabber.start();
//        while (grabber.grab() != null) {
//            System.out.println("frame grabbed");
//        }
        System.out.println("after grabber.start()");
        while (canvas.isDisplayable()) {
            //curImg = grabber.grab();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            Frame img = grabber.grab();
            curImg = converter.convert(img);
            ///////////////
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
//                System.err.println("Received data...");
            } else {
                System.out.println("received a null img!");
            }
        }
        grabber.stop();
    }

    public void listenAndDisplayTest() {
        FFmpegFrameGrabber videoCapture = null;

        String sListenTo = "udp://127.0.0.1:" + sPort;
        System.err.println("Trying to open UDP port " + sListenTo + "...");

        // https://github.com/opencv/opencv/pull/9292
        // Windows patch
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            if (System.getenv("OPENCV_FFMPEG_CAPTURE_OPTIONS") != null &&
                    !System.getenv("OPENCV_FFMPEG_CAPTURE_OPTIONS").equals("rtsp_transport;udp")) {

                System.err.println("ERROR: FFMPEG environement variables should contain 'OPENCV_FFMPEG_CAPTURE_OPTIONS=rtsp_transport;udp' for Windows." +
                        "\n Current value is : " +
                        System.getenv("OPENCV_FFMPEG_CAPTURE_OPTIONS") + "\n Aborting...");
                return;
            }
        }

        while (videoCapture == null) {
            videoCapture = new FFmpegFrameGrabber(sListenTo);
            try {
                videoCapture.start();
            } catch (FrameGrabber.Exception e) {
                System.err.println("Could not open video stream. Reason:");
                e.printStackTrace(System.err);
                return;
            }
        }

//            try {
//                if (!videoCapture.isOpened()) {
//                    Thread.sleep(1000);
//                    System.err.println("Retrying...");
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        boolean isOpened = videoCapture.isOpened();
//        boolean isSucceed = videoCapture.grab();
//
//        System.out.println("isOpened: " + isOpened);
//        System.out.println("isSucceed: " + isSucceed);
        System.out.println("------- START GRAB -------");
        while (canvas.isDisplayable()) {

            Frame fCur = null;
            try {
                fCur = videoCapture.grab();
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
//            opencv_core.Mat mat = new OpenCVFrameConverter.ToMat().convertToMat(fCur);
//            opencv_core.IplImage iplImage = Java2DFrameUtils.toIplImage(mat);
            opencv_core.IplImage iplImage = new OpenCVFrameConverter.ToMat().convertToIplImage(fCur);
            System.out.println("width, height = "+iplImage.width()+", "+iplImage.height());
            if(iplImage != null) {
                canvas.showImage(iplImage);
                System.err.println("Received data...");
            } else {
                System.out.println("received a null img!");
            }
        }
    }


}
