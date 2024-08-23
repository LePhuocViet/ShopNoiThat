package NoiThatGroup.Home.Enity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "invalidated_token")
public class InvalidatedToken {

    @Id
    String id;
    Date expiryTime;

}
