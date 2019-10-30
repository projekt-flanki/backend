package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import javax.validation.constraints.Email;

@Data
@Builder
public class UserRegisterDto {

    @NonNull
    private String name;

    @Email
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String profileImageBase64;
}
