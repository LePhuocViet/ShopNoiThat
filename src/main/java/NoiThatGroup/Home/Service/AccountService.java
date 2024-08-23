package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.respone.AccountResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;
import NoiThatGroup.Home.Enity.Account;

import java.util.List;

public interface AccountService {


    List<AccountResponses> getAccount();

    AccountResponses findAccountById(String id);


    void deletedAccountById(String id);


    boolean active(String id);

    UserRespone getMyInf();



}
