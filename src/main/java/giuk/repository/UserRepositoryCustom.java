package giuk.repository;

import giuk.entity.AppUser;

import java.util.List;

public interface UserRepositoryCustom {

  AppUser findByName(String name);

  List<AppUser> findByNameLike(String name);

  AppUser findByUserId(Integer userId);

  void setAppUser(AppUser addUser);

  void deleteAppUserByUserId(Integer userId);
}