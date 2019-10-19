package pl.lodz.p.edu.flanki.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "EVENT_OWNER")
@NoArgsConstructor
@AllArgsConstructor
public class EventOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany
    @Column(name = "events")
    private List<Event> events;
}