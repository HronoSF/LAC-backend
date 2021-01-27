package com.github.hronosf.services;

import javax.servlet.http.Cookie;
import java.util.List;

public interface AuthService {

    String buildAuthUrl(String redirectUrl);

    String buildLogoutUrl(String redirectUrl);

    List<Cookie> deleteCookies();

    List<Cookie> createCookiesFromCode(String code);

    List<Cookie> createCookiesFromRefreshToken(String refreshToken);
}
