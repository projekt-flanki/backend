package pl.lodz.p.edu.flanki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.flanki.entities.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaEventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByOwners_id(UUID userId);
    Optional<Event> findById(UUID id);
}