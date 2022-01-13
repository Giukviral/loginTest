package giuk.entity;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {

  public AppUser(String username) {
    this.username = username;
    this.email = username + "@mail.com";
    this.password = username + "pw";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Size(max = 255)
  @Column(unique = true, nullable = false)
  private String email;

  @Size(min = 4, message = "Minimum password length: 4 characters")
  @Column(nullable = false)
  private String password;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private List<AppUserRole> appUserRoles = new ArrayList<>();
}