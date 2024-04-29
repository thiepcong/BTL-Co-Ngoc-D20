package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {
    private Integer id;
    private String templateName;
    private String templateSubject;
    private String templateContent;
    private Timestamp createdDate;
//    private List<TemplatePropertyResponse> listProperties;
}
