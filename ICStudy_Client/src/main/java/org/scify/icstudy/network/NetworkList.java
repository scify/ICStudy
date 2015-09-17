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
        System.out.println(osName);
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
            System.out.println(inetAddress.toString());
            if(next) {
                return inetAddress.toString().substring(inetAddress.toString().lastIndexOf("/") + 1);
            }
            if(inetAddress.toString().contains("eth0") || inetAddress.toString().contains("e0")) {
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