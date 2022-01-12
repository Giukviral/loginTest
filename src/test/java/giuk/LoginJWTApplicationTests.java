package giuk;

import giuk.entity.AppUser;
import giuk.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LoginJWTApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testConnection() throws Exception{
		String name="client4";
		userRepository.save(new AppUser(name));
		List<AppUser> result = userRepository.findByNameLike("client");
		System.out.println("result--\n"+result.toString());
		Assertions.assertEquals(3,result.size());
		Assertions.assertEquals("clientpw",result.get(0).getPassword());
		result = userRepository.findByUserId(3);
		System.out.println("result--\n"+result.toString());
	}

}
