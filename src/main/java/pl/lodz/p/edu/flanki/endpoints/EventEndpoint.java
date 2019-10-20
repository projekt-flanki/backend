package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import pl.lodz.p.edu.flanki.services.EventService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/event")
public class EventEndpoint {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventEndpoint(final EventService eventService, final EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping("create")
    public ResponseEntity<Void> createEvent(@RequestBody @Valid final EventDto eventDto) {
        eventService.createEvent(eventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("owned")
    public ResponseEntity<Collection<EventDto>> getMyEvents() {
        final List<EventDto> events = eventService.getMyEvents()
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(events ,HttpStatus.OK);
    }
}
