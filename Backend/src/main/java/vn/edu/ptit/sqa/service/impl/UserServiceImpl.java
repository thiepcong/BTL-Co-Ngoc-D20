package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.ptit.sqa.entity.User;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.NotFoundException;
import vn.edu.ptit.sqa.repository.UserRepository;
import vn.edu.ptit.sqa.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User khong hop le"));
    }
}
