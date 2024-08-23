package NoiThatGroup.Home.Dto.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRespone {

    private String username;

    private String name;

    private String phone;

    private String address;

    private String sex;

}
