package giuk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import giuk.entity.AppUser;
import giuk.entity.QAppUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  private static final QAppUser appUser = QAppUser.appUser;

  @Override
  public AppUser findByName(String name) {
    log.info("findByName called : " + name);
    return jpaQueryFactory.selectFrom(appUser).where(appUser.username.eq(name)).fetchOne();
  }

  @Override
  public AppUser findByMail(String mail) {
    log.info("findByEmail called : " + mail);
    return jpaQueryFactory.selectFrom(appUser).where(appUser.email.eq(mail)).fetchOne();
  }

  @Override
  public List<AppUser> findByNameLike(String name) {
    log.info("findByNameLike called : " + name);
    return jpaQueryFactory.selectFrom(appUser).where(appUser.username.like("%" + name + "%"))
        .orderBy(appUser.userId.asc()).fetch();
  }

  @Override
  public AppUser findByUserId(Integer userId) {
    log.info("findByUserId Called : " + userId);
    return jpaQueryFactory.selectFrom(appUser).where(appUser.userId.eq(userId)).fetchOne();
  }

  @Override
  public void updateAppUser(AppUser addUser) {
    log.info("updateAppUser called : " + addUser.toString());
    jpaQueryFactory.update(appUser).where(appUser.userId.eq(addUser.getUserId()))
        .set(appUser.email, addUser.getEmail())
        .execute();
  }

  @Override
  public void deleteAppUserByUserId(Integer userId) {
    log.info("deleteAppUserByUserId called : "+userId);
    jpaQueryFactory.delete(appUser).where(appUser.userId.eq(userId)).execute();
  }

  @Override
  public int getAllUserCount() {
    return jpaQueryFactory.select(appUser.count()).from(appUser).fetchOne().intValue();
  }
}