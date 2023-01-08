package uz.jl.springbootintro.springbootintro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.jl.springbootintro.springbootintro.domains.AuthRole;
import uz.jl.springbootintro.springbootintro.domains.AuthUser;

import java.util.Optional;


public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
}
