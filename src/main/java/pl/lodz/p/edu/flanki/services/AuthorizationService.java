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
import pl.lodz.p.edu.flanki.dtos.UserLoginDto;
import pl.lodz.p.edu.flanki.dtos.UserRegisterDto;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.enums.UserRole;
import pl.lodz.p.edu.flanki.errors.BadUserCredentialsException;
import pl.lodz.p.edu.flanki.errors.UserAlreadyRegisteredException;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Autowired
    public AuthorizationService(final UserRepository userRepository,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManager authenticationManager,
                                final JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public String login(final UserLoginDto userLoginDto) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final Optional<User> username = userRepository.findByEmail(userLoginDto.getEmail());

        if (username.isPresent()) {
            log.info("authorized user " + username.get().getEmail());
        } else throw new BadUserCredentialsException("Niepoprawne dane logowania");

        return jwtProvider.generateJwtToken();
    }

    public void register(final UserRegisterDto userRegisterDto) {

        checkEmailValidity(userRegisterDto.getEmail());
        final User user = User.builder()
                .email(userRegisterDto.getEmail())
                .username(userRegisterDto.getName())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(UserRole.USER)
                .profileImageBase64(userRegisterDto.getProfileImageBase64())
                .build();
        userRepository.save(user);
    }

    private void checkEmailValidity(final String email) {

        final Optional<User> checkUser = userRepository.findByEmail(email);

        if (checkUser.isPresent()) {
            throw new UserAlreadyRegisteredException("Użytkownik o podanym e-mail już istnieje.");
        }
    }

}