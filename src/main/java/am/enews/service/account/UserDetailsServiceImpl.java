package am.enews.service.account;

import am.enews.data.RoleEntity;
import am.enews.data.UserEntity;
import am.enews.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vazgent on 3/15/2017.
 */
@Service
public class UserDetailsServiceImpl implements NewsUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public NewsUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        logger.info(String.format("Trying load user by username : %s", username));
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("User not found with this username %s", username));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleEntity role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new NewsUser(userEntity.getUsername(), userEntity.getPasswordHash(), grantedAuthorities, userEntity.isActive(), userEntity.getUsername());
    }

   }
