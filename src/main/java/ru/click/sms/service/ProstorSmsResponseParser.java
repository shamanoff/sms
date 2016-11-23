package ru.click.sms.service;

import org.springframework.http.ResponseEntity;
import ru.click.sms.model.SmsResponse;

/**
 * Реализация парсера ответа для сервиса Простор Смс.
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public class ProstorSmsResponseParser implements ResponseParser {
    /**
     * Парсит ответа сервера
     *
     * @param response ответ сервера
     * @return сведения о смс, {@link SmsResponse}
     */
    @Override
    public <T> SmsResponse parse(ResponseEntity<T> response) {
        // TODO: 23.11.2016 написать реализацию
        return null;
    }
}
