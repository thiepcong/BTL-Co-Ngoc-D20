package vn.edu.ptit.sqa.exception;

import vn.edu.ptit.sqa.util.message.MessageUtil;

import java.io.Serial;

public class ForbiddenException extends RuntimeExceptionHandling {

    @Serial
    private static final long serialVersionUID = 1616402540777823135L;

    public ForbiddenException() {
        super(MessageUtil.getMessage("message.forbidden"));
    }

    public ForbiddenException(String requestId,
                              String requestDate,
                              String message) {
        super(requestId, requestDate,
                message + " " + MessageUtil.getMessage("message.forbidden"));
    }
}