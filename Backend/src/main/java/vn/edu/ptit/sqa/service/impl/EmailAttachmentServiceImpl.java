package vn.edu.ptit.sqa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.sqa.config.AppProperties;
import vn.edu.ptit.sqa.entity.EmailAttachment;
import vn.edu.ptit.sqa.exception.BadRequestException;
import vn.edu.ptit.sqa.model.EmailAttachmentResponse;
import vn.edu.ptit.sqa.repository.EmailAttachmentRepo;
import vn.edu.ptit.sqa.service.EmailAttachmentService;
import vn.edu.ptit.sqa.util.converter.ConverterUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailAttachmentServiceImpl implements EmailAttachmentService {

    @Autowired
    EmailAttachmentRepo emailAttachmentRepo;

    @Autowired
    FTPServiceImpl ftpService;

    @Override
    public List<EmailAttachmentResponse> uploadAllAttachments(List<MultipartFile> files) {
        List<EmailAttachmentResponse> listAttachments = new ArrayList<>();
        for(MultipartFile file : files){
            int lastDotIndex = file.getOriginalFilename().lastIndexOf('.');
            if (lastDotIndex > 0) {
                String fileDomain = file.getOriginalFilename().substring(lastDotIndex);
                if(!AppProperties.EMAIL_ATTACHMENT.VALID_FILE_TYPE.contains(fileDomain))
                    throw new BadRequestException("Type of File " + file.getOriginalFilename() + " is not supported");
                else{
                    String fileName = ftpService.uploadFile(file, fileDomain);
                    EmailAttachment emailAttachment = new EmailAttachment();
                    emailAttachment.setFileName(fileName);
                    emailAttachment.setOriginalFileName(file.getOriginalFilename());
                    emailAttachment = emailAttachmentRepo.save(emailAttachment);
                    listAttachments.add(ConverterUtil.mappingToObject(emailAttachment, EmailAttachmentResponse.class));
                }
            }else{
                throw new BadRequestException("File is invalid: " + file.getOriginalFilename());
            }
        }
        return listAttachments;
    }

}