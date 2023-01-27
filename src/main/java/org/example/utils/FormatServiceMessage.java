package org.example.utils;

import org.example.NetworkManager.NetworkManager;

/*
Classe permettant la création des messages "standards" échangés via UDP.
 */
public class FormatServiceMessage {

    public static String msgGetConnectedUsers(){
        return "001" + NetworkManager.username;
    }

    public static String msgUsernameOK() { return  "002" + NetworkManager.username;}

    public static String msgUsernameKO() { return "003" + NetworkManager.username;}

    public static String msgChooseUsername(String msg) { return "004" + msg; }

    public static String msgUserLeaving(){ return "005" + NetworkManager.username; }


}
