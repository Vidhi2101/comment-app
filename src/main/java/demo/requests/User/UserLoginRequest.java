package demo.requests.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserLoginRequest {

    private Long userId;
    private Boolean isLoggedIn;


}
