package giuk.repository;

import giuk.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Integer>, UserRepositoryCustom {

    /*
    boolean existsByUsername(String username);

    AppUser findByUserId(int userId);
    AppUser findByUsername(String username);
    AppUser findByUsernameLike(String username);

    @Transactional
    void deleteByUsername(String username);
    */
}