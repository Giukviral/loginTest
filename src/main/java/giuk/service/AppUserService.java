package giuk.service;

import giuk.dto.UserDataDTO;
import giuk.dto.UserResponseDTO;
import giuk.entity.AppUser;
import giuk.entity.AppUserRole;
import giuk.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

  private final UserRepository userRepository;

  public String saveNewUser(UserDataDTO userDTO) {
    if(userRepository.findByName(userDTO.getUsername())!=null){
      return "already exist user name!";
    }
    if(userRepository.findByMail(userDTO.getEmail())!=null){
      return "already used eamil address!"; //이건 잘 됨.
    }
    AppUser newUser = new AppUser();
    newUser.setEmail(userDTO.getEmail());
    newUser.setUsername(userDTO.getUsername());
    newUser.setPassword(userDTO.getPassword());
    List<Integer> userRole = userDTO.getAppUserRole();
    for (Integer role : userRole) {
      newUser.getAppUserRoles().add(new AppUserRole(role));
    }
    return userRepository.save(newUser).getUserId().toString();
  }

  public UserResponseDTO findByUserId(Integer userid){
    AppUser user = userRepository.findByUserId(userid);
    UserResponseDTO retUser = new UserResponseDTO();
    retUser.setUsername(user.getUsername());
    retUser.setEmail(user.getEmail());
    List<Integer> userRole = new ArrayList<>();
    List<AppUserRole> hasRole = user.getAppUserRoles();
    for (AppUserRole role : hasRole){
      userRole.add(role.getRole().getNumber(role.getRole())); // role에서 enum->interger 방법을 좀더 찾아볼것.
    }
    retUser.setAppUserRole(userRole);
    return  retUser;
  }
}