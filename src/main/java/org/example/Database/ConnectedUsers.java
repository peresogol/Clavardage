package org.example.Database;

import org.example.CustomExceptions.UsernameManagementException;

import java.util.HashMap;


public class ConnectedUsers {

    private HashMap<String, String> connectedUsers;

    public ConnectedUsers() {
        this.connectedUsers = new HashMap<>();
    }

    public void addUser(String address, String username) {
        if(!this.connectedUsers.containsKey(address)){
            this.connectedUsers.put(address, username);
        }
    }

    public void removeUser(String address) throws UsernameManagementException {
        if(this.connectedUsers.containsKey(address)){
            this.connectedUsers.remove(address);
        } else {
            throw new UsernameManagementException("User is already disconnected. ");
        }
    }

    public boolean isConnected(String address){
        return this.connectedUsers.containsKey(address);
    }

    public void changeUsername(String address, String newUsername) throws UsernameManagementException {
        if(this.connectedUsers.containsKey(address)){
            this.connectedUsers.replace(address, newUsername);
            //this.connectedUsers.replace(address, oldUsername, newUsername);
        } else {
            throw new UsernameManagementException("User is already disconnected. ");
        }
    }

}


