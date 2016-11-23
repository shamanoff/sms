package ru.click.sms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String text;
    /**
     * Имеет ли шаблон парамметры
     */
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

    public Template setText(String text) {
        this.text = text;
        return this;
    }

    public boolean hasParams() {
        return hasParams;
    }

    public Template setHasParams(boolean hasParams) {
        this.hasParams = hasParams;
        return this;
    }
}
