package de.mh.walter.entity.repository;

import de.mh.walter.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
    
    User findByEmail(String email);
    
    User findByAuthenticationKey(String key);
}
