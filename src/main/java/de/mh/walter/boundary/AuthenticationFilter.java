package de.mh.walter.boundary;

import de.mh.walter.control.Constants;
import de.mh.walter.control.UserController;
import de.mh.walter.entity.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    UserController userController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("hi from " + this.getClass().getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            sendError(response, 406);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String pathInfo = httpRequest.getPathInfo();
        System.out.println("resource requested: " + pathInfo);
        if (pathInfo == null) {
            sendError(response, 400);
            return;
        }
        if (pathInfo.startsWith(Constants.API_LOCATION)) {
            if (pathInfo.equals(AccountController.API_PATH + "/register")
                    || pathInfo.equals(AccountController.API_PATH + "/login")) {
                chain.doFilter(request, response);
                return;
            }

            if (!authorized(httpRequest)) {
                sendError(response, 403);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    protected void sendError(ServletResponse response, int error) throws IOException {
        ((HttpServletResponse) response).sendError(error);
    }

    @Override
    public void destroy() {
    }

    private boolean authorized(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return false;
        }
        User user = userController.findUserByKey(authorizationHeader);
        if (user == null) {
            System.out.println("no user found for key: " + authorizationHeader);
            return false;
        }
        System.out.println("found user " + user.getEmail() + " by key :)");
        httpRequest.setAttribute("user", user);
        return true;
    }

}
