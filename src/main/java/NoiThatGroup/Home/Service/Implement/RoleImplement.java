package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.RoleRequest;
import NoiThatGroup.Home.Dto.respone.AccountResponses;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Role;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.RoleRepository;
import NoiThatGroup.Home.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class RoleImplement implements RoleService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;

    @Override
    public void updateRole(RoleRequest roleRequest) {
        Account account = accountRepository.findAccountById(roleRequest.getId());
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        if (!roleRepository.existsByName(roleRequest.getRoles())) throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        Optional<Role> role = roleRepository.findById(roleRequest.getRoles());


        Set<Role> roleA = account.getRoles();
        for (Role role1 : roleA){
            if (role1 == role.get()) throw new AppException(ErrorCode.ROLE_IS_EXIST);
        }
        roleA.add(role.get());
        accountRepository.save(account);

    }
}
