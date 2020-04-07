package com.Bostic.BosticApp.security;

public class SecurityConstants {
    public static final String SECRET = "HopefullyChangedOnProduction";
    public static final long EXPIRATION_TIME = 3_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CREATE_ACCOUNT_URL = "userAccounts/create";
}
