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
package org.scify.icstudy;

import org.bytedeco.javacv.FrameGrabber;
import org.scify.icstudy.filters.ManualBinarizationFilter;
import org.scify.icstudy.filters.ManualInverseBinarizationFilter;
import org.scify.icstudy.gui.ICStudyCanvas;

import javax.swing.*;

/**
 *
 * @author peustr
 */
public class DesktopSetup {

    public static void main(String[] args) throws FrameGrabber.Exception {

        final String sPort;
        if (args.length > 0)
            sPort = args[0];
        else
            sPort = "25055";

        ClientDisplay clientDisplay = new ClientDisplay(sPort, createCanvas());
        clientDisplay.listenAndDisplay();
    }

    private static ICStudyCanvas createCanvas() {
        ICStudyCanvas canvas = new ICStudyCanvas("Desktop source");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLocation(0, 0);
        canvas.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Available filters
        canvas.addFilter(new ManualBinarizationFilter());
        canvas.addFilter(new ManualInverseBinarizationFilter());
        canvas.setVisible(true);
        canvas.validate();
        return canvas;
    }
}
