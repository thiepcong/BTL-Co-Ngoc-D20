package vn.edu.ptit.sqa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EmailDetailAM {

    private List<String> toList;
    @NotNull
    private TemplateResponse template;
    private List<EmailAttachmentResponse> attachments;
}
