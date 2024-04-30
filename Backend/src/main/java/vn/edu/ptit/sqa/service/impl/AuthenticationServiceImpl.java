package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.config.jwt.JwtServiceImpl;
import vn.edu.ptit.sqa.entity.Role;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.enums.RoleName;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.ExistedException;
import vn.edu.ptit.sqa.model.UserDto;
import vn.edu.ptit.sqa.model.authentication.LoginRequest;
import vn.edu.ptit.sqa.model.authentication.LoginResponse;
import vn.edu.ptit.sqa.model.authentication.RegisterRequest;
import vn.edu.ptit.sqa.repository.RoleRepo;
import vn.edu.ptit.sqa.repository.UserRepository;
import vn.edu.ptit.sqa.service.AuthenticationService;
import vn.edu.ptit.sqa.service.UserService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtServiceImpl jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Optional<User> user = Optional.ofNullable(userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new ExistedException("Username " + request.getUsername())));
        var token = jwtService.generateToken(user.get());
        return new LoginResponse(token, ConverterUtil.mappingToObject(user, UserDto.class));
    }


    @Override
    public LoginResponse register(RegisterRequest request) {
        Optional<User> userByUserName = userRepo.findByUsername(request.getUsername());
        Optional<User> userByEmail = userRepo.findByEmail(request.getEmail());
        if(userByUserName.isPresent()) throw new ExistedException("Username " + request.getUsername());
        if(userByEmail.isPresent()) throw new ExistedException("Email " + request.getEmail());
        User user = ConverterUtil.mappingToObject(request, User.class);
        user.setCreatAt(new Timestamp(System.currentTimeMillis()));
        Optional<Role> role = roleRepo.findByName(RoleName.STAFF);
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        user.setRoles(roles);
        if(!checkPassword(request.getPassword())){
            throw new BadRequestException("Password must contain at least 8 characters and include at least 1 uppercase letter, 1 lowercase letter, and 1 special character");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        var token = jwtService.generateToken(user);
        UserDto  userDto = ConverterUtil.mappingToObject(user, UserDto.class);
        return new LoginResponse(token, userDto);
    }

    public static boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[^A-Za-z0-9]").matcher(password).find()) {
            return false;
        }
        if (password.contains(" ") || password.contains("\t")) {
            return false;
        }
        return true;
    }
}