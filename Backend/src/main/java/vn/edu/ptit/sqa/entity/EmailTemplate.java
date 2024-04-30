package vn.edu.ptit.sqa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EMAIL_TEMPLATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TEMPLATE_NAME")
    private String templateName;

    @Column(name = "TEMPLATE_SUBJECT")
    private String templateSubject;

    @Column(name = "TEMPLATE_CONTENT")
    private String templateContent;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "EMAIL_USED_TEMPLATE_PROPERTY",
//            joinColumns = @JoinColumn(name = "EMAIL_TEMPLATE_ID"),
//            inverseJoinColumns = @JoinColumn(name = "TEMPLATE_PROPERTY_ID"))
//    private List<TemplateProperty> listProperties;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emailTemplate", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("emailTemplate")
    private List<EmailDetail> listEmailDetails;
}
