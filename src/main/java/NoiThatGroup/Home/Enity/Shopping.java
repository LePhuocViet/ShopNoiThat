package NoiThatGroup.Home.Enity;


import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "shopping")
public class Shopping {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int count;

    private int total;

    @OneToMany(mappedBy = "shopping")
    private List<Cart> carts;

}
