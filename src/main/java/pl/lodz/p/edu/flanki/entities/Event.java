package pl.lodz.p.edu.flanki.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "EVENTS")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private Instant date;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = User.class)
    private Set<User> owners;

    @ManyToMany(targetEntity = User.class)
    private Set<User> participants;
}
