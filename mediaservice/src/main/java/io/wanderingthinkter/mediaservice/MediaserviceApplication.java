package io.wanderingthinkter.mediaservice;

import io.wanderingthinkter.mediaservice.utils.Constants;
import io.wanderingthinkter.mediaservice.utils.FileUploadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MediaserviceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MediaserviceApplication.class, args);
		FileUploadUtil.createDirectory(Constants.MEDIA_DIRECTORY);
	}

}
