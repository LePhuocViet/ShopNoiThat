package NoiThatGroup.Home.Repository;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByName(String name);
}
