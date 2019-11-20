package pl.lodz.p.edu.flanki.services;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.NotEventsFoundException;
import pl.lodz.p.edu.flanki.errors.UserAlreadyRegisteredException;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
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


    @Autowired
    EventService(final EventRepository eventRepository,
                 final UserService userService,
                 final EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
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
}