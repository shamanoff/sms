package ru.click.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.click.sms.model.Template;
import ru.click.sms.repository.TemplateRepository;

import static org.springframework.util.Assert.notNull;

/**
 * Сервис для управления шаблонами СМС
 */
@Service
public class TemplateServiceImpl implements TemplateService, TemplateReader {

    /**
     * Репозиторий шаблонов смс
     */
    private final TemplateRepository tempRep;

    /**
     * Конструктор для внедрения зависимостей
     * @param tempRep репозиторий шаблонов смс
     */
    @Autowired
    public TemplateServiceImpl(TemplateRepository tempRep) {
        this.tempRep = tempRep;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer addTemplate(String templateText) {
        return addTemplate(templateText, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer addTemplate(String templateText, boolean hasParams) {
        Template template = Template.of(templateText, hasParams);
        Integer templateId = tempRep.save(template).getId();
        notNull(templateId, "идентификатор шаблона не может быть пустым после сохранения в базу");
        return templateId;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteTemplate(Integer templateId) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateTemplate(Integer templateId, String templateText) {
        return false;
    }

    /**
     * Читает шаблон по id
     *
     * @param templateId идентификатор шаблона
     * @return шаблон смс
     */
    @Override
    public Template getTemplate(int templateId) {
        return tempRep.findOne(templateId);
    }
}
