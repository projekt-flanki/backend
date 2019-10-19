package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserInfoDto {

    @NonNull
    private String username;
    @NonNull
    private String email;
}
