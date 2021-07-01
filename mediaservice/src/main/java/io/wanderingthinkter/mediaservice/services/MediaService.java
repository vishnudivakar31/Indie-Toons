package io.wanderingthinkter.mediaservice.services;

import io.wanderingthinkter.mediaservice.models.MediaRecord;
import io.wanderingthinkter.mediaservice.models.MediaMetaData;
import io.wanderingthinkter.mediaservice.repositories.MediaRecordRepo;
import io.wanderingthinkter.mediaservice.utils.Constants;
import io.wanderingthinkter.mediaservice.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService {

    private final String VIDEO_DIRECTORY = Constants.MEDIA_DIRECTORY + "/VIDEO_DIRECTORY";
    private final String THUMBNAIL_DIRECTORY = Constants.MEDIA_DIRECTORY + "/THUMBNAIL_DIRECTORY";

    @Autowired
    private MediaRecordRepo mediaRecordRepo;

    public MediaRecord uploadFile(MultipartFile multipartVideoFile, MultipartFile multipartThumbnailFile) {
        try {
            String videoFilePath = FileUploadUtil.saveFile(VIDEO_DIRECTORY, UUID.randomUUID().toString() + multipartVideoFile.getOriginalFilename(), multipartVideoFile);
            String thumbnailPath = FileUploadUtil.saveFile(THUMBNAIL_DIRECTORY, UUID.randomUUID().toString() + multipartThumbnailFile.getOriginalFilename(), multipartThumbnailFile);
            MediaRecord mediaRecord = new MediaRecord(videoFilePath, thumbnailPath);
            mediaRecord.setCreatedDate(new Date());
            mediaRecord.setUpdatedDate(new Date());
            return mediaRecordRepo.save(mediaRecord);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public MediaMetaData downloadVideo(Long id) {
        Optional<MediaRecord> optionalMediaRecord = mediaRecordRepo.findById(id);
        if (optionalMediaRecord.isPresent()) {
            MediaRecord mediaRecord = optionalMediaRecord.get();
            try {
                byte[] videoData = FileUploadUtil.getByStreamFromFile(mediaRecord.getFilePath());
                return new MediaMetaData(videoData, FileUploadUtil.getFileName(mediaRecord.getFilePath()));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "no media record found for this id");
    }

    public MediaMetaData downloadThumbnail(Long id) {
        Optional<MediaRecord> optionalMediaRecord = mediaRecordRepo.findById(id);
        if (optionalMediaRecord.isPresent()) {
            MediaRecord mediaRecord = optionalMediaRecord.get();
            try {
                byte[] thumbnailData = FileUploadUtil.getByStreamFromFile(mediaRecord.getThumbnailPath());
                return new MediaMetaData(thumbnailData, FileUploadUtil.getFileName(mediaRecord.getThumbnailPath()));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "no media record found for this id");
    }
}
