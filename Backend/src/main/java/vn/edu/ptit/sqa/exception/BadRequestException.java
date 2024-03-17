package vn.edu.ptit.sqa.exception;


import vn.edu.ptit.sqa.util.message.MessageUtil;

import java.io.Serial;

public class BadRequestException extends RuntimeExceptionHandling {

    @Serial
    private static final long serialVersionUID = 1616402540777823135L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String requestId,
                               String requestDate,
                               String message) {
        super(requestId, requestDate,
                message + " " + MessageUtil.getMessage("message.existed"));
    }
}