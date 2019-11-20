package pl.lodz.p.edu.flanki.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.flanki.FakeEventRepository;
import pl.lodz.p.edu.flanki.FakeUserRepository;
import pl.lodz.p.edu.flanki.WithEventsData;
import pl.lodz.p.edu.flanki.WithUsersData;
import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;
import pl.lodz.p.edu.flanki.mappers.EventMapper;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class EventServiceTest implements WithAssertions, WithEventsData, WithUsersData {

    private final FakeEventRepository eventRepository = new FakeEventRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final UserService userService = mock(UserService.class);
    private final EventMapper eventMapper = new EventMapper(userRepository);
    private final EventService eventService = new EventService(eventRepository, userService, eventMapper);


    @Test
    void shouldReturnProperEventListOfGivenUser() {
        //given
        final User user = thereIsAnUser();
        final List<Event> expectedEvents = thereAreEventsBelongingToGivenUser(user);
        //when
        final List<Event> actualEvents = eventService.getMyEvents();
        //then
        assertThat(actualEvents).containsExactlyInAnyOrderElementsOf(expectedEvents);
    }

    @Test
    void shouldReturnProperEventListWhenEventWasAdded(){
        //given
        final User user = thereIsAnUser();
        final Event expectedEvent = getExampleEventWithOwner(user);
        //when
        final UUID expectedId = eventService.createEvent(expectedEvent);
        final List<Event> actualEvents = eventService.getMyEvents();
        //then
        assertThat(actualEvents).hasSize(1);
        final Event onlyEvent = Iterables.getOnlyElement(actualEvents);
        assertThat(onlyEvent).isEqualTo(expectedEventWithId(expectedEvent, expectedId));
    }

    @Test
    void shouldBeAbleToJoinToTheEvent(){
        //given
        final User user = thereIsAnUser();
        final Event event = getExampleEvent();
        thereIsAnEventInRepository(event);
        //when
        eventService.joinEvent(event.getId());
        //then
        final Event actualEvent = Iterables.getOnlyElement(eventService.getAllEvents());
        assertThat(actualEvent.getFirstTeam()).contains(user);
    }

    @Test
    void shouldBeAbleToRemoveEvent(){
        //given
        thereIsAnUser();
        final List<Event> expectedEvents = getNExampleEvents(2);
        thereAreEventsInRepository(expectedEvents);
        final Event eventToRemove = expectedEvents.get(0);
        //when
        final List<Event> actualEvents = eventService.getAllEvents();
        assertThat(actualEvents).containsExactlyInAnyOrderElementsOf(expectedEvents);
        eventService.remove(eventToRemove.getId());
        final List<Event> actualEventsAfterRemoval = eventService.getAllEvents();
        //then
        assertThat(actualEventsAfterRemoval).hasSize(1);
        assertThat(actualEventsAfterRemoval.get(0)).isEqualTo(expectedEvents.get(1));

    }

    private void thereAreEventsInRepository(final List<Event> events) {
        eventRepository.withEvents(events);
    }

    private void thereIsAnEventInRepository(final Event event) {
        eventRepository.withEvents(Collections.singletonList(event));
    }

    private User thereIsAnUser() {
        final User user = getExampleUser();
        userRepository.withUsers(Collections.singletonList(user));
        when(userService.getUser()).thenReturn(user);
        return user;
    }

    private List<Event> thereAreEventsBelongingToGivenUser(final User user){
        final List<Event> events = getNExampleEventsBelongingToUser(user, 5);
        eventRepository.withEvents(events);
        return events;
    }

    private Event expectedEventWithId(final Event expectedEvent, final UUID id) {
        return expectedEvent.toBuilder().id(id).build();
    }

}