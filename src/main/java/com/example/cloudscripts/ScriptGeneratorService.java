package com.example.cloudscripts;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class ScriptGeneratorService {

    public String generateScript(String cloud, String resourceType, String format, Map<String, String> params) throws Exception {
        Path scriptPath = Path.of("src/main/resources/templates/" + cloud + "/" + resourceType + "-template." + format);
        String scriptTemplate = Files.readString(scriptPath);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            scriptTemplate = scriptTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return scriptTemplate;
    }
}
