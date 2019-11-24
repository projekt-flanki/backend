package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import java.util.UUID;

@Value
@Builder
public class UserInfoWithoutImageDto {

    private UUID id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @Range(min = 1, max = 5)
    private double rating;

    private int points;
}
