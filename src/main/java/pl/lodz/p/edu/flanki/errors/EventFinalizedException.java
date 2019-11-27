package pl.lodz.p.edu.flanki.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventFinalizedException extends RuntimeException {
    public EventFinalizedException(final String message) {
        super(message);
    }
}