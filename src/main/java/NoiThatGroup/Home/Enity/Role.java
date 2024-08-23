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
@Table(name = "role")
public class Role {

    @Id
    private String name;

    private String description;

}
