package org.example.CustomExceptions;

public class UsernameManagementException extends Exception {
    public UsernameManagementException(String errorMessage) {
        super(errorMessage);
    }
}