package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.services.EventService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/event")
public class EventEndpoint {

    private final EventService eventService;

    @Autowired
    public EventEndpoint(final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("create")
    public ResponseEntity<Void> createEvent(@RequestBody @Valid final EventDto eventDto) {
        eventService.createEvent(eventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("owned")
    public ResponseEntity<Collection<Event>> getMyEvents() {
        final List<Event> events = eventService.getMyEvents();
        return new ResponseEntity<>(events ,HttpStatus.OK);
    }
}
