package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateAM {
    private String templateName;
    private String templateSubject;
    private String templateContent;
    private List<TemplatePropertyResponse> listProperties;
}
