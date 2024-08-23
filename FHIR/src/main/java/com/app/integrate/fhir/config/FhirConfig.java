package com.app.integrate.fhir.config;

import ca.uhn.fhir.rest.client.interceptor.SimpleRequestHeaderInterceptor;
import com.app.integrate.fhir.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

@Configuration
public class FhirConfig {

    @Value("${fhir.clientAssertionType}")
    private String clientAssertionType;
    @Value("${fhir.serverBase}")
    private String fhirServerBase;
    @Value("${fhir.authTokenProvider}")
    private String accessTokenUrl;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public AccessTokenProvider accessTokenProvider(RestTemplate restTemplate) throws Exception {
        return new AccessTokenProvider(restTemplate, this.clientAssertionType, JwtTokenUtil.createJwtSignedToken(), this.accessTokenUrl);
    }

    @Bean
    public FhirContext fhirContext() {
        return FhirContext.forR4();
    }

    @Bean
    public IGenericClient fhirClient(@Autowired FhirContext fhirContext, AccessTokenProvider tokenProvider) {
        IGenericClient client = fhirContext.newRestfulGenericClient(fhirServerBase);
        client.registerInterceptor(new BearerTokenAuthInterceptor(tokenProvider));
        client.registerInterceptor(new SimpleRequestHeaderInterceptor("Accept","application/json+fhir"));
        client.registerInterceptor(new LoggingInterceptor(true));
        return client;
    }
}