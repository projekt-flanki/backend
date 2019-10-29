package pl.lodz.p.edu.flanki.repositories;

import pl.lodz.p.edu.flanki.entities.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventsStorage implements EventRepository {

    private final JpaEventRepository jpaEventRepository;

    EventsStorage(final JpaEventRepository jpaEventRepository) {
        this.jpaEventRepository = jpaEventRepository;
    }

    @Override
    public UUID save(final Event event) {
        return jpaEventRepository.save(event).getId();
    }

    @Override
    public List<Event> findEventsByOwnerId(final UUID id) {
        return jpaEventRepository.findByOwners_id(id);
    }

    @Override
    public List<Event> findAll() {
        return jpaEventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(final UUID id) {
        return jpaEventRepository.findById(id);
    }

    @Override
    public void delete(final UUID id) {
        jpaEventRepository.deleteById(id);
    }
}
