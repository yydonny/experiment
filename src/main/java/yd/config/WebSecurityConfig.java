package yd.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    // completely ignore URLs starting with /public/
    web.ignoring().antMatchers("/vendor/**", "/http/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // set up security filter for session-based auth
        .anyRequest().authenticated()

        .and()

        .formLogin() // use a form page for login, also make all un-authed access redirected to it
        .loginPage("/login") // the route for the login page, just a normal spring mvc controller
        .permitAll() // the login url should be publicly accessible

        .and()

        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .deleteCookies("some-cookie");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("yangyd").password("asdf1234").roles("USER").and()
        .withUser("admin").password("asdf1234").roles("USER", "ADMIN");
  }

}
