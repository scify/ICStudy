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
