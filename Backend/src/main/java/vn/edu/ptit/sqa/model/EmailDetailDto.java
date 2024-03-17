package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailDto implements Serializable {
    private Long id;
    private Date createdDate;
    private Integer status;
    private String context;
    private String emailSender;
    private String toEmail;
    private String sendType;
    private String subject;
    private List<EmailAttachmentResponse> emailAttachments;
}
