package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
