package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Enity.Shopping;
import NoiThatGroup.Home.Repository.ShoppingRepository;
import NoiThatGroup.Home.Service.ShoppingService;
import NoiThatGroup.Home.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingImplement implements ShoppingService {

    ShoppingRepository shoppingRepository;
    @Override
    public void createShopping(Shopping shopping) {
        shoppingRepository.save(shopping);
    }
}
