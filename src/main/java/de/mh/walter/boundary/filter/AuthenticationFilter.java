package de.mh.walter.boundary.filter;

import de.mh.walter.boundary.AccountController;
import de.mh.walter.control.Constants;
import de.mh.walter.control.UserController;
import de.mh.walter.entity.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    UserController userController;
    //
    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.log(Level.INFO, "hi from {0}", this.getClass().getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            sendError(response, 406);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String pathInfo = httpRequest.getPathInfo();
        LOG.log(Level.FINE, "resource requested: {0}", pathInfo);
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
        LOG.log(Level.INFO, "bye from {0}", this.getClass().getSimpleName());
    }

    private boolean authorized(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            return false;
        }
        User user = userController.findUserByKey(authorizationHeader);
        if (user == null) {
            LOG.log(Level.INFO, "no user found for key: {0}", authorizationHeader);
            return false;
        }
        LOG.log(Level.FINE, "found user {0} by key :)", user.getEmail());
        httpRequest.setAttribute("user", user);
        return true;
    }

}
