package vn.edu.ptit.sqa.exception;

import vn.edu.ptit.sqa.util.message.MessageUtil;

import java.io.Serial;

public class NotFoundException extends RuntimeExceptionHandling {

    @Serial
    private static final long serialVersionUID = 161640254077785349L;

    public NotFoundException(String message) {
        super(message + " " + MessageUtil.getMessage("message.notFound"));
    }

}