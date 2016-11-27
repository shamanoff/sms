package ru.click.sms.model;

import javax.persistence.*;

import static java.lang.String.format;
import static org.springframework.util.Assert.notEmpty;

/**
 * ДТО шаблона с СМС собщениями
 */
@Entity(name = "templates")
public class Template {

    public static Template of(String text, boolean hasParams) {
        return new Template().setText(text).setHasParams(hasParams);
    }

    /**
     * Идентификатор шаблона
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Текст шаблона
     */
    @Column(name = "text")
    private String text;
    /**
     * Имеет ли шаблон парамметры
     */
    @Column(name = "has_params")
    private boolean hasParams;

    public Integer getId() {
        return id;
    }

    public Template setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public String getText(Object... params) {
        notEmpty(params, hasParams ? "Параметры сообщения обязаны присутствовать" : "Шаблон не имеет параметров");
        return format(text, params);
    }

    public Template setText(String text) {
        this.text = text;
        return this;
    }

    public Template setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
        return this;
    }
}
