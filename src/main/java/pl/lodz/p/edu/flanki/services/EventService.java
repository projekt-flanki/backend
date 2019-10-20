package pl.lodz.p.edu.flanki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMapper eventMapper;

    @Autowired
    EventService(final EventRepository eventRepository, final UserService userService, final EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
    }

    public void createEvent(final EventDto eventDto) {
        final Event event = eventMapper.toModel(eventDto);
        eventRepository.save(event);
    }

    public List<EventDto> getMyEvents() {
        final User user = userService.getUser();
        return eventRepository.findByOwners_id(user.getId()).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
}