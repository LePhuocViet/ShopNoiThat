package NoiThatGroup.Home.Dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    @Size(max = 18, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 5, message = "PASSWORD_INVALID")
    @Size(max = 18, message = "PASSWORD_INVALID")
    private String password;

}
