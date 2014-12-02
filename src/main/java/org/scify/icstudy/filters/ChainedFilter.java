package org.scify.icstudy.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.IplImage;

/**
 *
 * @author peustr
 */
public class ChainedFilter extends DefaultICSeeFilter {

    private List<ICSeeFilter> filters;

    public ChainedFilter(ICSeeFilter... filters) {
        this.filters = new ArrayList();
        this.filters.addAll(Arrays.asList(filters));
    }

    @Override
    public IplImage filter(IplImage sourceImage) {

        // Pass the first filter
        IplImage procImg = filters.get(0).filter(sourceImage);

        // Chain the others
        for (int i = 1; i < filters.size(); i++) {
            procImg = filters.get(i).filter(procImg);
        }

        return procImg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ICSeeFilter curFilter : filters) {
            sb.append(curFilter.toString());
            sb.append(" ");
        }
        return sb.toString();
    }
}
