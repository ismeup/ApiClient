package net.ismeup.apiclient.controller;

import net.ismeup.apiclient.model.CmdSelectable;
import net.ismeup.apiclient.model.LoginData;

import java.util.List;
import java.util.Scanner;

public class CmdLineInput {

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line.trim();
    }

    public String requestString(String message, String defaultString) {
        String defaultValueInfo = "";
        if (defaultString != null && !defaultString.isEmpty()) {
            defaultValueInfo = " [" + defaultString + "]";
        }
        System.out.print(message + defaultValueInfo + ": ");
        String userInput = getUserInput();
        if (userInput.isEmpty()) {
            if (defaultString != null) {
                return defaultString;
            } else {
                return requestString(message);
            }
        } else {
            return userInput;
        }
    }

    public String requestString(String message) {
        return requestString(message, null);
    }

    public CmdSelectable requestSelectable(String message, List<? extends CmdSelectable> selectableList) {
        return requestSelectable(message, selectableList, null);
    }

    public CmdSelectable requestSelectable(String message, List<? extends CmdSelectable> selectableList, CmdSelectable defaultElement) {
        System.out.println(message);
        int defaultIndex = -1;
        for (int i = 0; i < selectableList.size(); i++) {
            System.out.println("[" + i + "] " + selectableList.get(i).getName());
            if (defaultElement != null && selectableList.get(i).equals(defaultElement)) {
                defaultIndex = i;
            }
        }
        int value = -1;
        while (value == -1) {
            String msgToEnter = "Enter value";
            if (defaultIndex > -1) {
                msgToEnter += " [" + defaultIndex + "]";
            }
            String enteredValue = requestString(msgToEnter, "");
            if (enteredValue.isEmpty() && defaultIndex > -1) {
                value = defaultIndex;
            } else {
                try {
                    value = Integer.parseInt(enteredValue);
                    if (value > selectableList.size() - 1 || value < 0) {
                        value = -1;
                    }
                } catch (NumberFormatException e) {
                    value = -1;
                }
            }
        }
        return selectableList.get(value);
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

    public int requestInt(String message, int defaultValue) {
        System.out.print(message + " [" + (defaultValue) + "] : ");
        String value = getUserInput();
        if (value.isEmpty()) {
            return defaultValue;
        }
        int returnValue = 0;
        try {
            returnValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return requestInt(message, defaultValue);
        }
        return returnValue;
    }

    private boolean requestYesNo(String message, boolean defaultValue, boolean defaultIsSet) {
        String defaultString = "";
        String userValue = "";
        String msg = message + " (yes/no)" + defaultString;
        while (!userValue.trim().toLowerCase().equals("yes") && !userValue.trim().toLowerCase().equals("no")) {
            userValue = requestString(msg, defaultIsSet ? (defaultValue ? "yes" : "no") : null );
        }
        return userValue.trim().toLowerCase().equals("yes");
    }

    public boolean requestYesNo(String message, boolean defaultValue) {
        return requestYesNo(message, defaultValue, true);
    }

    public boolean requestYesNo(String message) {
        return requestYesNo(message, false, false);
    }

}
