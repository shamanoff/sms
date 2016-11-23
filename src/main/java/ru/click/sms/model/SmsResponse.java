package ru.click.sms.model;


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
     * Статус смс сообщения
     */
    // TODO: 23.11.2016 заменить на enum
    private final String smsStatus;

    /**
     * Конструктор, создающий экземпляр класса на основе
     * идетификатора СМС, пользовательского сообщения и статуса смс сообщения
     *
     * @param smsId     идетификатор смс сообщения, который возвращает СМС-шлюз
     * @param smsReply  пользовательское сообщение
     * @param smsStatus статус смс сообщения
     */
    public SmsResponse(String smsId, String smsReply, String smsStatus) {
        hasText(smsId, "Идентификатор сообщения не может быть пустым");
        hasText(smsStatus, "Сообщение должно иметь статус");
        this.smsId = smsId;
        this.smsReply = smsReply;
        this.smsStatus = smsStatus;
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
    public String reply() {
        return smsReply;
    }

    /**
     * @return статус смс сообщения
     */
    public String status() {
        return smsStatus;
    }
}
