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
