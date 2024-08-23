package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, String> {
}
