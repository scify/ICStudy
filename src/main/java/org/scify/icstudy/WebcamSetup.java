package org.scify.icstudy;

import java.awt.Toolkit;
import javax.swing.JFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FrameGrabber;
import org.scify.icstudy.filters.ManualBinarizationFilter;
import org.scify.icstudy.filters.ManualInverseBinarizationFilter;
import org.scify.icstudy.gui.ICStudyCanvas;

/**
 *
 * @author peustr
 */
public class WebcamSetup {

    private static final int SCR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) throws FrameGrabber.Exception {

        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.setImageWidth(SCR_WIDTH);
        grabber.setImageHeight(SCR_HEIGHT);

        ICStudyCanvas canvas = new ICStudyCanvas("Webcam source");
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
            }
        }
        grabber.stop();

    }

}
