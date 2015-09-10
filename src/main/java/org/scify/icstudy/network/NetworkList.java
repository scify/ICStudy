package org.scify.icstudy.network;

import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.out;

public class NetworkList {

    static String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        /*out.printf("Display name: %s\n", netint.getDisplayName());
        out.printf("Name: %s\n", netint.getName());*/
        boolean next = false;
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            //out.printf("InetAddress: %s\n", inetAddress);
            if(next) {
                //System.out.println(inetAddress);
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
        //System.out.println(initNetworks());
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