package com.sck.engine;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by KINCERS on 4/6/2015.
 */
public class ActivityLog {

    private String hostname;
    private String ip;

    public ActivityLog() {


        //Running from
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

    }


    public void clear() {

    }

}
