package com.zpop.web.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
	@JsonProperty("access_token")
    private String accessToken;
	@JsonProperty("token_type")
    private String tokenType;
	@JsonProperty("refresh_token")
    private String refreshToken;
	@JsonProperty("id_token")
    private String idToken;
	@JsonProperty("expires_in")
    private int expiresIn;
    private String scope;
    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}
	public void setRefreshTokenExpiresIn(int refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
