package com.hackthon.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleNameConverter implements Converter<String, RoleName> {

    @Override
    public RoleName convert(String source) {
        return RoleName.fromString(source);
    }
}

