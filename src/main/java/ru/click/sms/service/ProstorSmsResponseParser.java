package ru.click.sms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.click.sms.model.SmsResponse;

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
    /**
     * Парсит ответа сервера
     *
     * @param response ответ сервера
     * @return сведения о смс, {@link SmsResponse}
     */
    @Override
    public SmsResponse parse(ResponseEntity<String> response) {
        // TODO: 23.11.2016 написать реализацию
        return null;
    }
}
