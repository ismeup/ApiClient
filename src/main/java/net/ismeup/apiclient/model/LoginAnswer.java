package net.ismeup.apiclient.model;

import org.json.JSONObject;

public class LoginAnswer {
    public String message;
    public int code;
    public String token;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public static LoginAnswer fromJson(JSONObject jsonObject) {
        LoginAnswer loginAnswer = new LoginAnswer();
        loginAnswer.message = jsonObject.optString("message", "");
        loginAnswer.code = jsonObject.optInt("code", 0);
        loginAnswer.token = jsonObject.optString("token", "");
        return loginAnswer;
    }

}
