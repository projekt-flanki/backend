package pl.lodz.p.edu.flanki.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "EVENT_OWNER")
@NoArgsConstructor
@AllArgsConstructor
public class EventOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany
    @Column(name = "events")
    private List<Event> events;
}