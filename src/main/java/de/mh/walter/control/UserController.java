package de.mh.walter.control;

import de.mh.walter.entity.User;
import de.mh.walter.entity.dao.UserDao;
import java.util.Base64;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    private static final int KEY_SIZE = 32;
    private static final int SALT_ROUNDS = 10;

    @Autowired
    private UserDao userDao;

    public void createUser(String username, String password) {
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        String hash = BCrypt.hashpw(password, salt);
        User user = new User();
        user.setEmail(username);
        user.setPassword(hash);
        userDao.create(user);
    }

    public boolean userExists(String username) {
        return userDao.findByUsername(username) != null;
    }

    public boolean checkPassword(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPassword());
    }

    public User findUserByKey(String key) {
        return userDao.findByKey(key);
    }

    public String createAndPersistKey(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        Random random = new Random();
        byte[] buffer = new byte[KEY_SIZE];
        random.nextBytes(buffer);
        String key = Base64.getEncoder().encodeToString(buffer);
        userDao.addKeyToUser(user, key);
        return key;
    }

    public void removeKey(String key) {
        userDao.removeKey(key);
    }
}
