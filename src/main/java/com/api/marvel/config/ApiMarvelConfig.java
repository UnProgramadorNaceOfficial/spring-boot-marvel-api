package com.api.marvel.config;

import com.api.marvel.util.MD5Encoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiMarvelConfig {
    private MD5Encoder md5Encoder;

    public ApiMarvelConfig(MD5Encoder md5Encoder) {
        this.md5Encoder = md5Encoder;
    }

    private long timestamp = new Date(System.currentTimeMillis()).getTime();

    @Value("${integration.marvel.key.private}")
    private String privateKey;

    @Value("${integration.marvel.key.public}")
    private String publicKey;

    public Map<String, String> getAuthorizationParams(){
        String hashEncoded = this.md5Encoder.encode(String.valueOf(timestamp)
                                                          .concat(this.privateKey)
                                                          .concat(this.publicKey));

        Map<String, String> authorizationParams = new HashMap<>();
        authorizationParams.put("apikey", this.publicKey);
        authorizationParams.put("ts", String.valueOf(this.timestamp));
        authorizationParams.put("hash", hashEncoded);

        return authorizationParams;
    }
}
