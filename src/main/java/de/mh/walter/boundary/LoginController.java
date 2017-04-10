package de.mh.walter.boundary;

import de.mh.walter.boundary.bean.LoginRequest;
import de.mh.walter.boundary.bean.LoginResponse;
import de.mh.walter.boundary.bean.RegisterRequest;
import de.mh.walter.boundary.bean.RegisterResponse;
import de.mh.walter.control.UserController;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "rest")
public class LoginController {

    @Autowired
    private UserController userController;
    //
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9\\.\\-\\_]+@[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z0-9]{2,64}$");

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        
        System.out.println("register request for user: " + username);

        if (username == null || username.length() < 5 || !validEmailAddress(username) || userController.userExists(username)) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setMessage("invalid username");
            errorResponse.setSuccess(false);
            return errorResponse;
        }
        if (password == null || password.length() < 8) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setMessage("password too short");
            errorResponse.setSuccess(false);
            return errorResponse;
        }
        userController.createUser(username, password);
        RegisterResponse errorResponse = new RegisterResponse();
        errorResponse.setMessage(null);
        errorResponse.setSuccess(true);
        return errorResponse;
    }

    protected boolean validEmailAddress(String username) {
        return EMAIL_PATTERN.matcher(username).matches();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        System.out.println("login request for user: " + username);
        if (username == null || password == null) {
            return error("invalid input");
        }
        if (userController.checkPassword(username, password)) {
            String key = userController.createAndPersistKey(username);
            if (key == null) {
                return error("strange things happen");
            }
            LoginResponse response = new LoginResponse();
            response.setKey(key);
            response.setMessage("OK");
            return response;
        }
        return error("unauthorized");
    }

    private LoginResponse error(String message) {
        LoginResponse response = new LoginResponse();
        response.setKey(null);
        response.setMessage(message);
        return response;
    }

}
