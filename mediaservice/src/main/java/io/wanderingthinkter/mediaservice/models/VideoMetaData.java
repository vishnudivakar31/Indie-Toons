package io.wanderingthinkter.mediaservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VideoMetaData {
    private byte[] data;
    private String fileName;

    public VideoMetaData(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }
}
