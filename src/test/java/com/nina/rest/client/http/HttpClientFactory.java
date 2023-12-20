package com.nina.rest.client.http;

import com.nina.rest.client.http.apache.ApacheHttpClient;
import com.nina.rest.client.http.restassured.RestAssuredHttpClient;
import com.nina.util.EnvironmentPropertyLoader;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

    public static HttpClient createHttpClient() {
        boolean useApacheHttpClient = BooleanUtils.toBoolean(EnvironmentPropertyLoader.getProperty("useApacheHttpClient"));
        logger.info("Using %s HTTP client".formatted(useApacheHttpClient ? "Apache" : "RestAssured"));
        return useApacheHttpClient ? new ApacheHttpClient() : new RestAssuredHttpClient();
    }
}
