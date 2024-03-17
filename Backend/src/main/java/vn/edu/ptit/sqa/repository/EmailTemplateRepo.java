package vn.edu.ptit.sqa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.EmailTemplate;

import java.util.Optional;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Integer> {
    Optional<EmailTemplate> findById(Long id);
}
