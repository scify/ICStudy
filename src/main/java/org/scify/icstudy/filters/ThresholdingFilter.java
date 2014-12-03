package org.scify.icstudy.filters;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplConvKernel;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_GRAY2RGB;
import static org.bytedeco.javacpp.opencv_imgproc.MORPH_ELLIPSE;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCreateStructuringElementEx;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvDilate;

/**
 *
 * @author peustr
 */
public abstract class ThresholdingFilter extends DefaultICSeeFilter {

    public static final double FREQ_MIN_VALUE = 0;
    public static final double FREQ_MID_VALUE = 127;
    public static final double FREQ_WHITE_SEPARATION_VALUE = 200;
    public static final double FREQ_MAX_VALUE = 255;
    public static final int KERNEL_SIZE = 1;

    @Override
    public IplImage filter(IplImage sourceImage) {

        IplImage procImg = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);

        cvCvtColor(sourceImage, procImg, COLOR_RGB2GRAY);

        // Fill gaps
        IplConvKernel kernel
                = cvCreateStructuringElementEx(KERNEL_SIZE, KERNEL_SIZE, 0, 0, MORPH_ELLIPSE);
        cvDilate(procImg, procImg, kernel, 1);

        // Implemented by offspring
        applyThreshold(procImg, procImg);

        cvCvtColor(procImg, sourceImage, COLOR_GRAY2RGB);

        return sourceImage;

    }

    protected abstract void applyThreshold(IplImage sourceImage, IplImage destImage);

}
