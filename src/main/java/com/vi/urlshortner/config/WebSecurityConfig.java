package com.vi.urlshortner.config;

import com.vi.urlshortner.service.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
    .authorizeRequests()
    .antMatchers("/*").permitAll()
    .antMatchers("/login*").permitAll()
    //.anyRequest().authenticated()
    .and()
    .formLogin().and().logout().logoutSuccessUrl("/")
    .and().headers().frameOptions().sameOrigin();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser("user").password(passwordEncoder().encode("user")).roles(Role.USER.toString())
            .and()
            .withUser("admin").password(passwordEncoder().encode("admin")).roles(Role.ADMIN.toString());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return  new BCryptPasswordEncoder();
  }
}
