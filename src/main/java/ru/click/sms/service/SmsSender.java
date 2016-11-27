package ru.click.sms.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import ru.click.sms.model.SmsResponse;
import ru.click.sms.service.exception.SmsException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

public interface SmsSender {
    /**
     * Метод для отправки СМС
     *
     * @param phone      номер телефона
     * @return смс ответ {@link SmsResponse}
     */
    String send(String text, String phone);





    /**
     * Метод с гарантированной доставкой СМС
     *
     * @param phone      номер телефона
     * @return смс ответ  {@link SmsResponse}
     */
    Future<String> guaranteedSend(String text, String phone);



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
