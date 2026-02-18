package com.programmingtechie.tasklist.service.impl;

import com.programmingtechie.tasklist.domain.exception.ImageUploadException;
import com.programmingtechie.tasklist.domain.task.TaskImage;
import com.programmingtechie.tasklist.service.ImageService;
import com.programmingtechie.tasklist.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    public String upload(TaskImage image) {
        try {
            createBucket();
        } catch (Exception ex) {
            throw new ImageUploadException("Image upload faild " + ex.getMessage());
        }

        MultipartFile file = image.getImage();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name. ");
        }
        String fileName = generateFilename(file);
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload fail " + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;

    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder().stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName).build());
    }

    private String generateFilename(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.
                bucketExists(BucketExistsArgs.builder().
                        bucket(minioProperties.getBucket()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
        }
    }
}
