package pl.lodz.p.edu.flanki.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.config.authentication.UserPrinciple;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.UserNotExistException;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        final UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userPrinciple.getEmail()).orElseThrow(() ->
                new UserNotExistException("Użytkownik nie istnieje."));
    }

    public void setProfileImage(UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(userInfoDto.getEmail()).orElseThrow(
                () -> new UserNotExistException("Użytkownik nie istnieje")
        );
        user.toBuilder().profileImageBase64(userInfoDto.getProfileImageBase64());
        userRepository.save(user);
    }

    public void editUser(UserInfoDto userInfoDto) {

        final User user = userRepository.findByEmail(userInfoDto.getEmail()).orElseThrow(
                () -> new UserNotExistException("Użytkownik nie istnieje")
        );

        final User userEdited = user.toBuilder()
                .username(userInfoDto.getUsername())
                .rating(userInfoDto.getRating())
                .profileImageBase64(userInfoDto.getProfileImageBase64())
                .build();
        userRepository.save(userEdited);
    }
}