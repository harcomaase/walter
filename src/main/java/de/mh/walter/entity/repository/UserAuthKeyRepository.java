package de.mh.walter.entity.repository;

import de.mh.walter.entity.UserAuthKey;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthKeyRepository extends CrudRepository<UserAuthKey, Long> {

    UserAuthKey findByAuthenticationKey(String key);
}
