package io.wanderingthinkter.mediaservice;

import io.wanderingthinkter.mediaservice.services.MediaService;
import io.wanderingthinkter.mediaservice.utils.Constants;
import io.wanderingthinkter.mediaservice.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class MediaserviceApplication {

	@Autowired
	private MediaService mediaService;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MediaserviceApplication.class, args);
		FileUploadUtil.createDirectory(Constants.MEDIA_DIRECTORY);
	}

	@Scheduled(cron = "0 0 0 * * *", zone = "GMT-04:00")
	public void deleteObsoleteMedia() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddThh:mm");
		int deletedMedia = mediaService.deleteObsoleteMedia();
		System.out.println("********** Job for deleting obsolete video **********");
		System.out.println("Date and Time: " + format.format(date));
		System.out.println("Total no. of deleted media: " + deletedMedia);
		System.out.println("************** Job Completed **************");
	}

}
