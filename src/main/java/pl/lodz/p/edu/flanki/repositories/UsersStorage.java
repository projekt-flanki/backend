package pl.lodz.p.edu.flanki.repositories;

import pl.lodz.p.edu.flanki.entities.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersStorage implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UsersStorage(final JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public UUID save(final User user) {
        return jpaUserRepository.save(user).getId();
    }

    @Override
    public List<User> findAllById(final Iterable<UUID> ids) {
        return jpaUserRepository.findAllById(ids);
    }

    @Override
    public List<User> findAllByOrderByPointsDesc() {
        return jpaUserRepository.findAllByOrderByPointsDesc();
    }

    @Override
    public List<User> findAllByOrderByRatingDesc() {
        return jpaUserRepository.findAllByOrderByRatingDesc();
    }
}
