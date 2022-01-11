package giuk.repository;

import giuk.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    Optional<AppUser> findByUsernameLike(String username);

    @Transactional
    void deleteByUsername(String username);
}