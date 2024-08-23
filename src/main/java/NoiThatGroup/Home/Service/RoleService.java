package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.RoleRequest;

public interface RoleService {

    void updateRole(RoleRequest roleRequest);

    void deletedRole(RoleRequest roleRequest);
}
