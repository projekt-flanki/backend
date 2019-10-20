package pl.lodz.p.edu.flanki.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.flanki.dtos.EventDto;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import pl.lodz.p.edu.flanki.repositories.EventRepository;
import pl.lodz.p.edu.flanki.repositories.UserRepository;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventServiceTest {

    private final EventRepository eventRepository = mock(EventRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = mock(UserService.class);
    private final EventMapper eventMapper = new EventMapper(userRepository);
    private final EventService eventService = new EventService(eventRepository, userService, eventMapper);


    @Test
    void shouldReturnProperEventListOfGivenUser() {
        //given
        final User user = thereIsAnUser();
        final List<Event> expectedEvents = thereAreEventsBelongingToUser(user);
        //when
        final List<EventDto> actualEventsDto = eventService.getMyEvents();
        final List<Event> actualEvents = actualEventsDto.stream().map(eventDto -> eventMapper.toModel(eventDto))
                .collect(Collectors.toList());
        //then
        assertThat(actualEvents).containsExactlyElementsOf(expectedEvents);
    }

    private List<Event> thereAreEventsBelongingToUser(final User user) {
        return Arrays.asList(
                Event
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .name("EXAMPLE")
                        .date(Instant.now())
                        .location("EXAMPLE_LOCATION")
                        .description("EXAMPLE_DESCRIPTION")
                        .owners(Collections.singleton(user))
                        .participants(Collections.emptySet())
                        .build(),
                Event
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .name("EXAMPLE")
                        .date(Instant.now())
                        .location("EXAMPLE_LOCATION")
                        .description("EXAMPLE_DESCRIPTION")
                        .owners(Collections.singleton(user))
                        .participants(Collections.emptySet())
                        .build(),
                Event
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .name("EXAMPLE")
                        .date(Instant.now())
                        .location("EXAMPLE_LOCATION")
                        .description("EXAMPLE_DESCRIPTION")
                        .owners(Collections.singleton(user))
                        .participants(Collections.emptySet())
                        .build()
        );
    }

    private User thereIsAnUser() {
        final String DUMMY_ID = "DUMMY_ID";
        final String DUMMY_EMAIL = "DUMMY_EMAIL@DUMMY.COM";
        final String DUMMY_PASSWORD = "DUMMY_PASSWORD";
        final String DUMMY_USERNAME = "DUMMY_PASSWORD";

        return User.builder()
                .id(DUMMY_ID)
                .email(DUMMY_EMAIL)
                .password(DUMMY_PASSWORD)
                .username(DUMMY_USERNAME)
                .build();
    }

}