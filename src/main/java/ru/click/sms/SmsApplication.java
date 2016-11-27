package ru.click.sms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandlerImpl;
import ru.click.sms.model.Sender;

@SpringBootApplication
@EnableConfigurationProperties(Sender.class)
@EnableAsync
public class SmsApplication {

    @Bean
    public RestOperations restOperations() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new UriTemplateHandlerImpl());
        return restTemplate;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}
}
