package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.CartRequest;
import NoiThatGroup.Home.Dto.respone.CartRespone;
import NoiThatGroup.Home.Enity.Cart;

import java.util.List;

public interface CartService {
    CartRespone createCart(CartRequest  cartRequest);

    List<CartRespone> getCarts();
}
