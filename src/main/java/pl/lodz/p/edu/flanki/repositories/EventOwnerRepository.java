package pl.lodz.p.edu.flanki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.flanki.entities.EventOwner;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, UUID> {
    Optional<EventOwner> findByUserId(UUID userId);
}