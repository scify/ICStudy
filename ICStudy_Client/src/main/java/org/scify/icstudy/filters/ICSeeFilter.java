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
