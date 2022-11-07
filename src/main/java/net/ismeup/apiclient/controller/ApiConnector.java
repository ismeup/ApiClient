package net.ismeup.apiclient.controller;

import net.ismeup.apiclient.exceptions.ConnectionFailException;
import net.ismeup.apiclient.model.*;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConnector {

    private ApiConnectionData connectionData;
    private String cookie;
    private TokenStorage tokenStorage;
    private boolean debugNetwork;

    public ApiConnector(ApiConnectionData connectionData, TokenStorage tokenStorage, boolean debugNetwork) {
        this.connectionData = connectionData;
        this.tokenStorage = tokenStorage;
        this.debugNetwork = debugNetwork;
    }

    public ApiConnector(ApiConnectionData connectionData, TokenStorage tokenStorage) {
        this(connectionData, tokenStorage, false);
    }

    private String getUrl(String remoteComponent, String operation) {
        return connectionData.getUrl().toString() + "/api/service/" + remoteComponent + "/operation/" + operation + "";
    }

    private void printDebug(String line) {
        if (debugNetwork) {
            System.out.println(line);
        }
    }

    public ApiResult postOperation(String remoteComponent, String operation, JSONObject parameters) {
        ApiResult apiResult = ApiResult.empty();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            printDebug(getUrl(remoteComponent, operation));
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(getUrl(remoteComponent, operation)).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-type", "application/json");
            if (cookie != null) {
                httpURLConnection.setRequestProperty("Cookie", cookie);
            }
            outputStream = httpURLConnection.getOutputStream();
            JSONObject jsonObject = parameters == null ? new JSONObject() : parameters;
            jsonObject.put("token", tokenStorage.getToken() == null ? "" : tokenStorage.getToken());
            printDebug(jsonObject.toString());
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.flush();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int c;
            while ( (c = inputStream.read()) != -1) {
                byteArrayOutputStream.write(c);
            }
            String requestResult = new String(byteArrayOutputStream.toByteArray());
            printDebug(requestResult);
            JSONObject requestResultJson = new JSONObject(requestResult);
            apiResult = new ApiResult(requestResultJson.optString("status", "error").equals("ok"), requestResultJson, false);
            String setCookie = httpURLConnection.getHeaderField("Set-cookie");
            if (setCookie != null && !setCookie.equals(cookie)) {
                cookie = setCookie;
            }
        } catch (IOException e) {
            if (connectionData.isDefault()) {
                System.out.println("Network error. Try again later");
            } else {
                System.out.println("Network error. Try again later or check connection URL");
            }
            apiResult = ApiResult.interrupted();
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {

            }
            try {
                inputStream.close();
            } catch (Exception e) {

            }
        }
        return apiResult;
    }

    public ApiResult postOperation(String remoteComponent, String operation) {
        return postOperation(remoteComponent, operation, null);
    }

    public boolean authenticate(LoginData loginData) throws ConnectionFailException {
        boolean returnValue = false;
        ApiResult apiResult = postOperation("login", "generate_token", new JSONObject().put("loginData", loginData.toJson()));
        if (!apiResult.isConnectionInterrupted()) {
            if (apiResult.isOk() && apiResult.getAnswer().optJSONObject("answer") instanceof JSONObject) {
                LoginAnswer loginAnswer = LoginAnswer.fromJson(apiResult.getAnswer().getJSONObject("answer"));
                if (loginAnswer.getCode() == 1) {
                    tokenStorage.setToken(loginAnswer.getToken());
                    returnValue = true;
                }
            }
        } else {
            throw new ConnectionFailException();
        }
        return returnValue;
    }
}
