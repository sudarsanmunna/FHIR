package com.app.integrate.fhir.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    // Define the expiration time of the token in milliseconds
    private static final long EXPIRATION_TIME = 300000;
    public static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCx1B/m1tB7HV7ZiceB+ehgC5TTCfrjy76tR4brCrpIUK6Fjup6AGAQc6n1txFy8e33WJYLN0RBtymE7c51xHyRZfZFjDBYuxATxL+otSWN6WQd5HbZ4n+OcSlk6yszWCr6Hu1zAyE/uZwStY5un4fhWplyHPn+1MSevHN/my5qz8RNpcAagmD9RkfoQsgzNrvxtbsvr9TXGBk0/e+nupk8lEbXRDpdMFMOkXQ3y6uqW4tOy7nvg6rq0CSqSV4A21niMCxr6DfIrkTr5OsyFZJU39OU6s+AYjNXOhmCX/Wpm50WsKh+hc9qyS/4ggBnX6HVfMlZb37oFOsBRqD1C5NpAgMBAAECggEAE/o7LUEzwaj+h1zRo3nyT5vtd7n2zBGPFcNj1lLps4cQ3CM+lz8ki6Sa0uZimPGx5Kg4jwEB84oCqZLXr2ZIAkOco5qIsect+rKPP4I+Ok+edjc83ORzpQp0dohu3YFJrri7v0B6Q0D0Viu8WRQl/Tyi4W187lJz1ktghFToJYqKbWoOi/P2brQVXc/A8ADsgnwkp1+5S18h+Lial4xdVUoZyUoY8fyMCxvcZLYqFaLyQHz0FgoIQhILIFW6yyo0gFzbWkLrgDUtvX1a4rHx63jwuA/qEd+/PNyaTab/rOCD0ayM9zd9jv4hbdNU20WDIfrEST1lvJ7X0Vl67QG/SwKBgQDzZjl2k5lYt0BM9oxHfRWKLZ+Xi/a/2dX4pOkD4CZzaMjPxFB0XgZRxok6l06kF6tdDnmpm2umnqSRsM/Sff3PY20UVtgjx+jWeE3AciUYzwlAEJlGhMgJEWUWAxdkvdST+/k577UwI0CP5BvX73ajlFcFTCTPJtwf62ZbcM6hOwKBgQC7COPzyChIzWclEpoWHNR9U3nW87M3niDAr/s26fxlDXtZv6mvot2CGg0TV3wvTjky6a6WF43B/G4nQkFMebQNvDT+53OQzvA8cfzbyJX2WlPQYhCLO58iKtsBgaMq2wvOLb2dv/t8KViXmN3ijuF8a6z67/k8nFPHEA8ZJkOTqwKBgQC0tVO6frEeVceOnInf1bV1RpQzfr3vCHs9RKqfrv69NrHI17bn/prwgdeGwamoWM5oo4Oecf7F0QjcBgr1+4bCP85PiH6mahiritwnIlf6iFQU1X4HBjRBz0Fey2LF785xJGWQJzE9kR6w9inZ8zcBEfRdwX+esPpDVJFFGIbA2wKBgCDFZwOA76YJ72esgbkcJhMrfC6wlpdJKp34d8DgcKvYF+Pa1EWE+ODh5aac7pJvV1BXZ5K+38S8kSRp1H0s2eKKNpLvRO17hNZidpaf5765hvP9Kr7qnHBlJ+h0qX1J0iC3HQE18T+JEw/E5iPbOyEM0MtLHtWC4p7/blXOwYpvAoGAOR6HpgdiDejV2LALg5ELpdQFK/TsmJfcdQ7YLLTB7ekAB2Yykg+Ab7QwhJdq2bIFzwqg0po+GfNiQN4uI3YtbYyJDCM/LsQCsG+Hj6KJNmgM3pG1ovJ+nE4W3QoDUZN7act7eXpScE4w+pERoIGhj1NE0xERoiuyMl1LjQugDoE=";
    public static final String TOKEN_ALGO = "RS384";
    public static final String TOKEN_TYPE = "JWT";
    public static final String CLIENT_ID = "ba6bbf5d-314e-40fc-a839-cf0fb4ca863c";
    public static final String TOKEN_URL = "https://fhir.epic.com/interconnect-fhir-oauth/oauth2/token";

    public static String createJwtSignedToken() throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);

        // Get the current time
        Date now = new Date();

        // Calculate the expiration time
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        // Create the header as a JSON object
        Map<String, Object> header = new HashMap<>();
        header.put("alg", TOKEN_ALGO);
        header.put("typ", TOKEN_TYPE);

        // Create the payload as a JSON object
        Map<String, Object> payload = new HashMap<>();
        payload.put("iss", CLIENT_ID);
        payload.put("sub", CLIENT_ID);
        payload.put("aud", TOKEN_URL);
        payload.put("jti", CLIENT_ID);
        payload.put("exp", expiryDate.getTime() / 1000);
        payload.put("nbf", now.getTime() / 1000);
        payload.put("iat", now.getTime() / 1000);

        // Create the token by concatenating the header, payload, and signature
        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .signWith(SignatureAlgorithm.RS384, privateKey)
                .compact();

        return token;
    }
}
