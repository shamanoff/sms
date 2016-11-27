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
import ru.click.sms.model.Template;
import ru.click.sms.service.annotations.ProstorSmsParser;
import ru.click.sms.service.exception.BadRequestSmsException;
import ru.click.sms.service.exception.IncorrectParamsTemplateException;
import ru.click.sms.service.exception.NotFoundTemplateException;

import javax.annotation.Nullable;
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
     * Репозиторий шаблонов смс
     */
    private final TemplateReader tmpService;

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
     * @param tmpService     репозиторий шаблонов смс
     * @param rest           http client
     * @param sender         свойства отправителя
     * @param responseParser парсер ответа
     */
    @Autowired
    public ProstorSmsService(
            TemplateReader tmpService,
            RestOperations rest,
            Sender sender,
            @ProstorSmsParser ResponseParser responseParser
    ) {
        notNull(tmpService, "Репозиторий шаблонов смс не может быть равным null");
        notNull(rest, "Rest Template не может быть равным null");
        notNull(sender, "Свойства акканута смс шлюза не могут быть равным null");
        notNull(responseParser, "Парсер ответа не может быть раным null");
        this.tmpService = tmpService;
        this.rest = rest;
        this.sender = sender;
        this.responseParser = responseParser;
    }

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ {@link SmsResponse}
     */
    @Override
    public SmsResponse send(int templateId, String phone) {
        return send(templateId, phone, (Object) null);
    }

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ {@link SmsResponse}
     */
    @Override
    public SmsResponse send(int templateId, String phone, @Nullable Object... args) {
        ResponseEntity<String> response = doRequest(templateId, phone, args);
        return responseParser.parse(response);
    }

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ {@link SmsResponse}
     */
    @Override
    public SmsResponse send(int templateId, String phone, @Nullable String[] args) {
        ResponseEntity<String> response = doRequest(templateId, phone, args);
        return responseParser.parse(response);
    }

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ  {@link SmsResponse}
     */
    @Override
    @Async
    public Future<SmsResponse> guaranteedSend(int templateId, String phone) {
        return guaranteedSend(templateId, phone, (Object) null);
    }

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ  {@link SmsResponse}
     */
    @Override
    @Async
    public Future<SmsResponse> guaranteedSend(int templateId, String phone, @Nullable Object... args) {
        for (int i = 0; i < 3; i++) {
            ResponseEntity<String> response = doRequest(templateId, phone, args);
            val status = response.getStatusCode();
            if (!status.is4xxClientError()) {
                SmsResponse smsResponse = responseParser.parse(response);
                return completedFuture(smsResponse);
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

    private ResponseEntity<String> doRequest(int templateId, String phone, @Nullable Object[] args) {
        Template smsTemplate = tmpService.getTemplate(templateId).orElseThrow(NotFoundTemplateException::new);
        String smsText;
        try {
            smsText = args == null ? smsTemplate.getText() : smsTemplate.getText(args);
        } catch (Exception e) {
            throw new IncorrectParamsTemplateException(e.getMessage(), e);
        }
        val uri = uri(smsText, phone);
        HttpHeaders auth = auth(sender.getLogin(), sender.getPassword());
        return rest.exchange(uri, GET, new HttpEntity<>(auth), String.class);
    }
}
