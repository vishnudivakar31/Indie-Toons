package io.wanderingthinkter.indieservice.services;

import io.wanderingthinkter.indieservice.repositories.MediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private MediaRepo mediaRepo;
}
