package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import static org.scify.icstudy.filters.ThresholdingFilter.FREQ_MAX_VALUE;

/**
 *
 * @author peustr
 */
public class ManualBinarizationFilter extends ThresholdingFilter {

    @Override
    protected void applyThreshold(IplImage sourceImage, IplImage destImage) {
        cvThreshold(sourceImage,
                destImage,
                FREQ_WHITE_SEPARATION_VALUE,
                FREQ_MAX_VALUE,
                CV_THRESH_BINARY
        );
    }

}
