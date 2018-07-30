package com.tabor.notes.service.project.sharing;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerator {
    public String generate() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
