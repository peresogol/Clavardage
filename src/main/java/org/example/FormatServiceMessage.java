package org.example;

public class FormatServiceMessage {

    public static String msgGetConnectedUsers(){
        return "001";
    }
    public static String msgIsConnected(){
        return "002";
    }

    public static String msgChooseUsername(String msg) { return "003" + msg; }

    public static String msgUsernameAlreadyUsed(){
        return "004";
    }

    public static String msgUserLeaving(){
        return "005";
    } // TODO add in GUI class when clicking on "close" button

    public static String msgAskPort() { return "006"; }

    public static String msgRespondPort (String port) { return "007" + port; }

}
