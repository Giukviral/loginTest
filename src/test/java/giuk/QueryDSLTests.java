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
    // 신규 저장
    AppUser result = new AppUser("client4");
    result.getAppUserRoles().add(new AppUserRole(0));
    int count = userRepository.getAllUserCount();
    userRepository.save(result);
    Assertions.assertEquals(count + 1, userRepository.getAllUserCount());

    // 업데이트
    result = userRepository.findByName("client4");
    AppUser replace = new AppUser(result.getUserId(), result.getUsername(), "ttt@gmail.com",
        result.getPassword(), result.getAppUserRoles());
    System.out.println(replace);
    System.out.println(result);
    userRepository.updateAppUser(replace);
    replace = userRepository.findByMail("ttt@gmail.com");
    System.out.println(replace); //ttt로 찾아지는데, 결과물은 client4임...
//    Assertions.assertEquals("ttt@gmail.com", result.getEmail());
    userRepository.deleteAppUserByUserId(replace.getUserId());
    Assertions.assertEquals(count, userRepository.getAllUserCount());
  }
}
