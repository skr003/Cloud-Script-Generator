package com.example.cloudscripts;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@RestController
@RequestMapping("/api/scripts")
public class CloudScriptController {

    private final ScriptGeneratorService scriptGeneratorService;

    public CloudScriptController(ScriptGeneratorService scriptGeneratorService) {
        this.scriptGeneratorService = scriptGeneratorService;
    }

    @GetMapping("/generate")
    public ResponseEntity<Resource> generateScript(
            @RequestParam String cloud,
            @RequestParam String resourceType,
            @RequestParam String format,
            @RequestParam Map<String, String> params) throws Exception {

        String scriptContent = scriptGeneratorService.generateScript(cloud, resourceType, format, params);
        Path tempFile = Files.createTempFile(resourceType, "." + format);
        Files.write(tempFile, scriptContent.getBytes(), StandardOpenOption.WRITE);

        Resource fileResource = new UrlResource(tempFile.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tempFile.getFileName() + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(fileResource);
    }
}
