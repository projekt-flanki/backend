package pl.lodz.p.edu.flanki;

import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.Location;
import pl.lodz.p.edu.flanki.entities.User;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface WithEventsData {
    default List<Event> getNExampleEventsBelongingToUser(final User user, final int n) {
        return getNExampleEvents(n).stream()
                .map(e -> e.toBuilder().owners(Collections.singleton(user)).build())
                .collect(Collectors.toList());
    }

    default List<Event> getNExampleEvents(final int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> getExampleEvent())
                .collect(Collectors.toList());
    }

    default Event getExampleEvent(){
        return Event
                .builder()
                .id(UUID.randomUUID())
                .name(generateRandomString())
                .date(Instant.now())
                .location(generateRandomLocation())
                .description(generateRandomString())
                .owners(Collections.emptySet())
                .participants(Collections.emptySet())
                .build();
    }

    default Event getExampleEventWithOwner(final User owner){
        return getExampleEvent().toBuilder()
                .owners(Collections.singleton(owner))
                .build();
    }

    default String generateRandomString(){
        final byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    default Location generateRandomLocation(){
        List<Double> twoDoubles = new Random().doubles(2).boxed().collect(Collectors.toList());
        return Location.builder()
                .latitude(twoDoubles.get(0))
                .longitude(twoDoubles.get(1))
                .build();
    }
}
