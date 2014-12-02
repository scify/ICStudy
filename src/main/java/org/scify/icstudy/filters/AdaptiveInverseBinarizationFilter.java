package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_OTSU;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;

/**
 *
 * @author peustr
 */
public class AdaptiveInverseBinarizationFilter extends ThresholdingFilter {

    @Override
    protected void applyThreshold(IplImage sourceImage, IplImage destImage) {
        cvThreshold(sourceImage,
                destImage,
                -1,
                FREQ_MAX_VALUE,
                CV_THRESH_BINARY_INV + CV_THRESH_OTSU
        );
    }

}
