package pl.lodz.p.edu.flanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.config.authentication.UserPrinciple;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.UserNotExistException;
import pl.lodz.p.edu.flanki.mappers.UserInfoMapper;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserInfoMapper userInfoMapper) {
        this.userRepository = userRepository;
        this.userInfoMapper = userInfoMapper;
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

    public List<UserInfoDto> getUsersByPoints() {

        final List<User> users = userRepository.findAllByOrderByPointsDesc();
        return prepareDtos(users);
    }

    public List<UserInfoDto> getRatingRanking() {

        final List<User> users = userRepository.findAllByOrderByRatingDesc();
        return prepareDtos(users);
    }

    private List<UserInfoDto> prepareDtos(final List<User> users) {
        return users.stream()
                .map(user -> userInfoMapper.toDto(user))
                .collect(Collectors.toList());
    }
}