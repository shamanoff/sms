package ru.click.sms.service.exception;

public class NotFoundTemplateException extends TemplateException {
    public NotFoundTemplateException() {
        super("Шаблон не найден");
    }

    public NotFoundTemplateException(String message) {
        super(message);
    }

    public NotFoundTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundTemplateException(Throwable cause) {
        super(cause);
    }
}
