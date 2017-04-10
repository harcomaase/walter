package de.mh.walter.boundary;

import de.mh.walter.control.UserController;
import de.mh.walter.entity.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserController userController;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("authentication request!");
        if (httpServletRequest == null) {
            error();
        }
        String authorizationHeader = httpServletRequest.getParameter("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            error();
        }
        User user = userController.findUserByKey(authorizationHeader);
        if (user == null) {
            error();
        }
        httpServletRequest.setAttribute("user", user);
        return auth;
    }

    private void error() throws BadCredentialsException {
        throw new BadCredentialsException("you shall not pass");
    }

    @Override
    public boolean supports(Class<?> type) {
        System.out.println("asked for support of: " + type.getName());
        return true;
    }

}
