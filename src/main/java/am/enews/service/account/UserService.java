package am.enews.service.account;

import am.enews.data.UserEntity;
import am.enews.service.dto.UserCreateDto;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface UserService {
    long save(UserCreateDto user);

    UserEntity findByUsername(String username);
    UserEntity getById(Long id);
}
