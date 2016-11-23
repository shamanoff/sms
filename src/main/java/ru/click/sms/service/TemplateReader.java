package ru.click.sms.service;

import ru.click.sms.model.Template;

/**
 * Интерфейс для чтения шаблонов смс
 * <p>
 * Создан 23.11.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
public interface TemplateReader {

    /**
     * Читает шаблон по id
     * @param templateId идентификатор шаблона
     * @return шаблон смс
     */
    Template getTemplate(int templateId);
}
