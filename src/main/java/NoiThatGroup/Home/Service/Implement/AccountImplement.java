package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.request.PasswordRequest;
import NoiThatGroup.Home.Dto.respone.AccountResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.User;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Mapper.AccountMapper;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.ShoppingRepository;
import NoiThatGroup.Home.Repository.UserRepository;
import NoiThatGroup.Home.Service.AccountService;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountImplement implements AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    ShoppingRepository shoppingRepository;

    @Override
    public List<AccountResponses> getAccount() {
        List<AccountResponses> accountResponsesList = new ArrayList<>();
        List<Account> accountList = accountRepository.findAll();
        for (Account account : accountList){
            AccountResponses accountResponses = new AccountResponses();
                accountResponses.setId(account.getId());
                accountResponses.setUsername(account.getUsername());
                accountResponses.setActive(account.isActive());
                accountResponses.setRole(account.getRoles());
                accountResponsesList.add(accountResponses);
        }
        return accountResponsesList;
    }

    @Override
    public AccountResponses findAccountById(String id) {
        if(accountRepository.findAccountById(id) == null){
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        Account account = accountRepository.findAccountById(id);

        return AccountResponses.builder()
                .id(account.getId())
                .username(account.getUsername())
                .active(account.isActive())
                .role(account.getRoles())
                .build();
    }

    @Override
    public void deletedAccountById(String id) {
        if (accountRepository.findAccountById(id) == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        User user = userRepository.findUserByAccountId(id);
        userRepository.deleteById(user.getId());

    }

    @Override
    public boolean active(String id) {
        Account account = accountRepository.findAccountById(id);

        if (account == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        boolean newStatus = !account.isActive();
        account.setActive(newStatus);
        accountRepository.save(account);

        return newStatus;
    }

    @Override
    public UserRespone getMyInf() {
        var security = SecurityContextHolder.getContext();
        String name = security.getAuthentication().getName();
        Account account = accountRepository.findAccountByUsername(name);
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        User user = userRepository.findUserByAccountId(account.getId());

        return UserRespone.builder()
                .username(account.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .sex(user.getSex())
                .address(user.getAddress())
                .build();
    }


}
