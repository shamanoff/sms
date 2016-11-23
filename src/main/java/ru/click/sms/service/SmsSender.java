package ru.click.sms.service;

import ru.click.sms.model.SmsResponce;

public interface SmsSender {
    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ {@link SmsResponce}
     */
    SmsResponce send(Integer templateId, String phone);

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ {@link SmsResponce}
     */
    SmsResponce send(Integer templateId, String phone, Object... args);

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ  {@link SmsResponce}
     */
    SmsResponce guarantedSend(Integer templateId, String phone);

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ  {@link SmsResponce}
     */

    SmsResponce guarantedSend(Integer templateId, String phone, Object... args);


}
