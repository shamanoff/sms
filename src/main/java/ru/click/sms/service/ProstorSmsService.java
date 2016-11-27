package ru.click.sms.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import ru.click.sms.model.Sender;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.service.annotations.ProstorSmsParser;
import ru.click.sms.service.exception.BadRequestSmsException;

import java.util.concurrent.Future;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.util.Assert.notNull;
import static ru.click.sms.utils.Utils.sleep;

/**
 * Реализация интерфейса {@link SmsSender} для
 * смс шлюза  <a href='http://prostor-sms.ru/'>Простор смс</a>
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service("prostor-sms-sender")
@Slf4j
public class ProstorSmsService implements SmsSender {

    /**
     * Http клиент
     */
    private final RestOperations rest;

    /**
     * Аккаунт от смс шлюза
     */
    private final Sender sender;

    /**
     * Парсер ответа
     */
    private final ResponseParser responseParser;

    /**
     * Конструктор для внедрения зависимостей
     *
     * @param rest           http client
     * @param sender         свойства отправителя
     * @param responseParser парсер ответа
     */
    @Autowired
    public ProstorSmsService(
            RestOperations rest,
            Sender sender,
            @ProstorSmsParser ResponseParser responseParser
    ) {
        notNull(rest, "Rest Template не может быть равным null");
        notNull(sender, "Свойства акканута смс шлюза не могут быть равным null");
        notNull(responseParser, "Парсер ответа не может быть раным null");
        this.rest = rest;
        this.sender = sender;
        this.responseParser = responseParser;
    }

    /**
     * Метод для отправки СМС
     *
     * @param text  текст сообщения
     * @param phone номер телефона
     * @return смс ответ {@link SmsResponse}
     */
    @Override
    public String send(String text, String phone) {
        ResponseEntity<String> response = doRequest(text, phone);
        return responseParser.parse(response);
    }

    private ResponseEntity<String> doRequest(String text, String phone) {
        val uri = uri(text, phone);
        HttpHeaders auth = auth(sender.getLogin(), sender.getPassword());
        return rest.exchange(uri, GET, new HttpEntity<>(auth), String.class);
    }


    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param text  текст сообщения
     * @param phone номер телефона
     * @return смс ответ  {@link SmsResponse}
     */
    @Override
    @Async
    public Future<String> guaranteedSend(String text, String phone) {
        for (int i = 0; i < 3; i++) {
            ResponseEntity<String> responseEntity = doRequest(text, phone);
            val status = responseEntity.getStatusCode();
            if (!status.is4xxClientError()) {
                return completedFuture(text);
            }
            sleep(10);

        }
        throw new BadRequestSmsException("Сервис не доступен");

    }


    private String uri(String text, String phone) {
        String textEnc = encode(text);
        //log.debug("Text parameter: " + textEnc);
        return UriComponentsBuilder
                .fromHttpUrl(sender.sendUri())
                .queryParam("phone", "%2B7".concat(phone))
                .queryParam("text", textEnc)
                .build().toUriString();
    }


}
