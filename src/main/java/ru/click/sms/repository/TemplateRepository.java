package ru.click.sms.repository;

import org.springframework.data.repository.CrudRepository;
import ru.click.sms.model.Template;

/**
 * Репозиторий для шаблонов СМС
 */
public interface TemplateRepository extends CrudRepository<Template, Integer> {


}
