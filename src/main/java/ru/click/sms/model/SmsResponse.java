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
     * Конструктор, создающий экземпляр класса на основе
     * идетификатора СМС, пользовательского сообщения
     *
     * @param smsId     идетификатор смс сообщения, который возвращает СМС-шлюз
     * @param smsReply  пользовательское сообщение
     */
    public SmsResponse(String smsId, String smsReply, String smsStatus) {
        hasText(smsId, "Идентификатор сообщения не может быть пустым");
        hasText(smsStatus, "Сообщение должно иметь статус");
        this.smsId = smsId;
        this.smsReply = smsReply;
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

}
