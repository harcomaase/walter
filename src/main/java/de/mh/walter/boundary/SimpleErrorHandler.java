package de.mh.walter.boundary;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class SimpleErrorHandler implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;
    //
    private static final String PATH = "/error";
    private static final Logger LOG = Logger.getLogger(SimpleErrorHandler.class.getName());

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
        String errorText = response.getStatus() + " / " + HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
        Map<String, Object> errorMap = getErrorAttributes(request, false);
        LOG.log(Level.WARNING, "error occurred: {0}; error: {1}; message: {2}", new Object[]{errorText, errorMap.get("error"), errorMap.get("message")});
        return errorText;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
