package io.wanderingthinkter.indietoonsusernamangement.utils;

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
}
