package giuk.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class AppUserRole {

  public AppUserRole(int role) {
    this.role = EnumAppUserRole.values()[role];
  }
  public AppUserRole(EnumAppUserRole role){this.role = role;}

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer role_id;
  @Column
  private Integer user_id;
  @Column
  @Enumerated(EnumType.ORDINAL)
  private EnumAppUserRole role;

  @Override
  public String toString() {
    return String.format("id:%d, user:%d, role:%s", role_id, user_id, role);
  }

}