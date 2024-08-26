package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findItemByCategories_Name(String name);

}
