package giuk.entity;

import javax.persistence.*;
import lombok.NoArgsConstructor;

enum UserPermission {
  CLIENT, ADMIN, MANAGER
}

@Entity
@NoArgsConstructor
public class AppUserRole {

  public AppUserRole(int role) {
    this.role = UserPermission.values()[role];
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer role_id;
  @Column
  private Integer user_id;
  @Column
  private UserPermission role;

  @Override
  public String toString() {
    return String.format("id:%d, user:%d, role:%s", role_id, user_id, role);
  }
}
