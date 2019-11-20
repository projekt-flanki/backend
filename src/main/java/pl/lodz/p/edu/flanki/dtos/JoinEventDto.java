package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class JoinEventDto {

    @NotNull
    private UUID eventId;

}
