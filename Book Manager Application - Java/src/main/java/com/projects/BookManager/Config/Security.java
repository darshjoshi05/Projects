package com.projects.BookManager.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.projects.BookManager.Services.Implementation.SecurityCustomUserDetailService;

@Configuration
public class Security {

    // User login using in memory data and not from database
    // UserDetails user = User
    // .withDefaultPasswordEncoder()
    // .username("admin")
    // .password("12345")
    // .roles("ADMIN", "USER")
    // .build();

    // @Bean
    // public UserDetailsService userDetailsService() {
    // return new InMemoryUserDetailsManager(user);
    // }

    @Autowired
    SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthSuccessHandler handler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(auth -> {
            // give access to those pages which are mentioned like /home, /regsiter, etc.
            // auth.requestMatchers("/home", "/register").permitAll();

            // secures all request from /user and permits all other requests
            auth.requestMatchers("/user/**").authenticated();
            auth.anyRequest().permitAll();
        });

        // Since we changed default configuration of security, we have to give below
        // defalut form
        // httpSecurity.formLogin(Customizer.withDefaults());

        // Your owm form
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/log-in")
                    .loginProcessingUrl("/authenticate")
                    .successForwardUrl("/user/dashboard")
                    .failureForwardUrl("/log-in?error=true")
                    .usernameParameter("name")
                    .passwordParameter("password");

            // .successHandler(new AuthenticationSuccessHandler() {

            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            //
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // })
            // .failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            //
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }

            // });
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logOutForm -> {
            logOutForm.logoutUrl("/log-out")
                    .logoutSuccessUrl("/log-in?logout=true");

        });

        // Oauth Configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/log-in");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
