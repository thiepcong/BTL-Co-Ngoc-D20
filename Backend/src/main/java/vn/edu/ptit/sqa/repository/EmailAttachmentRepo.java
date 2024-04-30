package vn.edu.ptit.sqa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.sqa.entity.EmailAttachment;
@Repository
public interface EmailAttachmentRepo extends JpaRepository<EmailAttachment, Long> {
    EmailAttachment getById(Long id);
}
