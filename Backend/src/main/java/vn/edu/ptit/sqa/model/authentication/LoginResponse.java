package vn.edu.ptit.sqa.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.ptit.sqa.model.UserDto;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class LoginResponse {
//    private String userName;
//    private HashSet role;
    private String token;
    private UserDto userInfo;
}
