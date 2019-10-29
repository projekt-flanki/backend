package pl.lodz.p.edu.flanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.NotEventsFoundException;
import pl.lodz.p.edu.flanki.errors.UserAlreadyRegisteredException;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final AuthorizationService authorizationService;

    @Autowired
    EventService(final EventRepository eventRepository, final AuthorizationService authorizationService) {
        this.eventRepository = eventRepository;
        this.authorizationService = authorizationService;
    }

    public UUID createEvent(final Event event) {
        final User user = authorizationService.getUser();
        event.toBuilder()
                .owners(Collections.singleton(user))
                .build();
        return eventRepository.save(event);
    }

    public List<Event> getMyEvents() {
        final User user = authorizationService.getUser();
        return eventRepository.findEventsByOwnerId(user.getId());
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void remove(final UUID id){
        eventRepository.delete(id);
    }

    public void joinEvent(final UUID id) {
        final User user = authorizationService.getUser();
        final Event event = eventRepository.findById(id).orElseThrow(
                () -> new NotEventsFoundException("Wydarzenie nie istnieje"));
        final Set<User> participants = new HashSet<>(event.getParticipants());
        if (!participants.contains(user)) {
            participants.add(user);
            eventRepository.delete(event.getId());
            eventRepository.save(event.toBuilder().participants(participants).build());
        } else {
            throw new UserAlreadyRegisteredException("Użytkownik już dołączył do spotkania");
        }
    }
}