package com.example.demo.model.entity.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username required")
    @Size(min=1, max=8)
    private String username;

    @NotBlank(message = "password required")
    @Size(min=1, max=8)
    private String password;

    @Email
    private String email;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;



//    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<UserRole> roles;
}
