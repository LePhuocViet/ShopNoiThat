package NoiThatGroup.Home.Dto.respone;

import NoiThatGroup.Home.Enity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponses {

    String id;
    String username;
    boolean active;
    Set<Role> role;


}
