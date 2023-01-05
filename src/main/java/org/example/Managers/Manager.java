package org.example.Managers;

import org.example.Database.Database;

public class Manager { //TODO rename Dispatcher ?
    public static void dispatch(String msg, String address) {
        // TODO affiche msg dans GUI

        Database.insertMessage("toto", msg);
    }
}
