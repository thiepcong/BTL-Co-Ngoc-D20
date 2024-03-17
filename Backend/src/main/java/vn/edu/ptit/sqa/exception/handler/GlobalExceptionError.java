package vn.edu.ptit.sqa.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GlobalExceptionError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date timestamp;
    private String message;
    private List<String> errors;
    private HttpStatus status;

    //

    public GlobalExceptionError(final HttpStatus status, final String message, Date timestamp,
                                final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;

    }

    public GlobalExceptionError(final HttpStatus status, final String message, Date timestamp,
                                final String error) {
        super();
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        errors = Collections.singletonList(error);
    }
    //
}