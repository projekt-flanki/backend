package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
@Builder
public class EventResultDto {

    @NonNull
    private UUID eventId;

    @NonNull
    @Range(min = 0, max = 1)
    private int teamNumber;

}
