package com.example.ruhogwartsschool.service;

import com.example.ruhogwartsschool.entity.Avatar;
import com.example.ruhogwartsschool.entity.Student;
import com.example.ruhogwartsschool.repositories.AvatarRepository;
import com.example.ruhogwartsschool.repositories.StudentRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServise {
    @Value("${Avatars.dir.path}")
    private String avatarsDir;
    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    public AvatarServise(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Avatar readAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }


    public void uploadAvatar(long studentId, MultipartFile avatarfile) throws IOException {
        Student student = studentRepository.getById(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(avatarfile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarfile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarfile.getSize());
        avatar.setMediaType(avatarfile.getContentType());
        avatar.setData(generatorDataForDB(filePath));
        avatarRepository.save(avatar);
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] generatorDataForDB(Path filePath) throws IOException {
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

}


