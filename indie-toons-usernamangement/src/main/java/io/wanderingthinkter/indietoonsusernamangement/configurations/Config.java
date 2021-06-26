package io.wanderingthinkter.indietoonsusernamangement.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:config.properties")
@Getter
public class Config {
    @Value("${email_address}")
    private String emailAddress;

    @Value("${email_password}")
    private String emailPassword;
}
