package net.ismeup.apiclient.model;

import org.json.JSONObject;

public class ApiResult {
    private boolean connectionInterrupted;
    private boolean isOk;
    private JSONObject answer;

    public ApiResult(boolean isOk, JSONObject answer, boolean connectionInterrupted) {
        this.isOk = isOk;
        this.answer = answer;
        this.connectionInterrupted = connectionInterrupted;
    }

    public boolean isOk() {
        return isOk;
    }

    public JSONObject getAnswer() {
        return answer;
    }

    public boolean isConnectionInterrupted() {
        return connectionInterrupted;
    }

    public static ApiResult empty() {
        return new ApiResult(false, new JSONObject(), false);
    }

    public static ApiResult interrupted() {
        return new ApiResult(false, new JSONObject(), true);
    }
}
