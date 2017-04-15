package de.mh.walter.boundary;

import de.mh.walter.boundary.bean.TarpitBean;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class Tarpit {

    private static final Map<String, TarpitBean> TARPIT = new HashMap<>();
    //
    private static final int MAX_ATTEMPTS = 5;
    private static final Duration WAIT_TIME = Duration.ofMinutes(2L);
    //
    private static final Logger LOG = Logger.getLogger(Tarpit.class.getName());
    
    public static boolean tarpitOk(HttpServletRequest request) {
        String id = extractIdentifier(request);
        LOG.log(Level.FINE, "checking tarpit for {0}", id);
        synchronized (TARPIT) {
            TarpitBean bean = TARPIT.get(id);
            if (bean == null) {
                return true;
            }
            if (bean.getFailedAttempts() > MAX_ATTEMPTS) {
                if (bean.getLastFailedAttempt().plus(WAIT_TIME).isAfter(Instant.now())) {
                    LOG.log(Level.INFO, "too many failed attempts by {0}", id);
                    return false;
                }
                bean.setFailedAttempts(0);
            }
        }
        return true;
    }

    public static void registerFailedAttempt(HttpServletRequest request) {
        String id = extractIdentifier(request);
        LOG.log(Level.INFO, "failed attempt by {0}", id);
        synchronized (TARPIT) {
            TarpitBean bean = TARPIT.get(id);
            if (bean == null) {
                bean = new TarpitBean();
                TARPIT.put(id, bean);
            }
            bean.setFailedAttempts(bean.getFailedAttempts() + 1);
            bean.setLastFailedAttempt(Instant.now());
        }
    }

    private static String extractIdentifier(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
