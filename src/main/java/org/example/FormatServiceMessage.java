package org.example;

public class FormatServiceMessage {

    public static String msgGetConnectedUsers(){
        return "001";
    }

    public static String msgIsConnected(){
        return "002";
    }

    public static String msgChooseUsername(String msg) { return "003" + msg; }

    public static String msgUserLeaving(){
        return "004";
    } // TODO add in GUI class when clicking on "close" button

}
