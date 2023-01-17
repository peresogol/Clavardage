package org.example;

import org.example.NetworkManager.NetworkManager;

public class FormatServiceMessage {

    public static String msgGetConnectedUsers(){
        return "001" + NetworkManager.username;
    }
    public static String msgIsConnected(){
        return "002" + NetworkManager.username;
    }

    public static String msgChooseUsername(String msg) { return "003" + msg; }

    public static String msgUsernameAlreadyUsed(){
        return "004" + NetworkManager.username;
    }

    public static String msgUserLeaving(){ return "005" + NetworkManager.username; }

    public static String msgAskPort() { return "006" + NetworkManager.username; }

    public static String msgRespondPort (String port) { return "007" + port; }

}
