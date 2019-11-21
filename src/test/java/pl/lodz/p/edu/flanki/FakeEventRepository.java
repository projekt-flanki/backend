package pl.lodz.p.edu.flanki;

import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class FakeEventRepository implements EventRepository {

    private final HashMap<UUID, Event> events = new HashMap<>();

    public void withEvents(final List<Event> events){
        final Map<UUID, Event> eventsMap = events.stream()
                .collect(Collectors.toMap(Event::getId, e -> e));
        this.events.putAll(eventsMap);
    }

    @Override
    public UUID save(final Event event) {
        final UUID id = event.getId() != null ? event.getId() : UUID.randomUUID();
        events.put(id, event.toBuilder().id(id).build());
        return id;
    }

    @Override
    public List<Event> findEventsByOwnerId(final UUID id) {
        return events.values()
                .stream()
                .filter(e -> e.getOwners()
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
                        .contains(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(events.values());
    }

    @Override
    public Optional<Event> findById(final UUID uuid) {
        return Optional.ofNullable(events.get(uuid));
    }

    @Override
    public void delete(final UUID id) {
        events.remove(id);
    }
}
