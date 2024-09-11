package com.megazone.ERPSystem_phase2_Backend.common.config.security;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtUtil 클래스
 *
 * JWT 토큰 생성 및 검증을 위한 유틸리티 클래스.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;  // JWT 서명에 사용되는 비밀키

    @Value("${jwt.expiration}")
    private long expiration;  // 토큰 만료 시간 (초 단위)

    /**
     * JWT 토큰에서 사용자 이름을 추출
     *
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * JWT 토큰에서 만료 시간을 추출
     *
     * @param token JWT 토큰
     * @return 토큰 만료 시간
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * JWT 토큰에서 클레임을 추출
     *
     * @param token JWT 토큰
     * @param claimsResolver 클레임 처리 함수
     * @param <T> 클레임의 타입
     * @return 추출된 클레임 값
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);  // 모든 클레임 추출
        return claimsResolver.apply(claims);  // 클레임 처리 함수에 전달
    }

    /**
     * 토큰에서 모든 클레임을 추출
     *
     * @param token JWT 토큰
     * @return 추출된 클레임
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)  // 서명에 사용된 비밀키 설정
                .parseClaimsJws(token)  // 토큰 검증 및 파싱
                .getBody();  // 클레임 내용 반환
    }

    /**
     * 토큰 만료 여부 확인
     *
     * @param token JWT 토큰
     * @return 토큰 만료 여부
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // 현재 시간과 비교하여 만료 여부 확인
    }

    /**
     * 사용자 이름과 닉네임을 포함한 JWT 토큰 생성
     *
     * @param username 사용자 이름
     * @param userNickname 사용자 닉네임
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String username, String userNickname) {
        Map<String, Object> claims = new HashMap<>();  // 기본 클레임 설정
        claims.put("userNickname", userNickname);  // 사용자 닉네임 추가
        return createToken(claims, username);  // 토큰 생성
    }

    /**
     * JWT 토큰 생성 로직
     *
     * @param claims 클레임 정보
     * @param subject 토큰 주체 (사용자 이름)
     * @return 생성된 JWT 토큰
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)  // 클레임 설정
                .setSubject(subject)  // 토큰 주체 설정
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secret)  // 서명 알고리즘과 비밀키 설정
                .compact();  // 토큰 생성 및 반환
    }

    /**
     * JWT 토큰의 유효성 검증
     *
     * @param token JWT 토큰
     * @param username 사용자 이름
     * @return 유효성 검증 결과
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);  // 토큰에서 사용자 이름 추출
        return (extractedUsername.equals(username) && !isTokenExpired(token));  // 사용자 이름과 만료 여부 확인
    }
}