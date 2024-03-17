package vn.edu.ptit.sqa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EMAIL_DETAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "CONTEXTS")
    private String context;

    @Column(name = "EMAIL_SENDER")
    private String emailSender;

    @Column(name = "TO_EMAIL")
    private String toEmail;

    @ManyToOne
    @JoinColumn(name = "EMAIL_TEMPLATE_ID")
    private EmailTemplate emailTemplate;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinTable(
            name = "EMAIL_USED_ATTACHMENT",
            joinColumns = @JoinColumn(name = "EMAIL_DETAIL_ID"),
            inverseJoinColumns = @JoinColumn(name = "ATTACHMENT_ID"))
    private List<EmailAttachment> emailAttachments;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
