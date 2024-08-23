package NoiThatGroup.Home.Enity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String img;

    private String detail;

    private String material;

    private boolean rate;

    private boolean weight;

    private String status;

    private int inventory;

    private int price;

    @OneToMany(mappedBy = "item")
    private List<Comment> comments;

    @ManyToMany
    private Set<Category> categories;
}
