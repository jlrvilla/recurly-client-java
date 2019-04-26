package com.recurly.v3;

import com.recurly.v3.http.HeaderInterceptor;
import com.recurly.v3.resources.*;
import com.recurly.v3.requests.*;

import okhttp3.OkHttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ClientTest {
    private static String siteId = "subdomain-mysubdomain";
    private static String apiKey = "myapikey";
    private static Client _client;

    @Test
    public void testCantInitializeWithoutSiteIdAndApiKey() {
        assertThrows(IllegalArgumentException.class, () -> {new Client("SITEID", null);});
        assertThrows(IllegalArgumentException.class, () -> {new Client("SITEID", "");});
        assertThrows(IllegalArgumentException.class, () -> {new Client(null, "APIKEY");});
        assertThrows(IllegalArgumentException.class, () -> {new Client("", "APIKEY");});
    }

    @Test
    public void testSomething() {

    }
}
