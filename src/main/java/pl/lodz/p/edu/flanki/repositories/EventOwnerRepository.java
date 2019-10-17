package pl.lodz.p.edu.flanki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.flanki.entities.EventOwner;

import java.util.Optional;

@Repository
public interface EventOwnerRepository extends JpaRepository<EventOwner, Integer> {
    Optional<EventOwner> findByUserId(Integer userId);
}