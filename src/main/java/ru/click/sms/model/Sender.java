package ru.click.sms.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@ConfigurationProperties(prefix = "spring.sms.account")
@Data
public class Sender {

    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
    @NotNull
    private Api api;

    public String sendUri() {
        return api.getSend().toString();
    }

    public String statusUri() {
        return api.getStatus().toString();
    }

    @Data
    public static class Api {
        private URI send;
        private URI status;
    }


}
