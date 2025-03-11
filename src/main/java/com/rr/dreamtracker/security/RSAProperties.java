package com.rr.dreamtracker.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class RSAProperties {
    private String privateKey;
    private String publicKey;
}
