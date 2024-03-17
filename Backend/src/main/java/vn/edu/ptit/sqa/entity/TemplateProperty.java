package vn.edu.ptit.sqa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TEMPLATE_PROPERTY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateProperty {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PROPERTY_KEY")
    private String propertyKey;

    @ManyToMany(mappedBy = "listProperties", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EmailTemplate> listTemplates;
}
