package vn.edu.ptit.sqa.config.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.net.ftp.FTPClient;

@Configuration
public class FTPConfig {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Bean
    public FTPClient ftpClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
        return ftpClient;
    }
}