package ru.click.sms;

/**
 * Интерфэйс для управления шаблонами СМС
 */
public interface TemplateService {
    /**
     * Добавляет шаблон
     *
     * @param templateText текст шаблона
     * @return идентификатор шаблона
     */
    Integer addTemplate(String templateText);

    /**
     * Добавляет параметризируемый шаблон если {@literal true},
     * иначе обычный шаблон
     *
     * @param templateText текст шаблона
     * @param hasParams    является ли шаблон параметризируемым
     * @return идентификатор шаблона
     */

    Integer addTemplate(String templateText, boolean hasParams);

    /**
     * Удаляет шаблон
     *
     * @param templateId идентификатор шаблона
     * @return идентификатор шаблона
     */
    boolean deleteTemplate(Integer templateId);

    /**
     * Обновляет шаблон
     *
     * @param templateId   идентификатор шаблона
     * @param templateText текст шаблона
     * @return идентификатор шаблона
     */
    boolean updateTemplate(Integer templateId, String templateText);
}
