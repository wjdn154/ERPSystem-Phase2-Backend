package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.EmployeeImage;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.EmployeeImage;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.EmployeeImage.EmployeeImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeImageServiceImpl implements EmployeeImageService {
    private final S3Client s3Client;
    private final EmployeeImageRepository employeeImageRepository;

    private String awsRegion = "ap-northeast-2";

    private String bucketName = "rjsgh-bucket";

//    @Value("${AWS_REGION}")
//    private String awsRegion;
//
//    @Value("${AWS_S3_BUCKET_NAME}")
//    private String bucketName;

    @Override
    public String uploadEmployeeImage(MultipartFile image) {
        try {
            String dbFilePath = saveImageToS3(image);
            EmployeeImage employeeImage = EmployeeImage.builder()
                    .imagePath(dbFilePath)
                    .build();
            employeeImageRepository.save(employeeImage);

            return employeeImage.getImagePath();

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public String saveImageToS3(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + Paths.get(image.getOriginalFilename()).getFileName().toString();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(image.getContentType())
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(image.getBytes()));

        if (putObjectResponse.sdkHttpResponse().isSuccessful()) {
            return "https://" + bucketName + ".s3." + awsRegion + ".amazonaws.com/" + fileName;
        } else {
            throw new IOException("S3 업로드에 실패했습니다.");
        }
    }
}

