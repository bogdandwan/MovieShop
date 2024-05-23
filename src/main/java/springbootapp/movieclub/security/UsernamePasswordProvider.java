package springbootapp.movieclub.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springbootapp.movieclub.entity.User;
import springbootapp.movieclub.repository.UserRepository;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsernamePasswordProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Authentication authenticate(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        final User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException(username);
        if (!new BCryptPasswordEncoder().matches(password,user.getPassword())) {
            throw new AccessDeniedException(username);
        }
        final UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                //umesto if
                .authorities(user.getRole() == null ?
                        new HashSet<>():
                        user.getRole().getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toSet()))
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
}
