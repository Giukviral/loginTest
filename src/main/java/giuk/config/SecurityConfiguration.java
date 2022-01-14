package giuk.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws  Exception{
    http.csrf().disable() //cross site request forgery disable
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and() // 쿠키에 세션 저장하지 않음. stateless를 위해.
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll() //브라우저에서 alive check 통과
        .antMatchers("/login").permitAll()
        .antMatchers("/signup").permitAll()
        .anyRequest().authenticated();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new PasswordEncoder() { //이게 없으면 비밀번호를 null로 만들어버림
      @Override
      public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.compareTo(rawPassword.toString())==0;
      }
    };
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}

//회원 가입 -> 토큰
//토큰 -> 검증 -> 회원 반환