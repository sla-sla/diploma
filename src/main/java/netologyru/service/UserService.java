package netologyru.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netologyru.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import netologyru.exceptions.UnauthorizedException;
import netologyru.models.User;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User Service: Unauthorized");
            throw new UnauthorizedException("User Service: Unauthorized");
        }
        return user;
    }
}