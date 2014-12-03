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
package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY_INV;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import static org.scify.icstudy.filters.ThresholdingFilter.FREQ_MAX_VALUE;
import static org.scify.icstudy.filters.ThresholdingFilter.FREQ_WHITE_SEPARATION_VALUE;

/**
 *
 * @author peustr
 */
public class ManualInverseBinarizationFilter extends ThresholdingFilter {

    @Override
    protected void applyThreshold(opencv_core.IplImage sourceImage, opencv_core.IplImage destImage) {
        cvThreshold(sourceImage,
                destImage,
                FREQ_WHITE_SEPARATION_VALUE,
                FREQ_MAX_VALUE,
                CV_THRESH_BINARY_INV
        );
    }

}
