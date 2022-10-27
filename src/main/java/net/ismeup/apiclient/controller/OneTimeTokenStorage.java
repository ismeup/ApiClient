package net.ismeup.apiclient.controller;

import net.ismeup.apiclient.model.TokenStorage;

public class OneTimeTokenStorage implements TokenStorage {
    private String token;
    static Object tokenLock = new Object();

    public void setToken(String token) {
        synchronized (tokenLock) {
            this.token = token;
        }
    }

    public String getToken() {
        synchronized (tokenLock) {
            return token;
        }
    }
}
