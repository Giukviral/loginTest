package giuk.config;

import giuk.security.JwtTokenFilter;
import giuk.security.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfiguration extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilterConfiguration(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
    // security에 위 필터를 먼저 쓸 것이며, username password
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}