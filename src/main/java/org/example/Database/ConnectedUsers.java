package org.example.Database;

import org.example.CustomExceptions.UsernameManagementException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;


/*
Classe permettant de stocker les utilisateurs connectés. Associe une adresse IP à un username.
 */
public class ConnectedUsers {

    private HashMap<String, String> connectedUsers;

    public ConnectedUsers() {
        this.connectedUsers = new HashMap<>();
    }

    public void addUser(String username, String address) {
        if(!this.connectedUsers.containsValue(address)){
            this.connectedUsers.put(username, address);
        }
    }

    /*
    Retire un utilisateur de la base de données des utilisateurs connectés
     */
    public void removeUser(String username) throws UsernameManagementException {
        if(this.connectedUsers.containsKey(username)){
            // TODO check pertinence : l'inverse n'est as mieux ?
            this.connectedUsers.remove(username);
        } else {
            throw new UsernameManagementException("User is already disconnected. ");
        }
    }

    public boolean isConnected(String address){
        return this.connectedUsers.containsValue(address);
    }

    public void changeUsername(String newUsername, String oldUsername, String address) throws UsernameManagementException {
        if(this.connectedUsers.containsValue(address)){
            this.removeUser(oldUsername);
            this.addUser(newUsername, address);
        } else {
            throw new UsernameManagementException("User is already disconnected. ");
        }
    }

    public String getAddress(String username){
        return this.connectedUsers.get(username);
    }

    /*
    Resolve username corresponding to an address
    */
    public String getUsername(String address) {
        String res = "";
        for (Entry<String, String> entry : this.connectedUsers.entrySet()) {
            if (Objects.equals(address, entry.getValue())) {
                return entry.getKey();
            }
        }
        return res;
    }
}


