package io.wanderingthinkter.mediaservice.controllers;

import io.wanderingthinkter.mediaservice.models.MediaRecord;
import io.wanderingthinkter.mediaservice.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mediaservice")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload_file")
    public MediaRecord uploadFile(@RequestParam("video") MultipartFile multipartVideoFile,
                                  @RequestParam("thumbnail") MultipartFile multipartThumbnailFile) {
        return mediaService.uploadFile(multipartVideoFile, multipartThumbnailFile);
    }
}
