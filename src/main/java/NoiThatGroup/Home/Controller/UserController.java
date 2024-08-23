package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.SignRequest;
import NoiThatGroup.Home.Dto.request.UserRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Dto.respone.SignResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/update")
    ApiResponses<UserRespone> updateUser(@RequestBody UserRequest userRequest){
        return ApiResponses.<UserRespone>builder()
                .result(userService.updateUser(userRequest))
                .build();
    }
}
