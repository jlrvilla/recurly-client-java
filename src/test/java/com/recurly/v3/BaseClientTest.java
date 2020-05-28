package com.recurly.v3;

import com.recurly.v3.exception.InternalServerException;
import com.recurly.v3.exception.InvalidApiKeyException;
import com.recurly.v3.exception.NotFoundException;
import com.recurly.v3.exception.TransactionException;
import com.recurly.v3.exception.ValidationException;
import com.recurly.v3.fixtures.MockClient;
import com.recurly.v3.fixtures.MockQueryParams;
import com.recurly.v3.fixtures.MyRequest;
import com.recurly.v3.fixtures.MyResource;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class BaseClientTest {

  @Test
  public void testMakeRequestWithResource() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> {
      Request request = i.getArgument(0);
      HttpUrl url = request.url();
      assertEquals("GET", request.method());
      assertEquals("/resources/code-aaron", url.url().getPath());
      return mCall;
    };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(200, "OK", getResponseJson()));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    final MyResource resource = client.getResource("code-aaron");

    assertEquals(MyResource.class, resource.getClass());
  }

  @Test
  public void testMakeRequestWithBody() throws IOException {
    final Call mCall = mock(Call.class);
    AtomicBoolean postCalled = new AtomicBoolean(false);
    AtomicBoolean putCalled = new AtomicBoolean(false);
    Answer answer = (i) -> {
      Request request = i.getArgument(0);
      HttpUrl url = request.url();
      switch (request.method()) {
        case "POST":
          assertEquals("/resources", url.url().getPath());
          postCalled.set(true);
          break;
        case "PUT":
          assertEquals("/resources/someId", url.url().getPath());
          putCalled.set(true);
          break;
        default:
          // Any other request method is a failure
          Assert.fail();
      }
      return mCall;
    };
    when(mCall.execute())
      .thenReturn(MockClient.buildResponse(200, "OK", getResponseJson()))
      .thenReturn(MockClient.buildResponse(200, "OK", getResponseJson()));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    final MyRequest newResource = new MyRequest();
    newResource.setMyString("aaron");

    final MyResource resource = client.createResource(newResource);
    assertEquals(MyResource.class, resource.getClass());
    assertEquals("aaron", resource.getMyString());
    assertTrue(postCalled.get());

    final MyResource anotherResource = client.updateResource("someId", newResource);
    assertEquals(MyResource.class, anotherResource.getClass());
    assertEquals("aaron", anotherResource.getMyString());
    assertTrue(putCalled.get());
  }

  @Test
  public void testMakeRequestWithoutResource() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> {
      Request request = i.getArgument(0);
      HttpUrl url = request.url();
      assertEquals("DELETE", request.method());
      assertEquals("/resources/resource-id", url.url().getPath());
      return mCall;
    };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(200, "OK", ""));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    client.removeResource("resource-id");
  }

  @Test
  public void testMakeRequestWithQueryParams() throws IOException {
    DateTime dateTime = new DateTime();

    final Call mCall = mock(Call.class);
    Answer answer = (i) -> {
      Request request = i.getArgument(0);
      HttpUrl url = request.url();
      assertEquals("Aaron", url.queryParameter("my_string"));
      assertEquals(dateTime.toString(), url.queryParameter("my_date_time"));
      assertEquals("1", url.queryParameter("my_integer"));
      assertEquals("2.3", url.queryParameter("my_float"));
      assertEquals("4.5", url.queryParameter("my_double"));
      assertEquals("6", url.queryParameter("my_long"));
      assertEquals(null, url.queryParameter("my_random"));
      assertEquals("[]", url.queryParameter("unsupported"));
      return mCall;
    };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(200, "OK", getResponseListJson()));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    final MockQueryParams qp = new MockQueryParams();
    qp.setMyString("Aaron");
    qp.setMyDateTime(dateTime);
    qp.setMyInteger(1);
    qp.setMyFloat(2.3f);
    qp.setMyDouble(4.5);
    qp.setMyLong(6L);
    qp.setMyRandom(null);
    qp.setUnsupported(new ArrayList<>());
    final Pager<MyResource> pager = client.listResources(qp);
    pager.getNextPage();
  }

  @Test
  public void testNonJsonError500() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    Headers headers = new Headers.Builder().build();
    MediaType contentType = MediaType.get("text/html; charset=UTF-8");
    when(mCall.execute()).thenReturn(MockClient.buildResponse(500, "Internal Server Error", "<html>badness</html>", headers, contentType));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    assertThrows(
        InternalServerException.class,
        () -> {
          client.getResource("code-aaron");
        });
  }

  @Test
  public void testInvalidApiKey() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(404, "Not Found", getErrorJson("invalid_api_key")));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    // This test is important because it ensures that application/json response errors are based on the json 
    // body's error type and not the status code based error
    assertThrows(
        InvalidApiKeyException.class,
        () -> {
          client.getResource("code-aaron");
        });
  }

  @Test
  public void testNotFoundError() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(404, "Not Found", getErrorJson("not_found")));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    assertThrows(
        NotFoundException.class,
        () -> {
          client.getResource("code-aaron");
        });
  }

  @Test
  public void testValidationError() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(422, "Unprocessable Entity", getErrorResponse("validation")));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    assertThrows(
        ValidationException.class,
        () -> {
          client.removeResource("code-aaron");
        });
  }

  @Test
  public void testTransactionError() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenReturn(MockClient.buildResponse(422, "Unprocessable Entity", getErrorResponse("transaction")));

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    TransactionException t = assertThrows(
            TransactionException.class,
            () -> {
              client.removeResource("code-aaron");
            });
    assertEquals("mbca9aaao6xr", t.getError().getTransactionError().getTransactionId());
  }

  @Test
  public void testNetworkError() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenThrow(new IOException());

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    assertThrows(
        NetworkException.class,
        () -> {
          client.getResource("code-aaron");
        });
  }

  @Test
  public void testNetworkErrorWithoutResource() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenThrow(new IOException());

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);
    assertThrows(
        NetworkException.class,
        () -> {
          client.removeResource("code-aaron");
        });
  }

  @Test
  public void testBadMethodError() throws IOException {
    final Call mCall = mock(Call.class);
    Answer answer = (i) -> { return mCall; };
    when(mCall.execute()).thenThrow(new IOException());

    OkHttpClient mockOkHttpClient = MockClient.getMockOkHttpClient(answer);

    final MockClient client = new MockClient("apiKey", mockOkHttpClient);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          client.badRequestMethod();
        });
  }

  @Test
  public void testSetApiUrl() {
    final MockClient client = new MockClient("apiKey");
    final String newApiUrl = "https://my.base.url/";
    client._setApiUrl(newApiUrl);

    assertEquals(newApiUrl, client.getApiUrl());
  }

  @Test
  public void testCantSetApiUrlWithoutRecurlyInsecure() throws Exception {
    final HashMap<String, String> environmentVariables = new HashMap<String, String>();
    environmentVariables.put("RECURLY_INSECURE", "false");
    setEnv(environmentVariables);
    final MockClient client = new MockClient("apiKey");
    final String originalUrl = client.getApiUrl();
    final String newApiUrl = "https://my.base.url/";
    client._setApiUrl(newApiUrl);

    assertEquals(originalUrl, client.getApiUrl());

    environmentVariables.clear();
    environmentVariables.put("RECURLY_INSECURE", "true");
    setEnv(environmentVariables);
  }

  @Test
  public void testInterpolatePathWithoutParams() {
    final MockClient client = new MockClient("apiKey");
    final String path = "/accounts";
    final String interpolatedPath = client.interpolatePath(path);

    assertEquals("/accounts", interpolatedPath);
  }

  @Test
  public void testInterpolatePathWithParams() {
    final MockClient client = new MockClient("apiKey");
    final String path = "/accounts/{account_id}/notes/{account_note_id}";
    final HashMap<String, String> urlParams = new HashMap<String, String>();
    urlParams.put("account_id", "accountId/");
    urlParams.put("account_note_id", "noteId,");
    final String interpolatedPath = client.interpolatePath(path, urlParams);

    assertEquals("/accounts/accountId%2F/notes/noteId%2C", interpolatedPath);
  }

  @Test
  public void testInterpolatePathValidations() {
    final MockClient client = new MockClient("apiKey");
    final String path = "/accounts/{account_id}/notes/{account_note_id}";
    final HashMap<String, String> urlParams = new HashMap<String, String>();
    urlParams.put("account_id", "");
    urlParams.put("account_note_id", "");

    assertThrows(
        RecurlyException.class,
        () -> {
          client.interpolatePath(path, urlParams);
        });
  }

  protected static void setEnv(Map<String, String> newenv) throws Exception {
    try {
      Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
      Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
      theEnvironmentField.setAccessible(true);
      Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
      env.putAll(newenv);
      Field theCaseInsensitiveEnvironmentField =
          processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
      theCaseInsensitiveEnvironmentField.setAccessible(true);
      Map<String, String> cienv =
          (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
      cienv.putAll(newenv);
    } catch (NoSuchFieldException e) {
      Class[] classes = Collections.class.getDeclaredClasses();
      Map<String, String> env = System.getenv();
      for (Class cl : classes) {
        if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
          Field field = cl.getDeclaredField("m");
          field.setAccessible(true);
          Object obj = field.get(env);
          Map<String, String> map = (Map<String, String>) obj;
          map.clear();
          map.putAll(newenv);
        }
      }
    }
  }

  private static String getResponseJson() {
    return "{ \"my_string\": \"aaron\" }";
  }

  private static String getResponseListJson() {
    return ""
        + "{"
        + "\"object\":\"list\","
        + "\"has_more\":false,"
        + "\"next\":null,"
        + "\"data\": ["
        + "{"
        + "\"my_string\":\"Du Monde\""
        + "},"
        + "]"
        + "}";
  }

  private static String getErrorJson(String exception) {
    return ""
        + "{\n"
        + "    \"error\": {\n"
        + "       \"type\":"
        + exception
        + ",\n"
        + "       \"message\": \"Resource has an error\",\n"
        + "       \"params\": [\n"
        + "           {\"param\":\"some_param\"}\n"
        + "        ]\n"
        + "    }\n"
        + "}";
  }

  private static String getErrorResponse(String exception) {
    InputStream resource = null;

    if ("validation".equals(exception)) {
      resource = BaseClientTest.class.getResourceAsStream("/errors/validationError.json");
    } else if ("transaction".equals(exception)) {
      resource = BaseClientTest.class.getResourceAsStream("/errors/transactionError.json");
    }

    if (resource != null) {
      try {
        return IOUtils.toString(resource, StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new IllegalStateException(e.getMessage(), e);
      }
    }

    return getErrorJson(exception);
  }
}
