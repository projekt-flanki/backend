package pl.lodz.p.edu.flanki.errors;

import pl.lodz.p.edu.flanki.dtos.EventResultDto;

public class NotResultOfEventProvidedException extends RuntimeException {
    public NotResultOfEventProvidedException(final EventResultDto eventResultDto) {
        super(String.format("Result of event must be provided when finalizing the game, eventResultDto: %s", eventResultDto));
    }
}
