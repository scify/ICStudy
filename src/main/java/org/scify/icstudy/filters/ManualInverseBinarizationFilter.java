package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import static org.scify.icstudy.filters.ThresholdingFilter.FREQ_MAX_VALUE;
import static org.scify.icstudy.filters.ThresholdingFilter.FREQ_MID_VALUE;

/**
 *
 * @author peustr
 */
public class ManualInverseBinarizationFilter extends ThresholdingFilter {

    @Override
    protected void applyThreshold(opencv_core.IplImage sourceImage, opencv_core.IplImage destImage) {
        cvThreshold(sourceImage,
                destImage,
                FREQ_MID_VALUE,
                FREQ_MAX_VALUE,
                CV_THRESH_BINARY_INV
        );
    }

}
