package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.product;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductImage;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.product.ProductImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Override
    public String uploadProductImage(MultipartFile image) {

        try {
            // 이미지 파일 저장을 위한 경로 설정
            String uploadsDir = "src/main/resources/static/uploads/";

            // 이미지 파일 경로를 저장
            String dbFilePath = saveImage(image, uploadsDir);

            // ProductImage 엔티티 생성 및 저장
            ProductImage productImage = ProductImage.builder()
                    .imagePath(dbFilePath)
                    .build();

            productImageRepository.save(productImage);

            return productImage.getImagePath();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 이미지 파일을 저장하는 메서드
    @Override
    public String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + image.getOriginalFilename();

        // 실제 파일이 저장될 경로
        String filePath = uploadsDir + fileName;

        // DB에 저장할 경로 문자열
        String dbFilePath = "/uploads/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;
    }


}
