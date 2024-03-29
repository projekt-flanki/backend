package pl.lodz.p.edu.flanki.mappers;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.dtos.EventWithUsersDto;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.dtos.UserInfoWithoutImageDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.Location;
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
        final Set<User> owners = Optional.ofNullable(eventDto.getOwnerIds())
                .map(ids -> new HashSet<>(userRepository.findAllById(ids)))
                .orElse(new HashSet<>());

        final Set<User> firstTeam = Optional.ofNullable(eventDto.getFirstTeamIds())
                .map(ids -> new HashSet<>(userRepository.findAllById(ids)))
                .orElse(new HashSet<>());

        final Set<User> secondTeam = Optional.ofNullable(eventDto.getSecondTeamIds())
                .map(ids -> new HashSet<>(userRepository.findAllById(ids)))
                .orElse(new HashSet<>());

        return Event.builder()
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .location(Location.builder()
                        .latitude(eventDto.getLatitude())
                        .longitude(eventDto.getLongitude())
                        .build())
                .description(eventDto.getDescription())
                .owners(owners)
                .firstTeam(firstTeam)
                .secondTeam(secondTeam)
                .finalized(false)
                .build();
    }

    public EventDto toDto(final Event event) {
        final Set<UUID> ownersIds = event.getOwners().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        final Set<UUID> firstTeamIds = event.getFirstTeam().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        final Set<UUID> secondTeamIds = event.getSecondTeam().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return EventDto.builder()
                .id(event.getId())
                .date(event.getDate())
                .description(event.getDescription())
                .latitude(event.getLocation().getLatitude())
                .longitude(event.getLocation().getLongitude())
                .name(event.getName())
                .ownerIds(ownersIds)
                .firstTeamIds(firstTeamIds)
                .secondTeamIds(secondTeamIds)
                .build();
    }

    public EventWithUsersDto toDtoWithUsers(final Event event){
        final Set<UserInfoWithoutImageDto> owners = event.getOwners()
                .stream()
                .map(this::userInfoWithoutImage)
                .collect(Collectors.toSet());

        final Set<UserInfoWithoutImageDto> firstTeam = event.getFirstTeam()
                .stream()
                .map(this::userInfoWithoutImage)
                .collect(Collectors.toSet());

        final Set<UserInfoWithoutImageDto> secondTeam = event.getSecondTeam()
                .stream()
                .map(this::userInfoWithoutImage)
                .collect(Collectors.toSet());


        return EventWithUsersDto.builder()
                .id(event.getId())
                .date(event.getDate())
                .description(event.getDescription())
                .latitude(event.getLocation().getLatitude())
                .longitude(event.getLocation().getLongitude())
                .name(event.getName())
                .owners(owners)
                .firstTeam(firstTeam)
                .secondTeam(secondTeam)
                .result(event.getResult())
                .build();
    }

    private UserInfoWithoutImageDto userInfoWithoutImage(final User user) {
        return UserInfoWithoutImageDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .points(user.getPoints())
                .username(user.getUsername())
                .rating(user.getRating())
                .build();
    }
}
