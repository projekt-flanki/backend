package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.util.UUID;

@Data
@Builder
public class UserInfoDto {

    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String email;
}
