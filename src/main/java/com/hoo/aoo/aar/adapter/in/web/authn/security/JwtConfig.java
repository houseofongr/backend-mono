package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.hoo.aoo.aar.adapter.out.jwt.JwtUtil;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    private final MACSigner macSigner;
    private final JwtProperties jwtProperties;

    public JwtConfig(@Value("${security.jwt.secret}") String secret,
                     @Value("${security.jwt.issuer}") String issuer,
                     @Value("${security.jwt.expire}") Long expire
    ) throws KeyLengthException {
        this.jwtProperties = new JwtProperties(secret, issuer, expire);
        this.macSigner = new MACSigner(jwtProperties.secret());
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(macSigner.getSecretKey()).build();
    }

    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil(macSigner, jwtProperties);
    }

}
