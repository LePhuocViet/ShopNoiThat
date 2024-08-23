package NoiThatGroup.Home.Mapper;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Enity.Account;

public class AccountMapper {

    public static Account MapperToAccount(AccountRequest accountRequest, Account account){
        account.setUsername(accountRequest.getUsername());
        account.setPassword(accountRequest.getPassword());
        return account;
    }
}
