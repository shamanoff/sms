package ru.click.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.repository.TemplateRepository;

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

    /**
     * Конструктор для внедрения зависимостей
     *
     * @param tmpRepo репозиторий шаблонов смс
     */
    @Autowired
    public ProstorSmsService(TemplateRepository tmpRepo) {
        notNull(tmpRepo, "Репозиторий шаблонов смс не может быть равным null");
        this.tmpRepo = tmpRepo;
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
        return null;
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
}
