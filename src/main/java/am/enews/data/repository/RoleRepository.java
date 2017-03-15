package am.enews.data.repository;

import am.enews.data.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vazgent on 3/15/2017.
 */
public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}