package giuk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import giuk.entity.AppUser;
import giuk.entity.QAppUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  private static final QAppUser appUser = QAppUser.appUser;

  @Override
  public AppUser findByName(String name) {
    return jpaQueryFactory.selectFrom(appUser).where(appUser.username.eq(name)).fetchOne();
  }

  @Override
  public List<AppUser> findByNameLike(String name) {
    return jpaQueryFactory.selectFrom(appUser).where(appUser.username.like("%" + name + "%"))
        .orderBy(appUser.userId.asc()).fetch();
  }

  @Override
  public AppUser findByUserId(Integer userId) {
    return jpaQueryFactory.selectFrom(appUser).where(appUser.userId.eq(userId)).fetchOne();
  }

  @Override
  public void setAppUser(AppUser addUser) {
    jpaQueryFactory.update(appUser).where(appUser.userId.eq(addUser.getUserId()))
        .set(appUser.email, addUser.getEmail())
        .execute();
  }

  @Override
  public void deleteAppUserByUserId(Integer userId) {
    jpaQueryFactory.delete(appUser).where(appUser.userId.eq(userId)).execute();
  }
}