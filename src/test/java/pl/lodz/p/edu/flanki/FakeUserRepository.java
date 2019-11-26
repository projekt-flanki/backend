package pl.lodz.p.edu.flanki;

import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.repositories.UserRepository;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FakeUserRepository implements UserRepository {

    private final HashMap<UUID, User> users = new HashMap<>();

    public void withUsers(final List<User> users) {
        final Map<UUID, User> usersMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));
        this.users.putAll(usersMap);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return users.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public UUID save(final User user) {
        final UUID id = UUID.randomUUID();
        users.put(id, user);
        return id;
    }

    @Override
    public List<User> findAllById(final Iterable<UUID> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByOrderByPointsDesc() {
        return users.values()
                .stream()
                .sorted(Comparator.comparingInt(User::getPoints))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByOrderByRatingDesc() {
        return users.values()
                .stream()
                .sorted(Comparator.comparingInt(User::getPoints).reversed())
                .collect(Collectors.toList());
    }
}
