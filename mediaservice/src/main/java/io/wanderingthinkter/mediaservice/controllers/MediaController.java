package io.wanderingthinkter.mediaservice.controllers;

import io.wanderingthinkter.mediaservice.models.MediaRecord;
import io.wanderingthinkter.mediaservice.models.MediaMetaData;
import io.wanderingthinkter.mediaservice.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/mediaservice")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping()
    public List<MediaRecord> getAllMedia() {
        return mediaService.getAllMediaRecords();
    }

    @PostMapping("/upload_file")
    public MediaRecord uploadFile(@RequestParam("video") MultipartFile multipartVideoFile,
                                  @RequestParam("thumbnail") MultipartFile multipartThumbnailFile) {
        return mediaService.uploadFile(multipartVideoFile, multipartThumbnailFile);
    }

    @GetMapping("/{id}/download_video")
    public ResponseEntity<StreamingResponseBody> downloadVideo(@PathVariable Long id) {
        MediaMetaData mediaMetaData = mediaService.downloadVideo(id);
        StreamingResponseBody responseBody = response -> {
            response.write(mediaMetaData.getData());
            response.flush();
        };
        return ResponseEntity
                .ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s\"", mediaMetaData.getFileName()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }

    @GetMapping("/{id}/download_thumbnail")
    public ResponseEntity<StreamingResponseBody> downloadThumbnail(@PathVariable Long id) {
        MediaMetaData mediaMetaData = mediaService.downloadThumbnail(id);
        StreamingResponseBody responseBody = response -> {
            response.write(mediaMetaData.getData());
            response.flush();
        };
        return ResponseEntity
                .ok()
                .header("Content-Disposition", String.format("attachment; filename=\"%s\"", mediaMetaData.getFileName()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(responseBody);
    }
}
