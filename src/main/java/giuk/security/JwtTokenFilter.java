package giuk.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// request 당 1번 동작하는 필터.
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    String token = jwtTokenProvider.resolveToken(
        httpServletRequest); // resolve token 이 controller보다 선행
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        log.info("token is valid");
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        log.info("auth is : "+auth.toString());
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("auth is set");
      } else {
        log.info("token is un valid");
      }
    } catch (
        Exception ex) {
      SecurityContextHolder.clearContext();
      httpServletResponse.sendError(500, ex.getMessage()); //internal server error
      return;
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }

}