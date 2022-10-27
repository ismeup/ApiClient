package net.ismeup.apiclient.controller;

import net.ismeup.apiclient.model.LoginData;

import java.util.Scanner;

public class CmdLineInput {

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line.trim();
    }

    public String requestString(String message) {
        System.out.println(message + ":");
        return getUserInput();
    }

    public LoginData authenticate(String appname, int lifetime) {
        System.out.println("To register this watcher in standalone mode, provide your login and password");
        System.out.println("If you are using \"Sign in with Apple\" or \"Sign in with Google\" function, you still can set password for your account");
        System.out.println("To do this, open \"My account\" page in your mobile app or in Web-browser and set new password");
        System.out.println("This will not affect your \"Sign in with Apple\" or \"Sign in with Google\" functionality. You will be able to use this functions.");
        String login = requestString("Enter your login");
        String password = requestString("Enter password for " + login);
        LoginData loginData = new LoginData(login, password, appname, lifetime);
        return loginData;
    }
}
