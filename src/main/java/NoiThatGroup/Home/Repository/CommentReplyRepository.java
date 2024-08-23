package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReply, String> {
}
