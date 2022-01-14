package giuk.controller;


import giuk.dto.UserDataDTO;
import giuk.dto.UserResponseDTO;
import giuk.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryResponseController {

  private final AppUserService userService;

  @PostMapping("/signup")
  public String signup(@RequestBody UserDataDTO user){
    return userService.saveNewUser(user);
  }

  @GetMapping("/users/{userid}")
  @PreAuthorize("hasRole('ADMIN')")
  public UserResponseDTO search(@PathVariable String userid){
    System.out.println("권한이 있나요? 정말?");
    return userService.findByUserId(Integer.parseInt(userid));
  }
}