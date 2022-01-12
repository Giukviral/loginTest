package giuk.repository;

import giuk.entity.AppUser;

import java.util.List;

public interface UserRepositoryCustom {
    List< AppUser > findByName(String name);
    List< AppUser > findByNameLike(String name);
    List< AppUser > findByUserId(Integer userId);
}
