package com.portfoliobaez.gnb.Config;

import com.portfoliobaez.gnb.Security.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        "/personas/crear",
                        "/personas/borrar/**",
                        "/personas/lista",
                        "/personas/editar/**",
                        "/personas/lista/perfil",
                        "/explad/create",
                        "/educacion/create"
                ).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth/login")
                .permitAll()
                .anyRequest()
                .permitAll();
    }
    
    @Override
    public void configure ( WebSecurity web ) throws Exception {
        web.ignoring()
          .antMatchers("/v2/api-docs",
                  "/experiencia/lista",
                  "/educacion/lista",
            "/configuration/ui",
            "/explab/update/**",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**");

        web.ignoring()
          .antMatchers(
            "/h2-console/**");
    }

}
