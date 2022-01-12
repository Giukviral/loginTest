package giuk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {
    public static final int ROLE_ADMIN = 0x01;

    public AppUser(String username){
        this.userId=0;
        this.username=username;
        this.email=username+"@mail.com";
        this.password=username+"pw";
        this.userrole=0;
    }

    @Id
    private Integer userId;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Size(max=255)
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 4, message = "Minimum password length: 4 characters")
    @Column(nullable = false)
    private String password;

    @Column
    private int userrole;

//    @OneToMany(fetch = FetchType.EAGER) sql 에서 안 됨.
//    List< AppUserRole > appUserRole;

}
