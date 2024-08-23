package com.projects.BookManager.Helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.var;

public class LoginHelper {

    public static String getUsernameOfLoggedUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            var loginProvider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

            String username = "";

            var user = (DefaultOAuth2User) authentication.getPrincipal();

            if (loginProvider.equalsIgnoreCase("google")) {

                username = user.getAttribute("name").toString();

            } else if (loginProvider.equalsIgnoreCase("github")) {

                username = user.getAttribute("login").toString();

            }

            return username;

        } else {
            return authentication.getName();
        }
    }
}
