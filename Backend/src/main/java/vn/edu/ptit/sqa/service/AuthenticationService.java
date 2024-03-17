package vn.edu.ptit.sqa.service;

import vn.edu.ptit.sqa.model.authentication.LoginRequest;
import vn.edu.ptit.sqa.model.authentication.LoginResponse;
import vn.edu.ptit.sqa.model.authentication.RegisterRequest;

public interface AuthenticationService {
    LoginResponse authenticate(LoginRequest request);

    LoginResponse register(RegisterRequest request);

}