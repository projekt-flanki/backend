package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;
import java.util.UUID;

@Data
@Builder
public class UserInfoDto {

    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    private String profileImageBase64;

    @Range(min = 1, max = 5)
    private Double rating;
}
