package am.enews.service.account;

import am.enews.data.UserEntity;
import am.enews.data.repository.RoleRepository;
import am.enews.data.repository.UserRepository;
import am.enews.service.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by vazgent on 3/15/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    @Transactional
    public long save(UserCreateDto request) {
        UserEntity user = new UserEntity();
        user.setUserName(request.getUserName());
        user.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.getOne(id);
    }
}
