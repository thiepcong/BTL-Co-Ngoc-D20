package vn.edu.ptit.sqa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.UserType;

import java.util.Optional;

@Repository
public interface UserTypeRepo extends JpaRepository<UserType, Integer> {
    Optional<UserType> findById(int id);
}
