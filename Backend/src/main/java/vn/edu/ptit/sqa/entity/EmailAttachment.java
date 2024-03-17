package vn.edu.ptit.sqa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "EMAIL_ATTACHMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FILE_PATH")
    private String fileName;

    @Column(name = "ORRIGINAL_FILENAME")
    private String originalFileName;

    @ManyToMany(mappedBy = "emailAttachments", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<EmailDetail> emailDetails;
}
