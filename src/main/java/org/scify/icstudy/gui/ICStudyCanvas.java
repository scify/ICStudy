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
package org.scify.icstudy.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.scify.icstudy.filters.ICSeeFilter;
import org.scify.icstudy.filters.NullFilter;

/**
 *
 * @author peustr
 */
public class ICStudyCanvas extends CanvasFrame {

//    private static final int SPEED_1 = 5;
//    private static final int SPEED_2 = 10;
//    private static final int SPEED_3 = 20;
    private List<ICSeeFilter> filters;
    private ICSeeFilter selectedFilter;
    private int selectedIndex;

    public ICStudyCanvas(String title) {
        super(title);
        filters = new ArrayList();
        selectedFilter = new NullFilter();
//        roi = new CvRect();
        selectedIndex = 0;
//        zoom = 0;
//        offsetX = 0;
//        offsetY = 0;
//        speed = SPEED_1;
        initComponents();
    }

    private void initComponents() {
        KeyListener controls = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_UP) {
//                    if (zoom + speed < inputImage.width() / 2) {
//                        zoom += speed;
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//                    if (zoom - speed >= 0) {
//                        zoom -= speed;
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_D) {
//                    if (roi.x() + roi.width() + speed < inputImage.width()) {
//                        offsetX += speed;
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_A) {
//                    if (roi.x() - speed >= 0) {
//                        offsetX -= speed;
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_S) {
//                    if (roi.y() + roi.height() + speed < inputImage.height()) {
//                        offsetY += speed;
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_W) {
//                    if (roi.y() - speed >= 0) {
//                        offsetY -= speed;
//                    }
//                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    nextFilter();
                    System.out.println("Switched to "
                            + selectedFilter);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    previousFilter();
                    System.out.println("Switched to "
                            + selectedFilter);
//                } else if (e.getKeyCode() == KeyEvent.VK_R) {
//                    zoom = 0;
//                    offsetX = 0;
//                    offsetY = 0;
//                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
//                    dispose();
//                } else if (e.getKeyCode() == KeyEvent.VK_1) {
//                    speed = SPEED_1;
//                } else if (e.getKeyCode() == KeyEvent.VK_2) {
//                    speed = SPEED_2;
//                } else if (e.getKeyCode() == KeyEvent.VK_3) {
//                    speed = SPEED_3;
                }
            }

        };

        this.addKeyListener(controls);
        this.getCanvas().addKeyListener(controls);

        filters.add(selectedFilter);
    }

    public ICSeeFilter getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(ICSeeFilter selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public void addFilter(ICSeeFilter filter) {
        filters.add(filter);
    }

    public void removeFilter(ICSeeFilter filter) {
        filters.remove(filter);
    }

    public List<ICSeeFilter> listFilters() {
        return filters;
    }

    private void nextFilter() {
        try {
            selectedFilter = filters.get(++selectedIndex);
        } catch (IndexOutOfBoundsException ex) {
            // If out of bounds, cycle
            selectedIndex = 0;
            selectedFilter = filters.get(selectedIndex);
        }
    }

    private void previousFilter() {
        try {
            selectedFilter = filters.get(--selectedIndex);
        } catch (IndexOutOfBoundsException ex) {
            // If out of bounds, cycle
            selectedIndex = filters.size() - 1;
            selectedFilter = filters.get(selectedIndex);
        }
    }

    /**
     * Shows image on canvas after it applies the selected filter to it. If no
     * filters have been added, it defaults to NullFilter.
     *
     * @param image
     */
    public void showImage(IplImage image) {
        super.showImage(selectedFilter.filter(image));
    }
//    public void showImage(IplImage image) {
//        inputImage = image;
//        double ratio = inputImage.width() / inputImage.height();
//        if (zoom > 0) {
//            // Hard calculations to keep aspect ratio
//            roi = cvRect(zoom / 2 + offsetX,
//                    (int) (zoom / ratio) / 2 + offsetY,
//                    inputImage.width() - zoom,
//                    inputImage.height() - (int) (zoom / ratio));
//            cvSetImageROI(inputImage, roi);
//            zoomedImage = cvCreateImage(cvGetSize(inputImage), inputImage.depth(), inputImage.nChannels());
//            cvResize(inputImage, zoomedImage, CV_INTER_LINEAR);
//            cvResetImageROI(inputImage);
//            super.showImage(selectedFilter.filter(zoomedImage));
//            cvReleaseImage(zoomedImage);
//        } else {
//            super.showImage(selectedFilter.filter(inputImage));
//        }
////        super.showImage(selectedFilter.filter(image));
//    }

}
