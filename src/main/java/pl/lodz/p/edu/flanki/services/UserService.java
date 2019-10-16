package pl.lodz.p.edu.flanki.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.config.authentication.JWTProvider;
import pl.lodz.p.edu.flanki.config.authentication.UserPrinciple;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.dtos.UserLoginDto;
import pl.lodz.p.edu.flanki.dtos.UserRegisterDto;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.enums.UserRole;
import pl.lodz.p.edu.flanki.errors.BadJwtTokenException;
import pl.lodz.p.edu.flanki.errors.BadUserCredentialsException;
import pl.lodz.p.edu.flanki.errors.UserAlreadyRegisteredException;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public String login(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> username = userRepository.findByEmail(userLoginDto.getEmail());

        if (username.isPresent()) {
            log.info("authorizeded user " + username.get().getEmail());
        } else throw new BadUserCredentialsException("Niepoprawne dane logowania");

        return jwtProvider.generateJwtToken();
    }

    public void register(UserRegisterDto userRegisterDto) {

        checkEmailValidity(userRegisterDto.getEmail());
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .username(userRegisterDto.getName())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(UserRole.USER)
                .build();
        userRepository.save(user);
    }

    private void checkEmailValidity(String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);

        if (checkUser.isPresent()) {
            throw new UserAlreadyRegisteredException("Użytkownik o podanym e-mail już istnieje.");
        }
    }

    public UserInfoDto getUserInfo() {

        UserPrinciple userPrinciple = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userPrinciple.getEmail());

        if(!optionalUser.isPresent()){
            throw new BadJwtTokenException("Niepoprawny token");
        } else {
            User user = optionalUser.get();
            return UserInfoDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .build();
        }
    }
}