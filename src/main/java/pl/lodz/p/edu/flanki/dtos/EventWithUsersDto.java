package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class EventWithUsersDto {

    @NonNull
    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private double longitude;

    @NonNull
    private double latitude;

    @NonNull
    private Instant date;

    @NonNull
    private Set<UserInfoWithoutImageDto> owners;

    @NonNull
    private Set<UserInfoWithoutImageDto> firstTeam;

    @NonNull
    private Set<UserInfoWithoutImageDto> secondTeam;

    private String description;

    @Builder.Default
    private ResultOfEvent result = ResultOfEvent.NOT_RESOLVED;

}
