package com.recurly.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JsonSerializerTest {

    @Test
    public void testSomething() {

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
}
