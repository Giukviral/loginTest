package giuk.controller;


import giuk.domain.AppUserDomain;
import giuk.dto.AppUserResponseDTO;
import giuk.dto.LoginDTO;
import giuk.dto.RestResponseDTO;
import giuk.dto.SignupDTO;
import giuk.service.AppUserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppUserQueryController {

  private final AppUserService userService;

  @PostMapping("/signup")
  public ResponseEntity<RestResponseDTO> signup(@RequestBody @Valid SignupDTO user, Errors errors) {
    log.info("signup : " + user.toString());
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(new RestResponseDTO("invalid id/pw"));
    }
    RestResponseDTO result = userService.signup(user);
    log.info(result.getMessage());
    if (result.getMessage().compareTo("duplicated email") == 0 ||
        result.getMessage().compareTo("duplicated username") == 0) {
      return ResponseEntity.badRequest().body(result);
    } else {
      return ResponseEntity.ok().body(result);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<RestResponseDTO> login(@RequestBody @Valid LoginDTO loginData,
      Errors errors) {
    log.info("login : " + loginData.toString());
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(new RestResponseDTO("invalid id/pw"));
    }
    RestResponseDTO result = userService.login(loginData);
    if (result.getMessage().compareTo("un correct id/pw") == 0) {
      return ResponseEntity.badRequest().body(result);
    } else {
      return ResponseEntity.ok().body(result);
    }
  }

  @GetMapping("/users/{userid}")
  @PreAuthorize("hasRole('ADMIN')")
  public AppUserResponseDTO search(@PathVariable String userid) {
    log.info("search : " + userid);
    return userService.findByUserId(Integer.parseInt(userid));
  }

  @GetMapping("/users/my-info")
  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  public AppUserResponseDTO myInfo(@AuthenticationPrincipal AppUserDomain user) {
    log.info("myInfo : " + user.toString());
    return userService.myInfo(user);
  }
}
