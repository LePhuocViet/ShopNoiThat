package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.CartRequest;
import NoiThatGroup.Home.Dto.respone.CartRespone;
import NoiThatGroup.Home.Enity.*;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.*;
import NoiThatGroup.Home.Service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class CartImplement implements CartService {

    AccountRepository accountRepository;
    UserRepository userRepository;

    ItemRepository itemRepository;

    ShoppingRepository shoppingRepository;

    CartRepository cartRepository;
    @Override
    public CartRespone createCart(CartRequest cartRequest) {
        var security = SecurityContextHolder.getContext();
        String username = security.getAuthentication().getName();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        User user = userRepository.findUserByAccountId(account.getId());
        Optional<Shopping> shopping = shoppingRepository.findById(user.getShopping().getId());
        if (shopping.isEmpty()) throw new AppException(ErrorCode.USER_NOT_FOUND);
        Optional<Item> item = itemRepository.findById(cartRequest.getId_item());
        if (item.isEmpty()) throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        Set<Item> items = new HashSet<>();
        items.add(item.get());
        Cart cart = Cart.builder()
                .doc(new Date())
                .price(item.get().getPrice()*cartRequest.getQuantity())
                .quantity(cartRequest.getQuantity())
                .shopping(shopping.get())
                .item(items)
                .build();
        shopping.get().setCount(shopping.get().getCount() + cartRequest.getQuantity());
        shopping.get().setTotal(shopping.get().getTotal() + item.get().getPrice()*cartRequest.getQuantity());
        shoppingRepository.save(shopping.get());
        cartRepository.save(cart);
        return CartRespone.builder()
                .quantity(cartRequest.getQuantity())
                .id_item(cartRequest.getId_item())
                .build();
    }

    @Override
    public List<CartRespone> getCarts() {
        List<CartRespone> cartRespones = new ArrayList<>();
        var security = SecurityContextHolder.getContext();
        String username = security.getAuthentication().getName();
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        User user = userRepository.findUserByAccountId(account.getId());
        Optional<Shopping> shopping = shoppingRepository.findById(user.getShopping().getId());
        if (shopping.isEmpty()) throw new AppException(ErrorCode.USER_NOT_FOUND);
        List<Cart> cartList = cartRepository.findCartByShoppingId(shopping.get().getId());
        if (cartList.isEmpty()) throw  new AppException(ErrorCode.CART_NOT_FOUND);

        for (int i = 0; i < cartList.size();i++){
            cartRespones.add(new CartRespone());
            Set<Item> items = cartList.get(i).getItem();
            cartRespones.get(i).setId_item(items.iterator().next().getId());
            cartRespones.get(i).setQuantity(cartList.get(i).getQuantity());

        }
        return cartRespones;
    }
}
