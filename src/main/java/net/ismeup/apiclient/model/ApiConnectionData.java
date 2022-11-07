package net.ismeup.apiclient.model;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiConnectionData {

    private static final String DEFAULT_URL = "https://ismeup.net";

    private URL url;

    private boolean isDefault = false;

    private ApiConnectionData(URL url, boolean isDefault) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public static ApiConnectionData defaultUrl() {
        URL url = null;
        try {
            url = new URL(DEFAULT_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return new ApiConnectionData(url, true);
    }

    public static ApiConnectionData parse(String urlParameter) throws MalformedURLException {
        URL url = null;
        url = new URL(urlParameter);
        return new ApiConnectionData(url, false);
    }

    public boolean isDefault() {
        return isDefault;
    }
}
