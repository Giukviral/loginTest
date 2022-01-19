package giuk.security;


import giuk.entity.AppUserRole;
import giuk.service.AppUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 300000;

  private final AppUserDetailsService appUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<AppUserRole> appUserRoles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth",
        appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getRole().name()))
            .filter(Objects::nonNull).collect(Collectors.toList()));
    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    byte[] keyBytes = Decoders.BASE64.decode(secretKey);

    return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
        .signWith(Keys.hmacShaKeyFor(keyBytes),
            SignatureAlgorithm.HS256) // 알고리즘에 따라 키의 미니멈 길이가 달라짐.
        .compact();
  }

  public Authentication getAuthentication(String token) {
    log.info("get authentication");
    UserDetails userDetails = appUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody()
        .getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) throws Exception {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.info("token is invalid");
      log.info(e.getMessage());
      throw new Exception("Expired or invalid JWT token");
    }
  }

}
