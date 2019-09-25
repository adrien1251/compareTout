package com.eltae.compareTout.security;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AuthenticationEntryPoint authEntryPoint;


    @Autowired
    public SecurityConfig(UserService userService, AuthenticationEntryPoint authEntryPoint) {
        this.userService = userService;
        this.authEntryPoint = authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable().cors().and()
            .authorizeRequests()
            .antMatchers(Routes.USERS + "/sign_in").permitAll()
            .anyRequest().permitAll()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authEntryPoint)
            .and()
            .logout()
            .logoutUrl(Routes.USERS + "sign_out")
            .logoutSuccessHandler(new AuthenticationLogoutSuccessHandler())
            .invalidateHttpSession(true)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * CORS configuration for the Application's security layer.
     * <p>
     * Allow all routes with any headers and methods.
     *
     * @return the {@link CorsConfigurationSource} for the Application's security layer.
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private class AuthenticationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


}