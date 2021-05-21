package br.com.zupacademy.fabio.proposta.shared.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

interface Scopes{
    String propostas = "SCOPE_propostas";
    String transacoes = "SCOPE_transacoes";
    String cartoes = "SCOPE_cartoes";
}

@Configuration
@EnableScheduling
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/propostas/**").hasAuthority(Scopes.propostas)
                .antMatchers(HttpMethod.POST, "/v1/propostas/**").hasAuthority(Scopes.propostas)
                .antMatchers(HttpMethod.GET, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .antMatchers(HttpMethod.POST, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .antMatchers(HttpMethod.DELETE, "/v1/cards/*/transactions").hasAuthority(Scopes.transacoes)
                .antMatchers(HttpMethod.GET, "/v1/cards/**").hasAuthority(Scopes.cartoes)
                .antMatchers(HttpMethod.POST, "/v1/cards/**").hasAuthority(Scopes.cartoes)
                .anyRequest().authenticated()
                .and().csrf().disable()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
