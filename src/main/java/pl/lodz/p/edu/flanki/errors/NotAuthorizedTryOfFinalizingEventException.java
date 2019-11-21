package pl.lodz.p.edu.flanki.errors;

import pl.lodz.p.edu.flanki.entities.Event;
import pl.lodz.p.edu.flanki.entities.User;

public class NotAuthorizedTryOfFinalizingEventException extends RuntimeException {
    public NotAuthorizedTryOfFinalizingEventException(final User user, final Event event) {
        super(String.format("User with id %s tried to finalized event with id %s but was not authorized for that action"
                , user.getId(), event.getId()));
    }
}
