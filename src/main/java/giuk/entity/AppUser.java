package giuk.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Create getters and setters
@Entity
@NoArgsConstructor
public class AppUser {

  public AppUser(String username) {
    this.username = username;
    this.email = username + "@mail.com";
    this.password = username + "pw";
  }

  @Id
  @Column
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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private List<AppUserRole> appUserRoles = new ArrayList<>();
}