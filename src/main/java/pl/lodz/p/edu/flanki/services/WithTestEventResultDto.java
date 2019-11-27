package pl.lodz.p.edu.flanki.services;

import pl.lodz.p.edu.flanki.dtos.EventResultDto;
import java.util.UUID;

public interface WithTestEventResultDto {

    default EventResultDto firstTeamWin(final UUID eventId){
        return EventResultDto.builder()
                .eventId(eventId)
                .teamNumber(0)
                .build();
    }

    default EventResultDto secondTeamWin(final UUID eventId){
        return EventResultDto.builder()
                .eventId(eventId)
                .teamNumber(1)
                .build();
    }
}
