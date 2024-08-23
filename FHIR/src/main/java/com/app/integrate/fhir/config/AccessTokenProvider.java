package com.app.integrate.fhir.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class AccessTokenProvider implements TokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenProvider.class);

    private String clientAssertionType;
    private String clientSecret;
    private String accessTokenUrl;
    private RestTemplate restTemplate;

    private Token token;

    public AccessTokenProvider(RestTemplate restTemplate, String clientAssertionType, String clientSecret, String accessTokenUrl) {
        this.restTemplate = restTemplate;
        this.clientAssertionType = clientAssertionType;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;

        this.token = getToken();
    }

    private Token getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body("client_credentials"), headers);

        ResponseEntity<Token> result = restTemplate.postForEntity(accessTokenUrl, request, Token.class);
        return result.getBody();
    }

    private MultiValueMap<String, String> body(String grantType) {
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("grant_type", grantType);
        formParams.add("client_assertion_type", clientAssertionType);
        formParams.add("client_assertion", clientSecret);
        return formParams;
    }

    @Override
    @Cacheable(value = "tokens", key = "3600")
    public String getAccessToken() {
        return token.getAccessToken();
    }
}