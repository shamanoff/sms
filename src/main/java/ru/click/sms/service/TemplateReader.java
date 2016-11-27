package ru.click.sms.service;

import ru.click.sms.model.Template;

import java.util.List;
import java.util.Optional;

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
    Optional<Template> getTemplate(int templateId);

    List<Template> getTemplates();
}
