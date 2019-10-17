package pl.lodz.p.edu.flanki.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDto {

    private String name;

    private String location;

    private String description;
}