package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.IdRequest;
import NoiThatGroup.Home.Dto.request.RoleRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Enity.Role;
import NoiThatGroup.Home.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;
    @PutMapping
    ApiResponses<String> updateRole(@RequestBody RoleRequest roleRequest){
        roleService.updateRole(roleRequest);
        return ApiResponses.<String>builder().result("success").build();
    }

    @DeleteMapping
    ApiResponses<String> deletedRole(@RequestBody RoleRequest roleRequest){
        roleService.deletedRole(roleRequest);
        return ApiResponses.<String>builder().result("success").build();
    }

}
