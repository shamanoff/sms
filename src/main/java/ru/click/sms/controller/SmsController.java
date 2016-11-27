package ru.click.sms.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.click.sms.service.SmsSender;
import ru.click.sms.service.annotations.ProstorSmsSender;
import ru.click.sms.service.exception.BadRequestSmsException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * Создан 24.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Validated
@RestController
public class SmsController {

    private final SmsSender sender;

    @Autowired
    public SmsController(@ProstorSmsSender SmsSender sender) {
        notNull(sender, "Sms sender не может быть равным null");
        this.sender = sender;
    }

    @GetMapping("/sms/send")
    public ResponseEntity<String> sendSms(

            @RequestParam @Pattern(regexp = "[9][0-9]{9}", message = "{validation.phone}") String phone,
            @RequestParam String text
    ) {
        return ok(sender.send(text, phone));
    }


    @ExceptionHandler({BadRequestSmsException.class})
    public ResponseEntity<String> handleError(Exception e) {
        return badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleError(ConstraintViolationException e) {
        val violations = e.getConstraintViolations();
        String message = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Неизвестно");
        return badRequest().body(message);
    }

}


