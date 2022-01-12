package giuk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data // Create getters and setters
@NoArgsConstructor
public class AppUser {

    @Id
    @Column(name="user_id")
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Size(max=255)
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(nullable = false)
    private String password;

//    @OneToMany(fetch = FetchType.EAGER) sql 에서 안 됨.
//    List< AppUserRole > appUserRole;

}
