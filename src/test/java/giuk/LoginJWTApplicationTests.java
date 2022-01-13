package giuk;

import giuk.entity.AppUser;
import giuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
public class LoginJWTApplicationTests {

  @Autowired // 없으면 빌드가 안됨.  variable userRepository not initialized in the default constructor
  private UserRepository userRepository;

  @Transactional(readOnly = false)
  @Test
  public void test_AppUser데이터_저장() throws Exception {
    String name = "client4";
    userRepository.save(new AppUser(name));
    List<AppUser> results = userRepository.findByNameLike("client");
    System.out.println("result--\n" + results.toString());
    Assertions.assertEquals(10, results.size());
    //Assertions.assertEquals("client4pw",results.get(0).getPassword());

    AppUser result = userRepository.findByUserId(3);
    result.getAppUserRole();
    System.out.println("result--\n" + result.toString());
    result.setEmail("ttt@gmail.com");
    System.out.println("replace--\n" + result.toString());
    userRepository.setAppUser(result);
//		userRepository.deleteAppUserByUserId(0);
    result = userRepository.findByUserId(0);
    System.out.println("result--\n" + result.toString());
  }

  @Transactional(readOnly = false)
  @Test
  public void test_AppUser데이터_저장2() throws Exception {
    String name = "client4";
    userRepository.save(new AppUser(name));
    List<AppUser> results = userRepository.findByNameLike("client");
    System.out.println("result--\n" + results.toString());
    //Assertions.assertEquals(3,results.size());
    //Assertions.assertEquals("client4pw",results.get(0).getPassword());
  }

}
