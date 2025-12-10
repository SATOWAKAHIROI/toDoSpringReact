package com.example.backend.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import org.slf4j.Logger;
/**
 * JWTトークンの生成・検証を行うコンポーネント
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}")
    private long validityInMilliseconds;

    /**
     * トークンからUserIdを取得するメソッド
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.get("userId", Long.class);
    }

    /**
     * トークンからメールアドレスを取得するメソッド
     * @param token
     * @return
     */
    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        
        return claims.getSubject();
    }
    /**
     * JWTトークンを検証する
     * 
     * @param token JWTトークン
     * @return トークンが有効な場合true、無効な場合false
     */
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

            logger.debug("JWTトークンの検証に成功しました");
            return true;
        }catch (SecurityException | MalformedJwtException e) {
            logger.error("無効なJWT署名です: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("期限切れのJWTトークンです: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("サポートされていないJWTトークンです: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWTトークンが空です: {}", e.getMessage());
        }

        return false;
    }

    /**
     * JWTトークンから認証情報を作成する
     * 
     * @param token JWTトークン
     * @return 認証情報
     */
    public Authentication getAuthentication(String token){
        String email = getEmailFromToken(token);
        Long userId = getUserIdFromToken(token);

        UserPrincipal principal = new UserPrincipal(userId, email);

        return new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
    }

    /**
     * JWTトークンを生成する
     * 
     * @param userId ユーザーID
     * @param email メールアドレス
     * @return JWTトークン
     */
    public String generateToken(Long userId, String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);
        
        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * 署名用の秘密鍵を取得する
     * @return 秘密鍵
     */
    private SecretKey getSigningKey(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 認証ユーザーを保持するクラス
     */
    public static class UserPrincipal {
        private final Long userId;
        private final String email;

        public UserPrincipal(Long userId, String email){
            this.userId = userId;
            this.email = email;
        }

        public Long getUserId(){
            return userId;
        }

        public String getEmail(){
            return email;
        }
    }

}
