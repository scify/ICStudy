package org.scify.icstudy.filters;

import org.bytedeco.javacpp.opencv_core.IplImage;

/**
 *
 * @author peustr
 */
public interface ICSeeFilter {

    /**
     * Takes an image as input and applies a filter from SciFY's legacy ICSee
     * filters.
     *
     * @param sourceImage The source image.
     * @return The filtered image.
     */
    public IplImage filter(IplImage sourceImage);

}
