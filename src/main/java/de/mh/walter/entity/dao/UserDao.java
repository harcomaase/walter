package de.mh.walter.entity.dao;

import de.mh.walter.entity.User;
import de.mh.walter.entity.repository.UserRepository;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    UserRepository userRepository;

    public User find(long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        List<User> result = new LinkedList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }
}
