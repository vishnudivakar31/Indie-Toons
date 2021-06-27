package io.wanderingthinkter.indietoonsusernamangement.configurations;

import io.wanderingthinkter.indietoonsusernamangement.utils.FileUploadUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StartupFiles {
    private final List<String> FILE_DIRECTORIES = Arrays.asList("images");

    public void createStartUpFiles() {
        FILE_DIRECTORIES.stream().forEach(directory -> {
            try {
                FileUploadUtil.createDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
