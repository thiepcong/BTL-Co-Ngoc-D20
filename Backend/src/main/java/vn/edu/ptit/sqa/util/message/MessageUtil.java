package vn.edu.ptit.sqa.util.message;

import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class MessageUtil {
    private final static String BASE_NAME = "messages";

    public static String getMessage(String code, Locale locale) {
        return getMessage(code, locale, (Object) null);
    }

    public static String getMessage(String code, Locale locale, Object... args) {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME, locale);
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle(BASE_NAME);
        }

        String message;
        try {
            message = resourceBundle.getString(code);
            message = MessageFormat.format(message, args);
        } catch (Exception ex) {
            message = code;
        }

        return message;
    }

    public static String getMessage(String code) {
        return getMessage(code, LocaleContextHolder.getLocale(), (Object) null);
    }

    public static String getMessage(String code, Object... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }
}
