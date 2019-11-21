package pl.lodz.p.edu.flanki.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Entity
@Table(name = "EVENTS")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.NONE)
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

    @OneToOne
    @Cascade(value = CascadeType.ALL)
    private Location location;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = User.class)
    private Set<User> owners;

    @ManyToMany(targetEntity = User.class)
    private Set<User> firstTeam;

    @ManyToMany(targetEntity = User.class)
    private Set<User> secondTeam;

    @Column(name = "finalized")
    private boolean finalized;

}
