package pl.lodz.p.edu.flanki.services;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.dtos.EventResultDto;
import pl.lodz.p.edu.flanki.dtos.ResultOfEvent;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.*;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;


    @Autowired
    EventService(final EventRepository eventRepository,
                 final UserService userService,
                 final EventMapper eventMapper, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
    }

    public Event getEvent(final UUID id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new NotEventsFoundException("Wydarzenie nie istnieje"));
    }

    public UUID createEvent(final Event event) {
        final User user = userService.getUser();
        final Event newEvent = event.toBuilder()
                .owners(Collections.singleton(user))
                .build();
        return eventRepository.save(newEvent);
    }

    public List<Event> getMyEvents() {
        final User user = userService.getUser();
        return eventRepository.findEventsByOwnerId(user.getId());
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void remove(final UUID id) {
        eventRepository.delete(id);
    }

    public void joinEvent(final UUID eventId) {
        final User user = userService.getUser();
        final Event event = getEvent(eventId);
        if (doesUserParticipateInEvent(user, event)) {
            throw new UserAlreadyRegisteredException("Użytkownik już dołączył do spotkania");
        }
        if(event.isFinalized()){
            throw new EventFinalizedException("Event został zakończony");
        }
        addUserToEvent(user, event);
    }

    private boolean doesUserParticipateInEvent(final User user, final Event event) {
        final Set<User> firstTeam = new HashSet<>(event.getFirstTeam());
        final Set<User> secondTeam = new HashSet<>(event.getFirstTeam());
        return firstTeam.contains(user) || secondTeam.contains(user);
    }

    private void addUserToEvent(final User user, final Event event) {
        final Set<User> firstTeam = Sets.newHashSet(event.getFirstTeam());
        final Set<User> secondTeam = Sets.newHashSet(event.getSecondTeam());
        if (firstTeam.size() <= secondTeam.size()) {
            firstTeam.add(user);
        } else {
            secondTeam.add(user);
        }

        eventRepository.save(event.toBuilder()
                .firstTeam(firstTeam)
                .secondTeam(secondTeam)
                .build());
    }

    public void editEvent(@Valid final EventDto eventDto) {
        final Event editedEvent = eventMapper.toModel(eventDto);
        final Event eventToPersist = editedEvent.toBuilder().id(eventDto.getId()).build();
        eventRepository.save(eventToPersist);
    }

    public void finalizeEvent(final EventResultDto eventResultDto) {

        final Event event = getEvent(eventResultDto.getEventId());

        final User user = userService.getUser();
        final Set<User> owners = event.getOwners();
        if (!owners.contains(user)) {
            throw new NotAuthorizedTryOfFinalizingEventException(user, event);
        }

        final ResultOfEvent resultOfEvent;
        if (eventResultDto.getTeamNumber() == 0) {
            resultOfEvent = ResultOfEvent.FIRST_TEAM_WON;
            grantPointsToWinners(event.getFirstTeam());
        } else if (eventResultDto.getTeamNumber() == 1) {
            resultOfEvent = ResultOfEvent.SECOND_TEAM_WON;
            grantPointsToWinners(event.getSecondTeam());
        }else {
            throw new NotResultOfEventProvidedException(eventResultDto);
        }

        eventRepository.save(event.toBuilder()
                .result(resultOfEvent)
                .finalized(true)
                .build());
    }

    private void grantPointsToWinners(final Set<User> members) {
        members.forEach(
                member -> userRepository.save(member.toBuilder()
                        .points(member.getPoints() + 1)
                        .build())
        );
    }

    public void unsubscribeEvent(final UUID eventId) {

        final Event event = getEvent(eventId);
        final User user = userService.getUser();

        if (event.getFirstTeam().contains(user)) {
            event.getFirstTeam().remove(user);
        } else {
            event.getSecondTeam().remove(user);
        }
        eventRepository.save(event);
    }
}