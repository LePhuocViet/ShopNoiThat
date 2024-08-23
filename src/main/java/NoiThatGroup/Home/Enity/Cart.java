package NoiThatGroup.Home.Enity;


import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date doc;

    private int quantity;

    private int price;


    @ManyToOne
    @JoinColumn(name = "shopping_id")
    private Shopping shopping;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private Set<Item> item;
}
