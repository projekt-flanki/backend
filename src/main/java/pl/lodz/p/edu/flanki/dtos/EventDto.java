package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EventDto {

    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private Instant date;

    private Set<UUID> ownerIds;

    private Set<UUID> getParticipantIds;

    private String description;

}
