package com.nina.rest.client.http.apache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nina.rest.client.http.BasicAuth;
import com.nina.rest.client.http.HttpClient;
import com.nina.rest.client.http.HttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.utils.Base64;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApacheHttpClient implements HttpClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T post(String url,
                      Map<String, String> formParams,
                      BasicAuth basicAuth,
                      Class<T> responseClass) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(convertToNameValuePairList(formParams)));
            post.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader(basicAuth));

            return httpClient.execute(post, response -> {
                assertThat(response.getCode(), is(HttpStatus.SC_OK));
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(jsonResponse, responseClass);
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to send POST request: ", e);
        }
    }

    @Override
    public <T> T post(String url,
                      Map<String, String> pathParams,
                      Object body,
                      String oauthToken,
                      int expectedStatusCode,
                      Class<T> responseClass) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uriWithParams = buildUrlWithParams(url, pathParams, Map.of());
            HttpPost post = new HttpPost(uriWithParams);
            post.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);

            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            post.setEntity(entity);

            return httpClient.execute(post, response -> {
                assertThat(response.getCode(), is(expectedStatusCode));
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(jsonResponse, responseClass);
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to send POST request: ", e);
        }
    }

    @Override
    public HttpResponse get(String url, Map<String, String> pathParams, String oauthToken) {
        return get(url, pathParams, Map.of(), oauthToken);
    }

    @Override
    public <T> T get(String url,
                     Map<String, String> pathParams,
                     Map<String, String> queryParams,
                     String oauthToken,
                     int expectedStatusCode,
                     Class<T> responseClass) {
        var response = get(url, pathParams, queryParams, oauthToken);
        assertThat(response.getStatusCode(), is(expectedStatusCode));
        return response.as(responseClass);
    }

    private HttpResponse get(String url,
                             Map<String, String> pathParams,
                             Map<String, String> queryParams,
                             String oauthToken) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uriWithParams = buildUrlWithParams(url, pathParams, queryParams);
            HttpGet get = new HttpGet(uriWithParams);
            get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);
            return httpClient.execute(get, response -> {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return new ApacheResponseAdapter(jsonResponse, response.getCode());
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to send GET request: ", e);
        }
    }

    @Override
    public <T> T delete(String url, Map<String, String> pathParams, String oauthToken, Class<T> responseClass) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uriWithParams = buildUrlWithParams(url, pathParams, Map.of());
            HttpDelete delete = new HttpDelete(uriWithParams);
            delete.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);

            return httpClient.execute(delete, response -> {
                assertThat(response.getCode(), is(HttpStatus.SC_OK));
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(jsonResponse, responseClass);
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to send DELETE request: ", e);
        }
    }

    @Override
    public <T> T put(String url,
                     Map<String, String> pathParams,
                     Object body,
                     String oauthToken,
                     int expectedStatusCode,
                     Class<T> responseClass) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uriWithParams = buildUrlWithParams(url, pathParams, Map.of());
            HttpPut put = new HttpPut(uriWithParams);
            put.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken);

            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            put.setEntity(entity);

            return httpClient.execute(put, response -> {
                assertThat(response.getCode(), is(expectedStatusCode));
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return objectMapper.readValue(jsonResponse, responseClass);
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to send PUT request: ", e);
        }
    }

    private URI buildUrlWithParams(String url,
                                   Map<String, String> pathParams,
                                   Map<String, String> queryParams) throws URISyntaxException {
        var enrichedUrl = url;
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            enrichedUrl = enrichedUrl.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        URIBuilder uriBuilder = new URIBuilder(enrichedUrl);
        queryParams.forEach(uriBuilder::addParameter);
        return uriBuilder.build();
    }

    private String getBasicAuthHeader(BasicAuth basicAuth) {
        final String auth = basicAuth.username() + ":" + basicAuth.password();
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    private List<NameValuePair> convertToNameValuePairList(Map<String, String> formParams) {
        List<NameValuePair> urlParameters = new ArrayList<>();
        formParams.forEach((key, value) -> urlParameters.add(new BasicNameValuePair(key, value)));
        return urlParameters;
    }
}
