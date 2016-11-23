package ru.click.sms.service;

import org.springframework.http.ResponseEntity;
import ru.click.sms.model.SmsResponse;

/**
 * Интерфейс для парсера ответа сервера
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@FunctionalInterface
public interface ResponseParser {

    /**
     * Парсит ответа сервера
     * @param response ответ сервера
     * @return сведения о смс, {@link SmsResponse}
     */
    SmsResponse parse(ResponseEntity<String> response);
}
