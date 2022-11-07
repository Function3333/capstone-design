package capstone_back.etc.jwt;

import capstone_back.etc.SecretKeyConst;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;


@Component
@NoArgsConstructor
public class JwtManager {
    //30분
    private long ACCESS_TOKEN_VALIDATiON_SECOND = 60 * 30;
    //3시간
    private long REFRESH_TOKEN_VALIDATiON_SECOND = 60 * 30 * 6;

    private String secretKey = SecretKeyConst.secretKey;


    public JwtManager(
            String secretKey
            , Long accessTokenValidationSecond
            , Long refreshTokenValidationSecond
    ) {
        this.secretKey = secretKey;
        this.ACCESS_TOKEN_VALIDATiON_SECOND = accessTokenValidationSecond;
        this.REFRESH_TOKEN_VALIDATiON_SECOND = refreshTokenValidationSecond;
    }

    private Key getSigninKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 해석
    public Claims validTokenAndReturnBody(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigninKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new InvalidParameterException("유효하지 않은 토큰입니다");
        }
    }

    // 유저id 조회
    public String getEmail(String token) {
        return validTokenAndReturnBody(token).get("email", String.class);
    }

    // 토큰 만료
    private Boolean isTokenExpired(String token){
        Date expiration = validTokenAndReturnBody(token).getExpiration();
        return expiration.before(new Date());
    }

    // access token 생성
    public String generateAccessToken(String email) {
        return doGenerateToken(email, ACCESS_TOKEN_VALIDATiON_SECOND * 1000l);
    }

    // refresh token 생성
    public String generateRefreshToken(String email) {
        return doGenerateToken(email, REFRESH_TOKEN_VALIDATiON_SECOND * 1000l);
    }

    // accessToken 유효시간 알림(second)
    public Long getValidationAccessTokenTime(){
        return ACCESS_TOKEN_VALIDATiON_SECOND;
    }

    private String doGenerateToken(String email, Long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }


}
