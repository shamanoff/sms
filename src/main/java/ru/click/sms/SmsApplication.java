package ru.click.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.click.sms.model.Sender;

@SpringBootApplication
@EnableConfigurationProperties(Sender.class)
public class SmsApplication {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}
}
