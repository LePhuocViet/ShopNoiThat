package NoiThatGroup.Home.Enity;


import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Table(name = "commentReply")
public class CommentReply {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String context;

    private Date doc;

    private int rate;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
