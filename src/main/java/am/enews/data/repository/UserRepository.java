package am.enews.data.repository;

import am.enews.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String userName);
}