package giuk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppUserRole{
    @Id
    @Column
    private Integer role_id;
    @Column(name="user_id")
    private Integer user_id;
    @Column
    private int role;
}