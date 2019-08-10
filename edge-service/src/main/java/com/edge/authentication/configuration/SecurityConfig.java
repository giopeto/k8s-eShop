package com.edge.authentication.configuration;

import com.edge.authentication.V1.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import static com.edge.authentication.common.AuthenticationConstants.AUTHENTICATION_BASE_URL;
import static com.edge.authentication.common.SecurityRoles.ROLE_ADMIN;
import static com.edge.authentication.common.SecurityRoles.ROLE_USER;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.GET;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REMEMBER_ME_KEY = "remember-me-key";

    @Autowired
    private UsersService usersService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices(REMEMBER_ME_KEY, usersService);
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .eraseCredentials(true)
                .userDetailsService(usersService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
                .antMatchers(POST, "/" + AUTHENTICATION_BASE_URL + "/users/**").permitAll()
                .antMatchers(GET, "/" + AUTHENTICATION_BASE_URL + "/users/**").permitAll()
                .antMatchers(GET, "/store-service/store/**").hasAnyRole(ROLE_ADMIN.getName(), ROLE_USER.getName())
                .antMatchers(GET, "/zuul/files-service/files/**").hasAnyRole(ROLE_ADMIN.getName(), ROLE_USER.getName())
                .anyRequest().hasRole(ROLE_ADMIN.getName())
            .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(SC_UNAUTHORIZED))
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key(REMEMBER_ME_KEY)
            .and()
                .cors()
            .and()
                .csrf().disable();
    }
}