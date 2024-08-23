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
@Table(name ="emailsender")
public class EmailSender {
    @Id
    String id;

    String token;

    Date date;

}
