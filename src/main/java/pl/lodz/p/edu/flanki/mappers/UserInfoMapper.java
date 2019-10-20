package pl.lodz.p.edu.flanki.mappers;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.entities.User;

@Component
public class UserInfoMapper {

    public UserInfoDto toDto(final User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
