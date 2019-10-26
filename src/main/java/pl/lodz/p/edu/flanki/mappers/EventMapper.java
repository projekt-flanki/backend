package pl.lodz.p.edu.flanki.mappers;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.repositories.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final UserRepository userRepository;

    public EventMapper(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Event toModel(final EventDto eventDto) {
        final Set<User> owners = new HashSet<>(userRepository.findAllById(eventDto.getOwnerIds()));

        final Set<User> participants = Optional.ofNullable(eventDto.getGetParticipantIds())
                .map(ids -> new HashSet<>(userRepository.findAllById(ids)))
                .orElse(new HashSet<>());

        return Event.builder()
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .location(eventDto.getLocation())
                .description(eventDto.getDescription())
                .owners(owners)
                .participants(participants)
                .build();
    }

    public EventDto toDto(final Event event) {
        final Set<UUID> ownersIds = event.getOwners().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        final Set<UUID> participantsIds = event.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return EventDto.builder()
                .id(event.getId())
                .date(event.getDate())
                .description(event.getDescription())
                .location(event.getLocation())
                .name(event.getName())
                .ownerIds(ownersIds)
                .getParticipantIds(participantsIds)
                .build();
    }
}
