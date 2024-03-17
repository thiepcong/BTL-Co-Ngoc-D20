package vn.edu.ptit.sqa.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuntimeExceptionHandling extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 20210508213000564L;

    private String requestId;
    private String requestDate;
    private StackTraceElement traceElement;


    public RuntimeExceptionHandling(String message) {
        super(message);
    }

    public RuntimeExceptionHandling(String requestId,
                                    String requestDate,
                                    String message) {
        super(message);
        this.requestId = requestId;
        this.requestDate = requestDate;
    }

}