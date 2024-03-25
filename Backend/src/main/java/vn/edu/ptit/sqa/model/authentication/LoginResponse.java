package vn.edu.ptit.sqa.model.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;

@Data
@AllArgsConstructor
public class LoginResponse {
//    private String userName;
//    private HashSet role;
    private String token;
}
