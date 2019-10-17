package pl.lodz.p.edu.flanki.errors;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEventsFoundException extends RuntimeException {
    public NotEventsFoundException(String message) {
        super(message);
    }
}