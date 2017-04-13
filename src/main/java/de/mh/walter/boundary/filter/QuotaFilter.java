package de.mh.walter.boundary.filter;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class QuotaFilter implements Filter {

    private static Calendar lastReset = Calendar.getInstance();
    private static final Map<String, Integer> IP2REQUESTS = new HashMap<>();
    private static final Object LOCK = new Object();
    //
    private static final int MAX_REQUESTS_PER_MINUTE = 10 * 60;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String remote = request.getRemoteAddr();
        System.out.println("checking quota for: " + remote);
        synchronized (LOCK) {
            pruneRequestMap();
            if (!quotaOK(remote)) {
                sendBadQuota(response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void pruneRequestMap() {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.MINUTE) == lastReset.get(Calendar.MINUTE)) {
            return;
        }
        System.out.println("pruning request map, size: " + IP2REQUESTS.size());
        IP2REQUESTS.clear();
        lastReset = now;
    }

    private boolean quotaOK(String remote) {
        Integer requests = IP2REQUESTS.get(remote);
        if (requests == null) {
            IP2REQUESTS.put(remote, 1);
            return true;
        }
        if (requests > MAX_REQUESTS_PER_MINUTE) {
            System.out.println("quota of " + MAX_REQUESTS_PER_MINUTE + " request per minute exceeded by: " + remote);
            return false;
        }
        int incremented = requests + 1;
        System.out.println("request no. " + incremented + " by " + remote);
        IP2REQUESTS.put(remote, incremented);
        return true;
    }

    private void sendBadQuota(ServletResponse response) throws IOException {
        ((HttpServletResponse) response).sendError(429);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("hi from " + this.getClass().getSimpleName());
    }

    @Override
    public void destroy() {
        System.out.println("bye from " + this.getClass().getSimpleName());
    }
}
