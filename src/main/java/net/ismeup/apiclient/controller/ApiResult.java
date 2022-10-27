package net.ismeup.apiclient.controller;

import org.json.JSONObject;

public class ApiResult {
    private boolean isOk;
    private JSONObject answer;

    public ApiResult(boolean isOk, JSONObject answer) {
        this.isOk = isOk;
        this.answer = answer;
    }

    public boolean isOk() {
        return isOk;
    }

    public JSONObject getAnswer() {
        return answer;
    }

    public static ApiResult empty() {
        return new ApiResult(false, new JSONObject());
    }
}
