package uz.jl.springbootintro.springbootintro.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class AuthRole implements GrantedAuthority {
    @Id
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String code;

    @Override
    public String getAuthority() {
        return "ROLE_" + code;
    }

    @ManyToMany(mappedBy = "roles")
    private List<AuthUser> users;

}
