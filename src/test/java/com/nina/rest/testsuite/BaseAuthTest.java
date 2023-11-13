package com.nina.rest.testsuite;

import com.nina.rest.client.AuthClient;
import com.nina.rest.model.UserSession;
import com.nina.util.EnvironmentPropertyLoader;
import org.junit.jupiter.api.BeforeAll;

public class BaseAuthTest {
    private static final AuthClient authClient = new AuthClient();
    protected static UserSession userSession;

    @BeforeAll
    public static void loginUser() {
        userSession = authClient.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }
}
