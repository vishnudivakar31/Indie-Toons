package io.wanderingthinkter.mediaservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MediaMetaData {
    private byte[] data;
    private String fileName;

    public MediaMetaData(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }
}
