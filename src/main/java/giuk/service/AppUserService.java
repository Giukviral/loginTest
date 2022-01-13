package giuk.service;

import giuk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

  private final UserRepository userRepository;

}
