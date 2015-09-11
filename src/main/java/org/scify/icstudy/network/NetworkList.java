package org.scify.icstudy.network;

import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.out;

public class NetworkList {

    static String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        boolean next = false;
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if(next) {
                return inetAddress.toString().substring(inetAddress.toString().lastIndexOf("/") + 1);
            }
            if(inetAddress.toString().contains("eth0")) {
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