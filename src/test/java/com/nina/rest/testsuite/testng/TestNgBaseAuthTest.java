package com.nina.rest.testsuite.testng;

import com.nina.rest.client.AuthClient;
import com.nina.rest.client.http.HttpClient;
import com.nina.rest.client.http.HttpClientFactory;
import com.nina.rest.model.UserSession;
import com.nina.util.EnvironmentPropertyLoader;
import org.testng.annotations.BeforeClass;

public class TestNgBaseAuthTest {
    protected static HttpClient httpClient;
    protected static UserSession userSession;

    @BeforeClass
    public static void loginUser() {
        httpClient = HttpClientFactory.createHttpClient();
        var authClient = new AuthClient(httpClient);
        userSession = authClient.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }
}
