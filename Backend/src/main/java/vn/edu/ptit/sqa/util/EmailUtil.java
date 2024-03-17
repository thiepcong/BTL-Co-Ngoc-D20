package vn.edu.ptit.sqa.util;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.ptit.sqa.config.AppProperties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Component
public class EmailUtil {

    private static String host;

    private static int port;

    private static String userName;
    private static String password;

    @Autowired
    public EmailUtil(){
        this.host = AppProperties.EMAIL_SERVER.HOST;
        this.port = AppProperties.EMAIL_SERVER.PORT;
        this.userName = AppProperties.EMAIL_SERVER.USERNAME;
        this.password = AppProperties.EMAIL_SERVER.PASSWORD;
    }
    public static void sendEmailWithAttachments(String toAddress, String subject,
                                                String message, Map<String, byte[]> attachments)
            throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress(userName, userName.substring(0, userName.indexOf('@'))));
        msg.setReplyTo(InternetAddress.parse(userName, false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(message, "UTF-8");
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress, false));

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/HTML; charset=UTF-8");
        // Create Multipart object
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        for(String fileName : attachments.keySet()){
            InputStream attachmentStream = new ByteArrayInputStream(attachments.get(fileName));
            DataSource source = new ByteArrayDataSource(attachmentStream, "application/octet-stream");
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(fileName);
            multipart.addBodyPart(attachmentPart);
        }
        msg.setContent(multipart);
        // sends the e-mail
        Transport.send(msg);
    }
}
