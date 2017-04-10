package de.mh.walter.boundary;

import java.util.Map;
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

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
        String errorText = response.getStatus() + " / " + HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
        Map<String, Object> errorMap = getErrorAttributes(request, false);
        System.out.println("error occurred: " + errorText + "; error: " + errorMap.get("error") + "; message: " + errorMap.get("message") + "");
        return errorText;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
