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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

  public AppUser(String username) {
    this.username = username;
    this.email = username + "@mail.com";
    this.password = username + "pw";
  }

  public AppUser(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
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

  public List<EnumAppUserRole> getRoleWithEnumAppUserRoles() {
    List<EnumAppUserRole> enumAppUserRoles = new ArrayList<>();
    for (AppUserRole role : appUserRoles) {
      enumAppUserRoles.add(role.getRole());
    }
    return enumAppUserRoles;
  }

  @Override
  public String toString(){
    return String.format("id : %d, name : %s, email : %s, passwd : %s, role : %s",userId, username, email, password, appUserRoles.toString());
  }
}