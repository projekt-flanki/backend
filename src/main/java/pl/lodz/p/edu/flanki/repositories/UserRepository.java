package pl.lodz.p.edu.flanki.repositories;

import pl.lodz.p.edu.flanki.entities.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    UUID save(User user);

    List<User> findAllById(Iterable<UUID> ids);

    List<User> findAllByOrderByPointsDesc();

    List<User> findAllByOrderByRatingDesc();
}
