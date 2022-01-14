package giuk.repository;

import giuk.entity.AppUser;
import java.util.List;

public interface UserRepositoryCustom {

  AppUser findByName(String name);
  AppUser findByMail(String mail);

  List<AppUser> findByNameLike(String name);

  AppUser findByUserId(Integer userId);

  void updateAppUser(AppUser addUser);

  void deleteAppUserByUserId(Integer userId);

  int getAllUserCount();
}