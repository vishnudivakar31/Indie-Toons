package io.wanderingthinkter.mediaservice.controllers;

import io.wanderingthinkter.mediaservice.models.MediaRecord;
import io.wanderingthinkter.mediaservice.models.VideoMetaData;
import io.wanderingthinkter.mediaservice.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @GetMapping("/{id}/download_video")
    public ResponseEntity<StreamingResponseBody> downloadVideo(@PathVariable Long id) {
        VideoMetaData videoMetaData = mediaService.downloadVideo(id);
        StreamingResponseBody responseBody = response -> {
            response.write(videoMetaData.getData());
            response.flush();
        };
        return ResponseEntity
                .ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s\"", videoMetaData.getFileName()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}
