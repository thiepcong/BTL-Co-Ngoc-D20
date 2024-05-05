package vn.edu.ptit.sqa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import vn.edu.ptit.sqa.model.reportInfor.ReportInforRequest;

import java.util.List;

@Data
@RequiredArgsConstructor
public class EmailDetailAM {
    ReportInforRequest reportInforRequest;
    @NotNull
    private Integer templateId;
    private List<Long> attachmentIds;
}
