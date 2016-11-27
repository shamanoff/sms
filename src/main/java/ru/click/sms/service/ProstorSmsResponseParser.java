package ru.click.sms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.service.exception.BadRequestSmsException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация парсера ответа для сервиса Простор Смс.
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component("prostor-sms-parser")
public class ProstorSmsResponseParser implements ResponseParser {

    private final Map<String, String> responseMessages;

    public ProstorSmsResponseParser() {
        Map<String, String> map = new HashMap<>(7);
        // TODO: 25/11/2016 заполнить мап
        this.responseMessages = Collections.unmodifiableMap(map);
    }

    /**
     * Парсит ответа сервера
     *
     * @param response ответ сервера
     * @return сведения о смс, {@link SmsResponse}
     */
    @Override
    public SmsResponse parse(ResponseEntity<String> response) {

        String responseBody = response.getBody();

        String[] smsStatusAndID = responseBody.split(";");

        if ("accepted".equals(smsStatusAndID[0])) {
            return SmsResponse.of(smsStatusAndID[1], "Сообщение принято сервисом");
        }
        String responseMessage = responseMessages.get(smsStatusAndID[1]);
        throw new BadRequestSmsException(responseMessage);
    }
}
