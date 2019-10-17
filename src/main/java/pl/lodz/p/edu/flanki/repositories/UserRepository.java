package pl.lodz.p.edu.flanki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.flanki.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
