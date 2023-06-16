package com.project.restapip;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class APIController {
Send s1 = new Send();

    @PostMapping("/post/{id}")
    public String post(@RequestBody String id2, @PathVariable String id) {

        s1.sender(id);
        return  id  ;
    }
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> get(@PathVariable String id) throws FileNotFoundException {
        System.out.println(id);
        File pdfFile = new File("Files\\"+id+".pdf");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=1.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new FileInputStream(pdfFile)));
    }
}
