package ru.click.sms.utils;

import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.Map;

public class UriEncoder implements UriTemplateHandler {

    @Override
    public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
        return URI.create(uriTemplate);
    }

    @Override
    public URI expand(String uriTemplate, Object... uriVariables) {
        return URI.create(uriTemplate);
    }
}
