package com.api.power.repository;

import com.api.power.repository.model.RefreshTokenEntity;
import com.api.power.repository.model.SessionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class ElectricKiwiRepository {

    @Value("${electric-kiwi.base-path}")
    private String basePath;

    @Value("${electric-kiwi.endpoint.consumption-averages}")
    private String consumptionAveragesEndpoint;

    @Value("${electric-kiwi.endpoint.session}")
    private String sessionEndpoint;

    @Value("${electric-kiwi.oauth.uri}")
    private String oauthUri;

    @Value("${electric-kiwi.oauth.client-id}")
    private String oauthClientId;

    @Value("${electric-kiwi.oauth.client-secret}")
    private String oauthClientSecret;

    @Getter @Setter
    @Value("${electric-kiwi.oauth.refresh-token}")
    private String oauthRefreshToken;

    public static final String GRANT_TYPE = "refresh_token";

    private String accessToken;
    private LocalDateTime accessTokenExpiry;

    private final RestTemplate restTemplate;

    public ElectricKiwiRepository() {
        this.restTemplate = setupRestTemplate();
    }

    public JSONObject getConsumptionAverages(
            final String customer,
            final String icpId,
            final LocalDate startDate,
            final LocalDate endDate,
            final String groupBy
    ) {
        getAccessToken();

        String url = basePath + consumptionAveragesEndpoint;
        url += "?start_date=" + startDate;
        url += "&end_date=" + endDate;
        url += "&group_by=" + groupBy;

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        final Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("customer", customer);
        uriVariables.put("icpId", icpId);

        try {
            final ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, uriVariables);

            return new JSONObject(response.getBody());
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public SessionEntity getSession() {
        getAccessToken();

        final String url = basePath + sessionEndpoint;

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, SessionEntity.class).getBody();
        } catch (final Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    public void getAccessToken() {
        final LocalDateTime oneMinuteAhead = LocalDateTime.now().minusMinutes(1);

        if (accessTokenExpiry == null || accessTokenExpiry.isBefore(oneMinuteAhead)) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(oauthClientId, oauthClientSecret);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            final MultiValueMap<String, String> content = new LinkedMultiValueMap<>();
            content.add("grant_type", GRANT_TYPE);
            content.add("client_id", oauthClientId);
            content.add("client_secret", oauthClientSecret);
            content.add("refresh_token", oauthRefreshToken);
            final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(content, headers);

            try {
                final RefreshTokenEntity refreshToken = restTemplate.postForObject(oauthUri, request, RefreshTokenEntity.class);
                this.accessToken = refreshToken.getAccess_token();
                this.oauthRefreshToken = refreshToken.getRefresh_token();
                final LocalDateTime now = LocalDateTime.now();
                final Integer expiresIn = refreshToken.getExpires_in();
                this.accessTokenExpiry = now.plusMinutes(expiresIn);
                log.info("Bearer token: " + accessToken + ". Refresh token: " + oauthRefreshToken);
            } catch (final Exception ex) {
                log.error(ex.getMessage());
                throw ex;
            }
        }
    }

    private RestTemplate setupRestTemplate() {
        final CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectionRequestTimeout(10000);
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(60000);

        return new RestTemplate(factory);
    }

}