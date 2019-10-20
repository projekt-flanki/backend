package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class EventDto {

    private String id;

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private Instant date;

    @NonNull
    private Set<String> owners;

    private Set<String> participants;

    private String description;

}
