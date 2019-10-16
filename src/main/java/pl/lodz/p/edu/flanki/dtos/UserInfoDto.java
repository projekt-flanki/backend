package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {

    private String username;
    private String email;
}
