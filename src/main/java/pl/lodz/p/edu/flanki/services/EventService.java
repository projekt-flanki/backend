package pl.lodz.p.edu.flanki.services;

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
import java.util.*;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMapper eventMapper;


    @Autowired
    EventService(final EventRepository eventRepository, UserService userService, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
    }

    private Event getEvent(UUID id) {
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

    public void joinEvent(final UUID id) {
        final User user = userService.getUser();
        final Event event = getEvent(id);
        final Set<User> participants = new HashSet<>(event.getParticipants());
        if (!participants.contains(user)) {
            participants.add(user);
            eventRepository.delete(event.getId());
            eventRepository.save(event.toBuilder().participants(participants).build());
        } else {
            throw new UserAlreadyRegisteredException("Użytkownik już dołączył do spotkania");
        }
    }

    public void editEvent(@Valid EventDto eventDto) {
        final Event editedEvent = eventMapper.toModel(eventDto);
        final Event eventToPersist = editedEvent.toBuilder().id(eventDto.getId()).build();
        eventRepository.save(eventToPersist);
    }
}