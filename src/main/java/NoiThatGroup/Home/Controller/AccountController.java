package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.IdRequest;
import NoiThatGroup.Home.Dto.respone.AccountResponses;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Service.AccountService;
import NoiThatGroup.Home.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;
    @GetMapping
    ApiResponses<List<AccountResponses>> getAccount(){
        return ApiResponses.<List<AccountResponses>>builder()
                .result(accountService.getAccount())
                .build();
    }

    @GetMapping("/search")
    ApiResponses<AccountResponses> getAccountById(@RequestParam("id") String id){
        return ApiResponses.<AccountResponses>builder()
                .result(accountService.findAccountById(id))
                .build();
    }

    @PostMapping("/active")
    ApiResponses<Boolean> activeAccount(@RequestBody IdRequest id){
        return ApiResponses.<Boolean>builder()
                .result(accountService.active(id.getId()))
                .build();
    }

    @DeleteMapping("/deleted")
    ApiResponses<String> deletedAccount(@RequestBody IdRequest id){
        accountService.deletedAccountById(id.getId());
        return ApiResponses.<String>builder().result("success").build();
    }

    @GetMapping("/myinf")
    ApiResponses<UserRespone> getMyInf(){
        return ApiResponses.<UserRespone>builder()
                .result(accountService.getMyInf())
                .build();
    }






}
