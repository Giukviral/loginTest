package giuk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import giuk.entity.AppUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static giuk.entity.QAppUser.appUser;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List< AppUser > findByName(String name) {
        return jpaQueryFactory.selectFrom(appUser).where(appUser.username.eq(name)).fetch();
    }

    @Override
    public List< AppUser > findByNameLike(String name) {
        return jpaQueryFactory.selectFrom(appUser).where(appUser.username.like(name+"%")).fetch();
    }

    @Override
    public List< AppUser > findByUserId(Integer userId) {
        return jpaQueryFactory.selectFrom(appUser).where(appUser.userId.eq(userId)).fetch();
    }
}
