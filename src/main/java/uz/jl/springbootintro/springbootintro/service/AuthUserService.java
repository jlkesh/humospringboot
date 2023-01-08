package uz.jl.springbootintro.springbootintro.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.jl.springbootintro.springbootintro.config.UserDetails;
import uz.jl.springbootintro.springbootintro.repository.AuthUserRepository;

@Service
public record AuthUserService(AuthUserRepository authUserRepository) implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user '%s' not found".formatted(username)));
        return new UserDetails(authUser);
    }
}
