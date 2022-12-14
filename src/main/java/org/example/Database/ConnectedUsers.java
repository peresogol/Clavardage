package org.example.Database;

import org.example.CustomExceptions.UsernameManagementException;

import java.util.LinkedList;


public class ConnectedUsers {

    // TOUT MODIFIER POUR REMPLACER LISTE PAR HASHMAP AVEC CODE ID <=> USERNAME
    private LinkedList<String> connectedUsers;

    public ConnectedUsers() {
        this.connectedUsers = new LinkedList<>();
    }

    public void addUser(String username) throws UsernameManagementException {
        if(this.connectedUsers.contains(username)){
            throw new UsernameManagementException("Username is already in use. ");
        } else {
            this.connectedUsers.add(username);
        }
    }

    public void removeUser(String username) throws UsernameManagementException {
        if(this.connectedUsers.contains(username)){
            this.connectedUsers.remove(username);
        } else {
            throw new UsernameManagementException("User is already disconnected. ");
        }
    }

    public boolean isConnected(String username){
        return this.connectedUsers.contains(username);
    }

    public void changeUsername(String oldUsername, String newUsername) {

    }

}


