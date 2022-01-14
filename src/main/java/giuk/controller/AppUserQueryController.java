package giuk.controller;


import giuk.dto.LoginDTO;
import giuk.dto.SignupDTO;
import giuk.dto.AppUserResponseDTO;
import giuk.service.AppUserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppUserQueryController {

  private final AppUserService userService;

  @PostMapping("/signup")
  public String signup(@RequestBody @Valid SignupDTO user){
    return userService.saveNewUser(user);
  }

  @GetMapping("/users/{userid}")
  public AppUserResponseDTO search(@PathVariable String userid){
    return userService.findByUserId(Integer.parseInt(userid));
  }

  @PostMapping("/login")
  public String login(@RequestBody @Valid LoginDTO loginData) {
    return userService.login(loginData);
  }
}
