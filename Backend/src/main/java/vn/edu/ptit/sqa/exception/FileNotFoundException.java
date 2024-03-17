package vn.edu.ptit.sqa.exception;


import vn.edu.ptit.sqa.util.message.MessageUtil;

public class FileNotFoundException extends RuntimeExceptionHandling {
    public FileNotFoundException(String message) {
        super(message + MessageUtil.getMessage("message.fileNotFound"));
    }
}