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
package org.scify.icstudy.server;

import helper.PropertyHandler;
import network.IPManager;

import java.awt.*;


public class SciFyServer {

    ImageBroadcast imageBroadcast;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

    public void startServer() {
        String osName = System.getProperty("os.name");
        System.out.println("platform: " + osName);
        String screenResolution = width + "x" + height;
        String clientIP = getClientIP();
        imageBroadcast = new ImageBroadcast(clientIP, screenResolution);
        imageBroadcast.startBroadcasting();
    }

    public void stopServer() {
        imageBroadcast.stopBroadcasting();
    }

    protected String getClientIP() {
        PropertyHandler propertyHandler = new PropertyHandler();
        String ip = propertyHandler.getValue("client_ip");
        if(ip == null) {
            IPManager ipManager = new IPManager();
            ip = ipManager.getIPFromServer();
        }

        return ip;
    }

}
