package de.mh.lists.entity.repository;

import de.mh.lists.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
}
