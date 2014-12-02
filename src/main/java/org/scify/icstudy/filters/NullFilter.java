package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.IplImage;

/**
 *
 * @author peustr
 */
public class NullFilter extends DefaultICSeeFilter {

    @Override
    public IplImage filter(IplImage sourceImage) {
        return sourceImage;
    }

}
