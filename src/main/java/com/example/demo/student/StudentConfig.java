package com.example.demo.student;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ConfigurationProperties(prefix = "customproperty")
@Configuration
@Log4j2
@Getter
@Setter
public class StudentConfig {


    String baseUrl;

    @Bean
    RestTemplate thirdPartyAPICall(){
        RestTemplate restTemplate = new RestTemplate();
        log.info("3rd party baseUrl is:"+baseUrl);
        return restTemplate;
    }

}
