package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAttachmentResponse implements Serializable {
    private Long id;
    private String fileName;
    private String originalFileName;
}
