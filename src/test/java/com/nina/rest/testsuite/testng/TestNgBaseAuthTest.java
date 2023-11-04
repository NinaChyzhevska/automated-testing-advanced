package com.nina.rest.testsuite.testng;

import com.nina.rest.client.AuthClient;
import com.nina.rest.model.UserSession;
import com.nina.util.EnvironmentPropertyLoader;
import org.testng.annotations.BeforeClass;

public class TestNgBaseAuthTest {
    private static final AuthClient authClient = new AuthClient();
    protected static UserSession userSession;

    @BeforeClass
    public static void loginUser() {
        userSession = authClient.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }
}
