package NoiThatGroup.Home.Enity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "category")
public class Category {

    @Id
    private String name;


}
