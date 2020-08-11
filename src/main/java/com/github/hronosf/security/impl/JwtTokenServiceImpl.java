package com.github.hronosf.security.impl;

import com.github.hronosf.exceptions.TokenException;
import com.github.hronosf.model.payload.response.AuthTokensResponseDTO;
import com.github.hronosf.security.JwtTokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.refresh.secret}")
    private String secretRefresh;

    @Value("${jwt.token.expire}")
    private Long expireTimeMs;

    @Value("${jwt.refresh.expire}")
    private Long expireTimeMsRefresh;

    @Value("${jwt.header}")
    private String tokenPrefix;

    private final UserDetailsService userDetailsService;

    private static final Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{4}");

    @PostConstruct
    private void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        secretRefresh = Base64.getEncoder().encodeToString(secretRefresh.getBytes());
    }

    @Override
    public AuthTokensResponseDTO createAuthTokens(String email) {
        return AuthTokensResponseDTO.builder()
                .token(generateToken(email, false))
                .refreshToken(generateToken(email, true))
                .build();
    }

    @Override
    public AuthTokensResponseDTO refreshAuthTokens(String token, String refreshToken) {
        try {
            isTokenValid(token, false);
        } catch (TokenException te) {
            if (isTokenValid(refreshToken, true)) {
                return createAuthTokens(extractEmailFromToken(refreshToken, true));
            }
        }
        return null;
    }

    @Override
    public Authentication getAuthentication(String token) {
        isTokenValid(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractEmailFromToken(token, false));
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith(tokenPrefix)) {
            return header.replace(tokenPrefix, StringUtils.EMPTY);
        }

        throw new TokenException(StringUtils.EMPTY);
    }

    @Override
    public UserDetails getUserDetailsByToken(HttpServletRequest request) {
        String token = extractToken(request);
        isTokenValid(token);
        return userDetailsService.loadUserByUsername(extractEmailFromToken(token, false));
    }

    public boolean isTokenValid(String token) {
        return isTokenValid(token, false);
    }

    @SneakyThrows
    private boolean isTokenValid(String token, boolean isRefresh) {
        // check if token expired, if yes - return how much time ago
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(isRefresh ? secretRefresh : secret)
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());

        } catch (ExpiredJwtException exception) {
            Matcher matcher = pattern.matcher(exception.getMessage());
            Date expiredAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(matcher.group(0).replace("T", " "));

            String period = PeriodFormat.getDefault().print(new Period(expiredAt.getTime(), new Date().getTime()));
            throw new TokenException(String.format("Ваше сессия истекла %s минут назад, пожалуйста перезайдите в приложение!", period));
        }
    }

    private String generateToken(String email, boolean isRefresh) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", getRolesByAuthorities(userDetailsService.loadUserByUsername(email).getAuthorities()));
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + (isRefresh ? expireTimeMsRefresh : expireTimeMs));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, isRefresh ? secretRefresh : secret)
                .compact();
    }

    private List<String> getRolesByAuthorities(Collection<? extends GrantedAuthority> userAuthorities) {
        return userAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private String extractEmailFromToken(String token, boolean isRefresh) {
        return Jwts.parser()
                .setSigningKey(isRefresh ? secretRefresh : secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
