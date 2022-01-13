package giuk.entity;

import javax.persistence.*;

// legacy
@Entity
public class AppUserRole {

  public final int role_admin = 1;
  public final int role_client = 0;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer role_id;
  @Column
  private Integer user_id;
  @Column
  private int role;
}
