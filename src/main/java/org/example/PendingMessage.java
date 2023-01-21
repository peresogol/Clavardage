package org.example;

import java.net.InetAddress;

public class PendingMessage {

    private InetAddress address;
    private int seqNb;
    private String msg;


    public PendingMessage(InetAddress addr, String msg){
        this.address = addr;
        this.msg = msg;
    }


    public String getMsg(){
        return this.msg;
    }

    public InetAddress getAddr() {
        return this.address;
    }
}
