package com.victorfisyuk.spring.security.oauth2.logindemo.config;

import com.victorfisyuk.spring.security.oauth2.logindemo.service.LocalUserLinkedOAuth2UserService;
import com.victorfisyuk.spring.security.oauth2.logindemo.service.LocalUserLinkedOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig<S extends Session> extends WebSecurityConfigurerAdapter {
    @Autowired
    private LocalUserLinkedOAuth2UserService localUserLinkedOAuth2UserService;

    @Autowired
    private LocalUserLinkedOidcUserService localUserLinkedOidcUserService;

    @Autowired
    private FindByIndexNameSessionRepository<S> sessionRepository;

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions()
                        .disable()
                .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(localUserLinkedOAuth2UserService)
                        .oidcUserService(localUserLinkedOidcUserService)
                    .and()
                .and()
                .formLogin()
                .and()
                .sessionManagement()
                    .sessionConcurrency(sc -> sc.sessionRegistry(sessionRegistry()));
    }
    // @formatter:on

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        var bcryptId = "bcrypt";
        return new DelegatingPasswordEncoder(bcryptId, Map.ofEntries(
                Map.entry(bcryptId, new BCryptPasswordEncoder())
        ));
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }
}
