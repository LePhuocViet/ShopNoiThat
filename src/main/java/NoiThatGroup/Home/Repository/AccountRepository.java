package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account , String> {


    Account findAccountById(String id);

    Boolean existsByUsername(String username);

    Account findAccountByUsername(String username);
}
