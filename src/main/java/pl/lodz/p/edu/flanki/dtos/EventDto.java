package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.time.Instant;

@Data
@Builder
public class EventDto {

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private Instant date;

    private String description;
}