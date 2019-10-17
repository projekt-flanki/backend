package pl.lodz.p.edu.flanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.config.authentication.UserPrinciple;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.EventOwner;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.errors.NotEventsFoundException;
import pl.lodz.p.edu.flanki.errors.UserNotExistException;
import pl.lodz.p.edu.flanki.repositories.EventOwnerRepository;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    private EventRepository eventRepository;

    private EventOwnerRepository eventOwnerRepository;

    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, EventOwnerRepository eventOwnerRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventOwnerRepository = eventOwnerRepository;
        this.userRepository = userRepository;
    }

    public void createEvent(EventDto eventDto) {
        User user = getUser();
        Event event = buildEvent(eventDto);
        eventRepository.save(event);
        saveEventToOwner(user, event);
    }

    private User getUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userPrinciple.getEmail()).orElseThrow(() ->
                new UserNotExistException("Użytkownik nie istnieje."));
    }

    private void saveEventToOwner(User user, Event event) {
        EventOwner eventOwner;
        Optional<EventOwner> eventOwnerOptional = eventOwnerRepository.findByUserId(user.getId());
        if (eventOwnerOptional.isPresent()) {
            eventOwner = eventOwnerOptional.get();
            eventOwner.getEvents().add(event);
        } else {
            eventOwner = EventOwner.builder()
                    .userId(user.getId())
                    .events(Arrays.asList(event)).build();
        }
        eventOwnerRepository.save(eventOwner);
    }

    private Event buildEvent(EventDto eventDto) {
        return Event.builder()
                .name(eventDto.getName())
                .date(LocalDateTime.now())
                .location(eventDto.getLocation())
                .description(eventDto.getDescription())
                .build();
    }

    public List<Event> getMyEvents() {
        User user = getUser();
        return eventOwnerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotEventsFoundException("Użytkownik nie posiada wydarzeń"))
                .getEvents();
    }
}