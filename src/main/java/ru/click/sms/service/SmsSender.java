package ru.click.sms.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.service.exception.SmsException;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

public interface SmsSender {
    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ {@link SmsResponse}
     */
    SmsResponse send(int templateId, String phone);

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ {@link SmsResponse}
     */
    SmsResponse send(int templateId, String phone, @Nullable Object... args);

    /**
     * Метод для отправки СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ {@link SmsResponse}
     */
    SmsResponse send(int templateId, String phone, @Nullable String[] args);

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @return смс ответ  {@link SmsResponse}
     */
    Future<SmsResponse> guaranteedSend(int templateId, String phone);

    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param templateId номер шаблона сообщения
     * @param phone      номер телефона
     * @param args       параметры шаблона
     * @return смс ответ  {@link SmsResponse}
     */

    Future<SmsResponse> guaranteedSend(int templateId, String phone, @Nullable Object... args);

    default HttpHeaders auth(String username, String password ){
        return new HttpHeaders(){
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")) );
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
    }

    default String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SmsException(e.getMessage(), e);
        }
    }


}
