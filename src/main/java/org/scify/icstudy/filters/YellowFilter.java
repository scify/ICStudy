package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvInRangeS;
import static org.bytedeco.javacpp.opencv_core.cvSet;

/**
 * Filter that replaces the white color in a black and white image with yellow.
 *
 * @author peustr
 */
public class YellowFilter extends DefaultICSeeFilter {

    @Override
    public IplImage filter(IplImage sourceImage) {

        IplImage whiteImg = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
        cvInRangeS(sourceImage, CvScalar.WHITE, CvScalar.WHITE, whiteImg);

        cvSet(sourceImage, CvScalar.YELLOW, whiteImg);

        return sourceImage;
    }

}
