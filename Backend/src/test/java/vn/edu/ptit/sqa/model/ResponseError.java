package vn.edu.ptit.sqa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String timestamp;
    private String message;
    private List<String> errors;
    private String status;
}
