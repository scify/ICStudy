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
    protected ICStudyCanvas canvas;
    protected String sPort;
    protected String ip;

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
            showImage(grabber, curImg);
        }
        grabber.stop();
    }

    protected void showImage(FrameGrabber grabber, opencv_core.IplImage curImg) throws FrameGrabber.Exception {
        if (curImg != null) {
            canvas.showImage(curImg);
            // Flush the bufer with a 2% chance
            if (Math.random() < 0.02) {
                // DEBUG LINES
                System.out.println("Flushing buffer..");
                //////////////
                grabber.flush();
            }
            System.err.println("Received data...");
        } else {
            System.out.println("received a null img!");
        }
    }
}
