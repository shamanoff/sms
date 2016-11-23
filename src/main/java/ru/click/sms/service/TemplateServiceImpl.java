package ru.click.sms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.click.sms.TemplateService;
import ru.click.sms.model.Template;
import ru.click.sms.repository.TemplateRepository;

import static org.springframework.util.Assert.notNull;

/**
 * Сервис для управления шаблонами СМС
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateRepository tempRep;

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
        Template template = tempRep.findOne(templateId);
        if (template != null) {
            tempRep.delete(templateId);
            return true;
        }
        return false;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateTemplate(Integer templateId, String templateText) {
        Template template = tempRep.findOne(templateId);
        if (template != null) {
            template = template.setText(templateText);
            tempRep.save(template);
            return true;
        }
        return false;
    }
}
