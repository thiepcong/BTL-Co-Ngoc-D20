package vn.edu.ptit.sqa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.EmailDetail;

@Repository
public interface EmailDetailRepo extends JpaRepository<EmailDetail, Long> {
}
