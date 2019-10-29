package pl.lodz.p.edu.flanki.repositories;

import pl.lodz.p.edu.flanki.entities.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    UUID save(Event event);

    List<Event> findEventsByOwnerId (UUID id);

    List<Event> findAll();

    Optional<Event> findById(UUID id);

    void delete(UUID id);
}
