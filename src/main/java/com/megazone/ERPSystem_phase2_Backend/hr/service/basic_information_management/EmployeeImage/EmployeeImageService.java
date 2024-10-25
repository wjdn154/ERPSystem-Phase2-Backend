package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.EmployeeImage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeImageService {
    String uploadEmployeeImage(MultipartFile image);

    String saveImageToS3(MultipartFile image) throws IOException;
}
