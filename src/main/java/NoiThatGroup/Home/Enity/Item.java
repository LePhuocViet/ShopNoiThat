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

    @Lob
    private byte[] img;

    private String detail;

    private String material;

    private int rate;

    private double weight;

    private String status;

    private int inventory;

    private int price;


    @ManyToMany
    private Set<Category> categories;

    @OneToMany(mappedBy = "item")
    private List<Comment> comments;
}
