package vn.edu.ptit.sqa.exception;

import vn.edu.ptit.sqa.util.message.MessageUtil;

import java.io.Serial;

public class UnAuthorizedException extends RuntimeExceptionHandling {

    @Serial
    private static final long serialVersionUID = 1616402540777823135L;

    public UnAuthorizedException() {
        super(MessageUtil.getMessage("message.unauthorized"));
    }

    public UnAuthorizedException(String message) {
        super(message + " " + MessageUtil.getMessage("message.unauthorized"));
    }
}