package de.mh.walter.boundary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleErrorHandler implements ErrorController {

    private static final String PATH = "/error";

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
        String errorText = response.getStatus() + " / " + HttpStatus.valueOf(response.getStatus()).getReasonPhrase();
        System.out.println("error occurred: " + errorText);
        return errorText;
    }
}
