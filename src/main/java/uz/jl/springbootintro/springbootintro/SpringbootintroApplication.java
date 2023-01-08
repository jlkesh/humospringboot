package uz.jl.springbootintro.springbootintro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.jl.springbootintro.springbootintro.domains.AuthRole;
import uz.jl.springbootintro.springbootintro.domains.AuthUser;
import uz.jl.springbootintro.springbootintro.repository.AuthRoleRepository;
import uz.jl.springbootintro.springbootintro.repository.AuthUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringbootintroApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootintroApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AuthUserRepository authUserRepository,
                             AuthRoleRepository authRoleRepository) {
        return (args) -> {
            authUserRepository.deleteAll();
            authRoleRepository.deleteAll();

            var authRole = new AuthRole(1L, "Admin", "ADMIN", null);
            authRoleRepository.save(authRole);

            var authUser = new AuthUser(1L
                    , "john",
                    "123",
                    "john@mail.com", LocalDateTime.now(),
                    List.of(authRole));
            authUserRepository.save(authUser);
        };
    }

}