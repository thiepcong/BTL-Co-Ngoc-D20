package vn.edu.ptit.sqa.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.sqa.config.AppProperties;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.exception.RuntimeExceptionHandling;
import vn.edu.ptit.sqa.service.FTPService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FTPServiceImpl implements FTPService {

    private final FTPClient ftpClient;

    @Override
    public String uploadFile(MultipartFile multipartFile, String fileDomain) {

        String remoteFilePath = AppProperties.EMAIL_ATTACHMENT.REMOTE_FILE_PATH +
                UUID.randomUUID().toString() + fileDomain;
        try (InputStream inputStream = multipartFile.getInputStream()) {

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

        } catch (IOException e) {
            throw new RuntimeExceptionHandling(e.getMessage());
        }
        return remoteFilePath;
    }

    @Override
    public byte[] readFile(String remoteFilePath) {

        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ftpClient.retrieveFile(remoteFilePath, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("Read file error");
        }
    }
}