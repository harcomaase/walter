package de.mh.walter.entity.dao;

import de.mh.walter.entity.User;
import de.mh.walter.entity.UserAuthKey;
import de.mh.walter.entity.repository.UserAuthKeyRepository;
import de.mh.walter.entity.repository.UserRepository;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthKeyRepository userAuthKeyRepository;
    //
    private static final long UPDATE_AFTER = Duration.ofHours(1).toMillis();

    public User find(long userId) {
        return userRepository.findOne(userId);
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public User findByKey(String key) {
        UserAuthKey userAuthkey = userAuthKeyRepository.findByAuthenticationKey(key);
        if (userAuthkey == null) {
            return null;
        }
        if (userAuthkey.getLastUse() + UPDATE_AFTER < now()) {
            userAuthkey.setLastUse(now());
            userAuthKeyRepository.save(userAuthkey);
        }
        return userRepository.findOne(userAuthkey.getUserId());
    }

    public void addKeyToUser(User user, String key) {
        UserAuthKey userAuthKey = new UserAuthKey();
        userAuthKey.setAuthenticationKey(key);
        userAuthKey.setUserId(user.getId());
        long now = now();
        userAuthKey.setIssued(now);
        userAuthKey.setLastUse(now);
        userAuthKeyRepository.save(userAuthKey);
    }

    protected static long now() {
        return System.currentTimeMillis();
    }

    public List<User> findAll() {
        List<User> result = new LinkedList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }

    public void removeKey(String key) {
        UserAuthKey userAuthKey = userAuthKeyRepository.findByAuthenticationKey(key);
        if (userAuthKey != null) {
            userAuthKeyRepository.delete(userAuthKey);
        }
    }
}
