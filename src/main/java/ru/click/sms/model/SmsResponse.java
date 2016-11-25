package ru.click.sms.model;


import java.util.Optional;

import static org.springframework.util.Assert.hasText;

/**
 * Этот класс хранит ответ на запрос отправки СМС
 */
public class SmsResponse {
    /**
     * Идетификатор сообщения, который возвращает СМС-шлюз
     */
    private final String smsId;
    /**
     * Пользовательское сообщение
     */
    private final String smsReply;


    /**
     * Конструктор, создающий экземпляр класса на основе
     * идетификатора СМС, пользовательского сообщения
     *
     * @param smsId     идетификатор смс сообщения, который возвращает СМС-шлюз
     * @param smsReply  пользовательское сообщение
     */
    private SmsResponse(String smsId, String smsReply) {
        hasText(smsId, "Идентификатор сообщения не может быть пустым");
        this.smsId = smsId;
        this.smsReply = smsReply;
    }

    /**
     * Статический конструктор
     * @param smsId     идетификатор смс сообщения, который возвращает СМС-шлюз
     * @param smsReply  пользовательское сообщение
     * @return ответ сервера на отправку смс
     */
    public static SmsResponse of(String smsId, String smsReply) {
        return new SmsResponse(smsId, smsReply);
    }

    /**
     * Статический конструктор для создания ответа с пустым сообщением
     * @param smsId     идетификатор смс сообщения, который возвращает СМС-шлюз
     * @return ответ сервера на отправку смс
     */
    public static SmsResponse of(String smsId) {
        return new SmsResponse(smsId, null);
    }


    /**
     * @return идентификатор смс сообщения
     */
    public String id() {
        return smsId;
    }

    /**
     * @return пользовательское сообщение
     */
    public Optional<String> reply() {
        return Optional.ofNullable(smsReply);
    }

}
