package io.wanderingthinkter.mediaservice.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static String saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + filename, ioe);
        }
    }

    public static void createDirectory(String directory) throws IOException {
        Path uploadPath = Paths.get(directory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
    }

    public static byte[] getByStreamFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    public static String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.delete(path);
    }
}
