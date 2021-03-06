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

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.scify.icstudy.filters.ICSeeFilter;
import org.scify.icstudy.filters.NullFilter;
import org.scify.icstudy.network.NetworkList;
import org.scify.icstudy.properties.PropertyHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author peustr
 */
public class ICStudyCanvas extends CanvasFrame {

    private List<ICSeeFilter> filters;
    private ICSeeFilter selectedFilter;
    private int selectedIndex;
    protected PropertyHandler properties;

    public ICStudyCanvas(String title) {
        super(title);
        properties = new PropertyHandler();
        filters = new ArrayList();
        selectedFilter = new NullFilter();
        selectedIndex = 0;

        initComponents();
    }

    private String getSavedIp() {
        String propIp;
        propIp = properties.getValue("client_ip");
        if (propIp != null)
            System.out.println("saved ip is: " + propIp);
        return propIp;
    }

    private String getNewIp() {
        NetworkList networkList = new NetworkList();
        String ip = networkList.initNetworks();
        System.out.println("new IP is: " + ip);
        return ip;
    }

    private void initComponents() {

        String ip = getSavedIp();
        if(ip == null) {
            ip = getNewIp();
        }
        properties.setValue("client_ip", ip);
        KeyListener controls = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
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
                }
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("escape");
                    try {
                        Runtime runtime = Runtime.getRuntime();
                        Process proc = runtime.exec("shutdown -h now");
                        //System.exit(0);
                        Scanner scanner = new Scanner(proc.getInputStream());
                        while (scanner.hasNext()) {
                            System.out.println(scanner.nextLine());
                        }
                    } catch (IOException ex) {
                    }

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
        //super.showImage(selectedFilter.filter(image));
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        Frame img = converter.convert(selectedFilter.filter(image));
        super.showImage(img);
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
