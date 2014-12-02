package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.IplImage;

/**
 *
 * @author peustr
 */
public abstract class DefaultICSeeFilter implements ICSeeFilter {

    @Override
    public abstract IplImage filter(IplImage sourceImage);

    @Override
    public String toString() {
        return getClass().getCanonicalName();
    }
}
