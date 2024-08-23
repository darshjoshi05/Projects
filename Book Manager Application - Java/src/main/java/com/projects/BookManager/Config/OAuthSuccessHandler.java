package com.projects.BookManager.Config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.projects.BookManager.Entities.Providers;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(OAuthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuth Success Handler");

        // To check which login provider is being used
        String loginProvider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        // This can be used to check the different data available from the login
        // providers
        // var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        // oauthUser.getAttributes().forEach((key, value) -> {
        // logger.info(key + " : " + value);
        // });

        // Saving the received data to database
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        User user = new User();

        user.setEmailVerified(true);
        user.setPassword("password");
        user.setVerified(true);

        if (loginProvider.equalsIgnoreCase("google")) {

            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setProfilePicLink(oAuth2User.getAttribute("picture").toString());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("Login from Google");

        } else if (loginProvider.equalsIgnoreCase("github")) {

            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setProfilePicLink(oAuth2User.getAttribute("avatar_url").toString());
            user.setName(oAuth2User.getAttribute("login").toString());
            user.setProvider(Providers.GITHUB);
            user.setAbout("Login from Github");

        } else {
            logger.info("OAuth Success Handler: Unknown Provider");
        }

        User user2 = userRepository.findByName(user.getName()).orElse(null);
        if (user2 == null) {
            userRepository.save(user);
            logger.info("User saved: " + user.getName());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
