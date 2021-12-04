package com.api.power.repository.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthEntity {

    private String grant_type;
    private String client_id;
    private String client_secret;
    private String refresh_token;
    
}
