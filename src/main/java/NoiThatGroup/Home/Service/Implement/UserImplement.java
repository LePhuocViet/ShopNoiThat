package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.SignRequest;
import NoiThatGroup.Home.Dto.request.UserRequest;
import NoiThatGroup.Home.Dto.respone.SignResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Role;
import NoiThatGroup.Home.Enity.Shopping;
import NoiThatGroup.Home.Enity.User;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Enums.RoleCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.ShoppingRepository;
import NoiThatGroup.Home.Repository.UserRepository;
import NoiThatGroup.Home.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserImplement implements UserService {
    UserRepository userRepository;
    AccountRepository accountRepository;
    ShoppingRepository shoppingRepository;

    @Override
    public SignResponses createUser(SignRequest signRequest) {

        Shopping shopping = new Shopping();
        shoppingRepository.save(shopping);

        if (!signRequest.getPassword().equals(signRequest.getRepassword()))
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);

        if(accountRepository.existsByUsername(signRequest.getUsername()))
            throw new AppException(ErrorCode.USERNAME_IS_EXIST);

        if(userRepository.existsByEmail(signRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_IS_EXIST);


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(RoleCode.USER.name(),""));


        Account account = Account.builder().username(signRequest.getUsername())
                .password(passwordEncoder.encode(signRequest.getPassword())).roles(roleSet).build();
        accountRepository.save(account);


        User user = User.builder().name(signRequest.getName()).email(signRequest.getEmail()).phone(signRequest.getPhone())
                .address(signRequest.getAddress()).sex(signRequest.getSex()).account(account).shopping(shopping).build();
        userRepository.save(user);


        return SignResponses.builder()
                .name(user.getName()).email(user.getEmail()).address(user.getAddress()).phone(user.getPhone()).sex(user.getSex()).username(account.getUsername())
                .build();
    }

    @Override
    public UserRespone updateUser(UserRequest userRequest) {
        Account account = accountRepository.findAccountByUsername(userRequest.getUsername());
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        User user = userRepository.findUserByAccountId(account.getId());
        user.setName(userRequest.getName());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setSex(userRequest.getSex());
        userRepository.save(user);
        return UserRespone.builder()
                .username(account.getUsername())
                .address(user.getAddress())
                .phone(user.getPhone())
                .name(user.getName())
                .sex(user.getSex())
                .build();
    }
}
