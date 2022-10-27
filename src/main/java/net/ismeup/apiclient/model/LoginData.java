package net.ismeup.apiclient.model;

import org.json.JSONObject;

public class LoginData {
    public String login;
    public String password;
    public String appName;
    public int lifetime;

    public LoginData(String login, String password, String appName, int lifetime) {
        this.login = login;
        this.password = password;
        this.appName = appName;
        this.lifetime = lifetime;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("appname", appName);
        jsonObject.put("lifetime", lifetime);
        return jsonObject;
    }
}
