package vn.edu.ptit.sqa.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.ptit.sqa.model.UserDto;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDto userInfo;
}
