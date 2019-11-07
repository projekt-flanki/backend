package pl.lodz.p.edu.flanki.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import pl.lodz.p.edu.flanki.repositories.UserRepository;

@SpringBootTest
public class DatabaseIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void smokeTest(){
        //will fail if cannot instantiaze
        eventRepository.findAll();
        userRepository.findByEmail("test@test.com");
    }
}
