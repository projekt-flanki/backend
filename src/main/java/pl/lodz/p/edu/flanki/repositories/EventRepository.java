package pl.lodz.p.edu.flanki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.flanki.entities.Event;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> { }