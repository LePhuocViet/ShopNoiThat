package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.SignRequest;
import NoiThatGroup.Home.Dto.request.UserRequest;
import NoiThatGroup.Home.Dto.respone.SignResponses;
import NoiThatGroup.Home.Dto.respone.UserRespone;

public interface UserService {

    SignResponses createUser(SignRequest signRequest);

    UserRespone updateUser(UserRequest userRequest);

}
