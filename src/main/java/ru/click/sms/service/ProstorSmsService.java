package ru.click.sms.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.click.sms.model.Sender;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.model.Template;
import ru.click.sms.repository.TemplateRepository;
import ru.click.sms.service.exception.IncorrectParamsTemplateException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

/**
 * Реализация интерфейса {@link SmsSender} для
 * смс шлюза  <a href='http://prostor-sms.ru/'>Простор смс</a>
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Service
public class ProstorSmsService implements SmsSender {

    /**
     * Репозиторий шаблонов смс
     */
    private final TemplateRepository tmpRepo;

    private final RestOperations rest;

    private final Sender sender;

    private final ResponseParser responseParser;

    /**
     * Конструктор для внедрения зависимостей
     * @param tmpRepo репозиторий шаблонов смс
     * @param rest http client
     * @param sender свойства отправителя
     * @param responseParser парсер ответа
     */
    @Autowired
    public ProstorSmsService(TemplateRepository tmpRepo, RestOperations rest, Sender sender, ResponseParser responseParser) {
        notNull(tmpRepo, "Репозиторий шаблонов смс не может быть равным null");
        notNull(rest, "Rest Template не может быть равным null");
        this.tmpRepo = tmpRepo;
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
    public SmsResponse send(Integer templateId, String phone) {
        Template smsTemplate = tmpRepo.findOne(templateId);
        if (smsTemplate.hasParams()) {
            throw new IncorrectParamsTemplateException("Нет параметров для шаблона");
        }
        val params = restParams(smsTemplate.getText(), phone);
        ResponseEntity<String> response = rest.getForEntity(sender.sendUri(), String.class, params);
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
    public SmsResponse send(Integer templateId, String phone, Object... args) {
        return null;
    }

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ  {@link SmsResponse}
     */
    @Override
    public SmsResponse guarantedSend(Integer templateId, String phone) {
        return null;
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
    public SmsResponse guarantedSend(Integer templateId, String phone, Object... args) {
        return null;
    }

    private Map<String, ?> restParams(String text, String phone) {
        val map = new HashMap<String, Object>(4);
        map.put("login", sender.getLogin());
        map.put("password", sender.getPassword());
        map.put("phone", phone);
        map.put("text", text);
        return map;
    }
}
