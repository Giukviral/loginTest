package giuk;

import giuk.entity.AppUser;
import giuk.entity.AppUserRole;
import giuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RequiredArgsConstructor
public class QueryDSLTests {

  @Autowired // 없으면 빌드가 안됨.
  private UserRepository userRepository;

  @Transactional(readOnly = false)
  @Test
  public void test_AppUser데이터_저장() throws Exception {
    AppUser result = new AppUser("client4");
    result.getAppUserRoles().add(new AppUserRole(0));
    int count = userRepository.getAllUserCount();
    int newUserId = userRepository.save(result).getUserId();
    System.out.println(count);
    Assertions.assertEquals(count + 1, userRepository.findByNameLike("").size());
    result = userRepository.findByName("client4");
    //System.out.println(result);  // userRole에 자동으로 user가 등록이 안됨
    result.setEmail("ttt@gmail.com");
    userRepository.updateAppUser(result);
    result = userRepository.findByName("client4");
    System.out.println(result); // 새로 가져와도 안됨. appUserRoles=[id:12, user:null, role:CLIENT]
    Assertions.assertEquals("ttt@gmail.com", result.getEmail());
  }

  @Transactional(readOnly = false)
  @Test
  public void test_AppUserRole조회() throws Exception {
    AppUser result = userRepository.findByUserId(1);
    System.out.println(result.getUsername());
    System.out.println(result);
  }
}
