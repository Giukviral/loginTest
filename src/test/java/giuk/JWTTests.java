package giuk;

import giuk.controller.AppUserQueryController;
import giuk.dto.AppUserResponseDTO;
import giuk.dto.LoginDTO;
import giuk.dto.RestResponseDTO;
import giuk.dto.SignupDTO;
import giuk.entity.EnumAppUserRole;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@WebAppConfiguration
@SpringBootTest
public class JWTTests {

  @Autowired // final이 빠져야 한대
  private AppUserQueryController appUserQueryController;

  @Transactional(readOnly = false)
  @Test
  public void test_JTW획득() throws Exception {
    // signup, login, {userid}, my-info
    ArrayList<EnumAppUserRole> role = new ArrayList<>();
    role.add(EnumAppUserRole.ROLE_ADMIN); role.add(EnumAppUserRole.ROLE_CLIENT);
    SignupDTO newAdmin = SignupDTO.builder().username("newadmin").password("newpw").email("newadmin@email.com").appUserRole(role).build();

    RestResponseDTO restResponseDTO = appUserQueryController.signup(newAdmin);
    System.out.println(restResponseDTO.getMessage());

    role.clear(); role.add(EnumAppUserRole.ROLE_CLIENT);
    SignupDTO newClient = SignupDTO.builder().username("newClient").password("newpw").email("newClient@email.com").appUserRole(role).build();

    restResponseDTO = appUserQueryController.signup(newClient);
    System.out.println(restResponseDTO.getMessage());

    System.out.println("@@@@ sign up clear");

    LoginDTO loginDTO;
    loginDTO = new LoginDTO("newadmin", "newpw");
    restResponseDTO = appUserQueryController.login(loginDTO);

    MockHttpServletRequest servletRequest = new MockHttpServletRequest("GET","/users/my-info");
    AppUserResponseDTO appUserResponseDTO;
    servletRequest.addHeader("Authorization","Bearer "+restResponseDTO.getMessage());
    appUserResponseDTO = appUserQueryController.myinfo(servletRequest);
    System.out.println(appUserResponseDTO.getUsername());

    loginDTO = new LoginDTO("newClient", "newpw");
    restResponseDTO = appUserQueryController.login(loginDTO);

    servletRequest = new MockHttpServletRequest("GET","/users/my-info");
    servletRequest.addHeader("Authorization","Bearer "+restResponseDTO.getMessage());
    appUserResponseDTO = appUserQueryController.myinfo(servletRequest);
    System.out.println(appUserResponseDTO.getUsername());

    System.out.println("@@@@ myinfo clear");

    appUserResponseDTO = appUserQueryController.search("1");
    System.out.println(appUserResponseDTO);

  }

}