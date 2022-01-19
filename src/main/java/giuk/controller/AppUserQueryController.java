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
  public ResponseEntity signup(@RequestBody @Valid SignupDTO user, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(new RestResponseDTO("invalid id/pw"));
    }
    RestResponseDTO result = userService.signup(user);
    log.info(result.getMessage());
    if (result.getMessage().compareTo("duplicated email") == 0 ||
        result.getMessage().compareTo("duplicated username") == 0) {
      return ResponseEntity.badRequest().body(result);
    }
    return ResponseEntity.ok().body(result);
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid LoginDTO loginData, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(new RestResponseDTO("invalid id/pw"));
    }
    RestResponseDTO result = userService.login(loginData);
    if (result.getMessage().compareTo("un correct id/pw") == 0) {
      return ResponseEntity.badRequest().body(result);
    }else{
      return ResponseEntity.ok().body(result);
    }
  }

  @GetMapping("/users/{userid}")
  @PreAuthorize("hasRole('ADMIN')")
  public AppUserResponseDTO search(@PathVariable String userid) {
    return userService.findByUserId(Integer.parseInt(userid));
  }

  @GetMapping("/users/my-info")
  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  public AppUserResponseDTO myInfo(@AuthenticationPrincipal AppUserDomain user) {
    if (user == null) {
      log.info("왜 널이야");
    } else {
      log.info(user.toString());
    }
    return userService.myInfo(user);
  }
}
