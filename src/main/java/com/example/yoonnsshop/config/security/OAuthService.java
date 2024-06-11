package com.example.yoonnsshop.config.security;

import com.example.yoonnsshop.domain.members.entity.Member;
import com.example.yoonnsshop.domain.members.repository.MemberRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OAuthService {
    private final RestTemplate restTemplate = new RestTemplate();
    private MemberRepository memberRepository;

    @Autowired
    public OAuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUri;

    String tokenUri = "https://oauth2.googleapis.com/token";
    String resourceUri = "https://www.googleapis.com/oauth2/v2/userinfo";

    public Member googleLogin(String code) {
        String accessToken = getAccessToken(code);
        JsonNode userResourceNode = getUserResource(accessToken);

        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();

        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return memberRepository.save(Member.Builder.aMember()
                    .withEmail(email)
                    .withName(nickname)
                    .build());
        }

        return member.get();
    }

    private String getAccessToken(String authorizationCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.postForEntity(tokenUri, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
