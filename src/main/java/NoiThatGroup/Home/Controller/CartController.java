package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.CartRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Dto.respone.CartRespone;
import NoiThatGroup.Home.Enity.Cart;
import NoiThatGroup.Home.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping
    ApiResponses<CartRespone> createCart(@RequestBody CartRequest cartRequest){
        return ApiResponses.<CartRespone>builder()
                .result(cartService.createCart(cartRequest))
                .build();
    }

    @GetMapping
    ApiResponses<List<CartRespone>> getCart(){
        return ApiResponses.<List<CartRespone>>builder()
                .result(cartService.getCarts())
                .build();
    }
}
