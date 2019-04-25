package com.recurly.v3;

import com.recurly.v3.http.HeaderInterceptor;
import com.recurly.v3.resources.*;
import com.recurly.v3.requests.*;

import okhttp3.OkHttpClient;
import okhttp3.Credentials;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;

import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

public class ClientTest {
    private static String siteId = "subdomain-mysubdomain";
    private static String apiKey = "myapikey";
    private static Client _client;
    private static MockWebServer server;

    @BeforeAll
    static void initAll() throws IOException {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(getAccountJson()));

        server.start();

        OkHttpClient okHttpClient = getClient(apiKey);

        _client = new Client(siteId, apiKey, okHttpClient);
        _client._setApiUrl(server.url("/").toString());
    }

    @Test
    public void testCantInitializeWithoutSiteIdAndApiKey() {
        assertThrows(IllegalArgumentException.class, () -> {new Client("SITEID", null);});
        assertThrows(IllegalArgumentException.class, () -> {new Client("SITEID", "");});
        assertThrows(IllegalArgumentException.class, () -> {new Client(null, "APIKEY");});
        assertThrows(IllegalArgumentException.class, () -> {new Client("", "APIKEY");});
    }

    @Test
    public void testFetchResource() throws IOException {
        Account account = _client.getAccount("code-aaron");
        assertEquals("aaron", account.getCode());
    }

    private static OkHttpClient getClient(final String apiKey)
    {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            });
            final String authToken = Credentials.basic(apiKey, "");
            final HeaderInterceptor headerInterceptor = new HeaderInterceptor(authToken, Client.API_VERSION);

            return builder
                    .addInterceptor(headerInterceptor)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getAccountJson() {
        return "" +
        "{\n" +
        "    \"id\": \"jcqpopq123e4\",\n" +
        "    \"object\": \"account\",\n" +
        "    \"code\": \"aaron\",\n" +
        "    \"parent_account_id\": null,\n" +
        "    \"bill_to\": \"self\",\n" +
        "    \"state\": \"active\",\n" +
        "    \"username\": \"\",\n" +
        "    \"email\": \"aaron@example.com\",\n" +
        "    \"cc_emails\": \"\",\n" +
        "    \"preferred_locale\": \"\",\n" +
        "    \"first_name\": \"Aaron\",\n" +
        "    \"last_name\": \"Du Monde\",\n" +
        "    \"company\": \"Du Monde Enterprises\",\n" +
        "    \"vat_number\": \"\",\n" +
        "    \"tax_exempt\": false,\n" +
        "    \"exemption_certificate\": null,\n" +
        "    \"address\": {\n" +
        "       \"phone\": \"555-555-5555\",\n" +
        "       \"street1\": \"123 Main St.\",\n" +
        "       \"street2\": null,\n" +
        "       \"city\": \"San Francisco\",\n" +
        "       \"region\": \"CA\",\n" +
        "       \"postal_code\": \"94110\",\n" +
        "       \"country\": \"US\"\n" +
        "    },\n" +
        "    \"shipping_addresses\": [],\n" +
        "    \"custom_fields\": [],\n" +
        "    \"created_at\": \"2019-01-01T17:53:16Z\",\n" +
        "    \"updated_at\": \"2019-04-23T16:50:36Z\",\n" +
        "    \"deleted_at\": null\n" +
        "}";
    }
    @AfterAll
    static void tearDownAll() throws IOException {
        server.shutdown();
    }
}
