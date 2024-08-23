package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.EmailSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailSender,String> {

}
