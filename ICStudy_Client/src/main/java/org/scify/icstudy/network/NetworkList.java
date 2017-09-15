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
package org.scify.icstudy.network;

import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.out;

public class NetworkList {

    static String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        boolean next = false;
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        String osName = System.getProperty("os.name");

        if(! new String("Linux").equals(osName) ) {
            try {
                InetAddress IP = InetAddress.getLocalHost();
                String ipWindows = IP.getHostAddress();
                System.out.println("IP of my system is := " + ipWindows);
                return ipWindows;
            } catch(UnknownHostException e) {
                System.out.println(e);
            }
        }

        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if(next) {
                return inetAddress.toString().substring(inetAddress.toString().lastIndexOf("/") + 1);
            }
            if(! inetAddress.isLoopbackAddress()) {
                next = true;
            }
        }
        out.printf("\n");
        return "";
    }

    public NetworkList() {
    }

    public String initNetworks() {
        String ip;
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                ip = displayInterfaceInformation(netint);
                if(ip !="")
                    return ip;
            }
        } catch(SocketException se) {
            System.out.println(se);
        }
        return "";
    }
}