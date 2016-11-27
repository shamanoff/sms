package ru.click.sms.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.click.sms.service.TemplateService;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService service;

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody String text, @RequestParam(required = false) Boolean hasParam) {
        int templateId = service.addTemplate(text, hasParam != null && hasParam);
        return ok(templateId);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        val templates = service.getTemplates();
        if (templates.isEmpty()) {
            return notFound().build();
        }
        return ok(templates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        val template = service.getTemplate(id);
        if (template.isPresent()) {
            return ok(template);
        }
        return notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean success = service.deleteTemplate(id);
        if (success) {
            return ok("Шаблон успешно удален");
        }
        return ok("Шаблон не был удален, возможно шаблона с таким id не существует");
    }
}
