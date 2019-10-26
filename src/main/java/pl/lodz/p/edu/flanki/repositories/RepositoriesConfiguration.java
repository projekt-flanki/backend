package pl.lodz.p.edu.flanki.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RepositoriesConfiguration {

    @Bean
    EventRepository eventRepository(final JpaEventRepository jpaEventRepository){
        return new EventsStorage(jpaEventRepository);
    }

    @Bean
    UserRepository userRepository(final JpaUserRepository jpaUserRepository){
        return new UsersStorage(jpaUserRepository);
    }
}
