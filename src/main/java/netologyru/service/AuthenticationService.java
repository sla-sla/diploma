package netologyru.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netologyru.models.User;
import netologyru.repository.AuthenticationRepository;
import netologyru.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import netologyru.dto.request.AuthenticationRequest;
import netologyru.dto.response.AuthenticationResponse;
import netologyru.jwt.JwtTokenUtil;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private AuthenticationRepository authenticationRepository;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    public String login(AuthenticationRequest authenticationRequest) {
        final String username = authenticationRequest.getLogin();
        final String password = authenticationRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = userService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        authenticationRepository.putTokenAndUsername(token, username);
        log.info("User {} authentication. JWT: {}", username, token);
        return token;
    }

    public void logout(String authToken) {
        final String token = authToken.substring(7);
        final String username = authenticationRepository.getUsernameByToken(token);
        log.info("User {} logout. JWT is disabled.", username);
        authenticationRepository.removeTokenAndUsernameByToken(token);
    }

    public User getUserByAuthToken(String authToken) {
        if (authToken.startsWith("Bearer ")) {
            final String authTokenWithoutBearer = authToken.split(" ")[1];
            final String username = authenticationRepository.getUsernameByToken(authTokenWithoutBearer);
            return userRepository.findByUsername(username);
        }
        return null;
    }
}