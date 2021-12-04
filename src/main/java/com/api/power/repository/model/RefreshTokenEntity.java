package com.api.power.repository.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RefreshTokenEntity {

    private String access_token;
    private String client_id;
    private Integer customer_number;
    private Integer expires_in;
    private Double issued_at;
    private String refresh_token;
    private Boolean revoked;
    private String scope;
    private String session_key;
    private String token_type;

}
