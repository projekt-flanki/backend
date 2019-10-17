package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.services.EventService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/event")
public class EventEndpoint {

    private EventService eventService;

    @Autowired
    public EventEndpoint(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "create")
    public ResponseEntity<?> createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "owned")
    public ResponseEntity<List<Event>> getMyEvents() {
        List<Event> events = eventService.getMyEvents();
        return new ResponseEntity<>(events ,HttpStatus.OK);
    }
}
