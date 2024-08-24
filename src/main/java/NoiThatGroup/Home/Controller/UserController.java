package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.SignRequest;
import NoiThatGroup.Home.Dto.request.UserRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Dto.respone.SignResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    ApiResponses<SignResponses> createUser(@RequestBody @Valid SignRequest signRequest){
        return ApiResponses.<SignResponses>builder()
                .result(userService.createUser(signRequest))
                .build();
    }
    @PutMapping
    ApiResponses<UserRespone> updateUser(@RequestBody UserRequest userRequest){
        return ApiResponses.<UserRespone>builder()
                .result(userService.updateUser(userRequest))
                .build();
    }
}
