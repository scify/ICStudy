package org.scify.icstudy.filters;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvNot;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_GRAY2RGB;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

/**
 *
 * @author peustr
 */
public class NegativeFilter extends DefaultICSeeFilter {

    @Override
    public IplImage filter(IplImage sourceImage) {

        IplImage procImg = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);

        cvCvtColor(sourceImage, procImg, COLOR_RGB2GRAY);

        cvNot(procImg, procImg);

        cvCvtColor(procImg, sourceImage, COLOR_GRAY2RGB);

        return sourceImage;
    }

}
