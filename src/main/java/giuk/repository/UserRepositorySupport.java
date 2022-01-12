package giuk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import giuk.entity.AppUser;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static giuk.entity.QAppUser.appUser;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositorySupport(JPAQueryFactory jpaQueryFactory){
        super(AppUser.class);
        this.jpaQueryFactory=jpaQueryFactory;
    }
    public List<AppUser> findByName(String name){
        System.out.printf("tar str : %s\n",name);
        System.out.printf("result : %s\n",jpaQueryFactory.selectFrom(appUser).where(appUser.username.eq(name)).fetch().toString());
        return jpaQueryFactory.selectFrom(appUser).where(appUser.username.eq(name)).fetch();
    }
}
